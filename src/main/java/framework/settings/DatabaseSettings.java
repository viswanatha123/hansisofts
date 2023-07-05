package framework.settings;

import framework.diagnostics.Status;
import java.io.IOException;

public final class DatabaseSettings extends Settings {

    private static final DatabaseSettings instance = new DatabaseSettings();

    //Settings Parameters
    private String driver;
    private String address;
    private String user;
    private String password;
    private int poolSize;

    private Status status;

    private DatabaseSettings() {
        super("database", "/config/configuration.properties", "Database Settings", true);
    }

    public static DatabaseSettings getInstance() {
        return instance;
    }

    @Override
    protected void load() throws IOException {
        PropertiesHandler handler = new PropertiesHandler(prefix, path, OverridePaths.getInstance().getDatabaseSettings());

        driver = handler.getParam("driver");
        address = handler.getParam("address");
        user = handler.getParam("user");
        password = handler.getParam("password");
        poolSize = Integer.parseInt(handler.getParam("poolSize"));
    }

    //Config specific getters
    public String getDriver() {
        return driver;
    }

    public String getAddress() {
        return address;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

}
