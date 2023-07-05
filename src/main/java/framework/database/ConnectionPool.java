package framework.database;

import framework.diagnostics.MonitoredComponent;
import framework.diagnostics.Status;
import framework.diagnostics.Status.State;
import framework.settings.DatabaseSettings;
import java.sql.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConnectionPool extends MonitoredComponent {

    private static final ConnectionPool instance = new ConnectionPool();

    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    private final List<Connection> pool = new LinkedList<>();
    private final List<Connection> taken = new LinkedList<>();

    private ConnectionPool() {
        super("Connection Pool", true);
        this.status = new Status(State.UNINITIALIZED, null);
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private boolean validate(Connection jdbcConnection) {   // Make sure the connection is not killed from the server side
        try {
            PreparedStatement stmt = jdbcConnection.prepareStatement("SELECT 1;");
            stmt.executeQuery();
            return true;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public synchronized void initialize() {
        try {
            DatabaseSettings settings = DatabaseSettings.getInstance();
            if (settings.getStatus().isOperational()) {
                Class.forName(settings.getDriver());
                int poolSize = settings.getPoolSize();
                for (int i = 0; i < poolSize; i++) {
                    pool.add(DriverManager.getConnection(settings.getAddress(), settings.getUser(), settings.getPassword()));
                }
                status = new Status(State.OPERATIONAL);
            } else {
                status = new Status(State.UNINITIALIZED);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            status = new Status(State.MALFUNCTION, ex);
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void shutdown() {
        try {
            // Closing all connections in the pool
            for (Connection connection : pool) {
                connection.close();
            }
            pool.clear();

            // Closing all of the taken connections
            for (Connection connection : taken) {
                connection.close();
            }
            taken.clear();

        } catch (SQLException ex) {
            LOG.log(Level.WARNING, null, ex);
        } finally {
            status = new Status(State.UNINITIALIZED);
            notifyAll();
        }
    }

    @Override
    public synchronized void permanentShutdown() {
        shutdown();

        // Deregistering JDBC
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, null, ex);
        }
    }

    public synchronized int getAvailable() {
        return pool.size();
    }

    public synchronized Connection getConnection() throws InterruptedException {
        while (pool.isEmpty() && status.isOperational()) {
            wait();
        }

        Connection jdbcConnection = pool.remove(0);

        if (validate(jdbcConnection)) {
            taken.add(jdbcConnection);
            return jdbcConnection;
        } else {
            try {
                jdbcConnection.close();
            } catch (SQLException ex) {
            }
            try {
                DatabaseSettings settings = DatabaseSettings.getInstance();
                jdbcConnection = DriverManager.getConnection(settings.getAddress(), settings.getUser(), settings.getPassword());
                taken.add(jdbcConnection);
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
                this.status = new Status(State.MALFUNCTION, ex);
            }
        }

        return jdbcConnection;
    }

    public synchronized void returnConnection(Connection jdbcConnection) {
        if (jdbcConnection != null) {   // A safeguard against null connections that might end up "returned"
            taken.remove(jdbcConnection);
            pool.add(jdbcConnection);
        }
        notifyAll();
    }

}
