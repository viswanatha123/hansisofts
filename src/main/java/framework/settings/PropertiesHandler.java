package framework.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class PropertiesHandler {

    private static final Logger LOG = Logger.getLogger(PropertiesHandler.class.getName());

    private final String prefix; // applicationName.module.param || applicationName.param || global.module.param

    private final Properties localProperties;
    private final Properties overrideProperties;

    public PropertiesHandler(String prefix, String localPath, String overridePath) throws IOException {
        this.prefix = prefix;

        localProperties = new Properties();
        overrideProperties = new Properties();

        //Load Local
        try (InputStream istream = PropertiesHandler.class.getResourceAsStream(localPath);) {
            localProperties.load(istream);
        }

        //Load Override
        try (InputStream istream = new FileInputStream(new File(overridePath))) {
            overrideProperties.load(istream);
            LOG.log(Level.INFO, "Overriding {0}:{1} -> {2}", new Object[]{prefix, localPath, overridePath});
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Failed to load override file ({0}).", prefix);
        }
    }

    public String getParam(String paramName) {
        //Overrides
        String specificOverride = null;
        if (prefix != null) {
            specificOverride = overrideProperties.getProperty(prefix + "." + paramName);
        }
        String globalOverride = overrideProperties.getProperty("global." + paramName);
        String unprefixedOverride = overrideProperties.getProperty(paramName);

        //Local Values (WAR Resources)
        String localValue = null;
        if (prefix != null) {
            localValue = localProperties.getProperty(prefix + "." + paramName);
        }
        String unprefixedLocalValue = localProperties.getProperty(paramName);

        String param;

        if (specificOverride != null) {
            param = specificOverride;
        } else if (globalOverride != null) {
            param = globalOverride;
        } else if (unprefixedOverride != null) {
            param = unprefixedOverride;
        } else if (localValue != null) {
            param = localValue;
        } else {
            param = unprefixedLocalValue;
        }

        return param;
    }
}
