package com.anupam.frameworkSetup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.anupam.frameworkSetup.exception.FrameworkException;

public class SetupHelper {

    static Properties envProperties;
    public static String plivoServer;
    public static String authId;
    public static String token;

    //Initialize configuration before test
    public static void initializeConfiguration(){
        envProperties = new Properties();

        try {
            final InputStream in = SetupHelper.class.getClassLoader()
                    .getResourceAsStream("stepupProperties/" + SetupClass.environment + "-env.properties");
            envProperties.load(in);
//            plivoServer = getValue("plivoServer");
//            authId = getValue("authId");
//            token = getValue("token");
            plivoServer = envProperties.getProperty("plivoServer");
            authId = envProperties.getProperty("authId");
            token = envProperties.getProperty("token");
        }
        catch (final IOException e) {
            throw new FrameworkException("Exception while loading environment property file");
        }

    }

    private static String getValue(final String key) {

        if (System.getProperty(key) != null) {
            return System.getProperty(key);
        }

        return envProperties.getProperty(key);

    }

}
