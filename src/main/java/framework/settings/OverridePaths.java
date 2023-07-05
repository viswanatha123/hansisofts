package framework.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class OverridePaths {

    private static final Logger LOG = Logger.getLogger(OverridePaths.class.getName());

    private static final OverridePaths instance = new OverridePaths();

    private final String path = "/config/overrides.properties";

    // TODO: list all properties paths that can be overriden
    private String databaseSettings;
    private String restlessSettings;

    private OverridePaths() {
        Properties properties = new Properties();
        try (InputStream istream = PropertiesHandler.class.getResourceAsStream(path);) {
            properties.load(istream);

            // TODO: read override paths
            restlessSettings = properties.getProperty("restless");
            databaseSettings = properties.getProperty("database");

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public static OverridePaths getInstance() {
        return instance;
    }

    // TODO: generate getters for all override paths
    public String getRestlessSettings() {
        return restlessSettings;
    }

    public String getDatabaseSettings() {
        return databaseSettings;
    }
}
