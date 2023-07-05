package framework.settings;

import java.io.IOException;

public final class RestlessSettings extends Settings {  // Skeleton class of a typical settings bean reading from a properties file

    private static final RestlessSettings instance = new RestlessSettings();

    /*
     *  TODO: declare parameters as fields
     *  String param1;
     *  int param2;
     */
    private RestlessSettings() {
        super("restless", "/config/configuration.properties", "Restless Settings", true);
    }

    public static RestlessSettings getInstance() {
        return instance;
    }

    @Override
    public void load() throws IOException {
        PropertiesHandler handler = new PropertiesHandler(prefix, path, OverridePaths.getInstance().getRestlessSettings());

        /*Getting parameters
        /* 
         *  param1 = handler.getParam("param_name1");
         *  param2 = Integer.parseInt(handler.getParam("param_name2");
         */
    }

    /*
     *  Getters for parameters as fields
     */
}
