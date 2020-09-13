package com.anupam.frameworkSetup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.anupam.frameworkSetup.exception.FrameworkException;

public class SetupClass {

    public static String environment;
    private static Properties frameworkProp;

    @BeforeTest(alwaysRun = true)
    public void initializeMarketplace() throws IOException {
        if (environment == null){
            final InputStream iStream = SetupHelper.class.getClassLoader().getResourceAsStream(
                    "framework.properties");
            frameworkProp.load(iStream);
            environment = frameworkProp.getProperty("environment");
        }
    }
    private void setFinalFrameworkProperties(final String environment) {
        SetupClass.environment = getValue("environment", environment);
    }

    private static void setFrameWorkPropeties() {
        frameworkProp = new Properties();
        final InputStream iStream = SetupHelper.class.getClassLoader().getResourceAsStream(
                "framework.properties");
        try {
            frameworkProp.load(iStream);
        } catch (final Exception e) {
            throw new FrameworkException("Framework.properties file is not loaded ");
        }
    }

    private void printFrameworkConfigurations() {
        Reporter.log("-------------------------------------------------------------", true);
        Reporter.log("!!!!!!!!!!!!!  Tests are starting !!!!!!!!!!!!!!", true);
        Reporter.log("-------------------------------------------------------------", true);
        Reporter.log("Environment        : " + SetupClass.environment, true);
        Reporter.log("----------------------------------------------------------", true);
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters({ "environment"})
    public void intializeAll(@Optional("") final String environment) {

        final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

        setFrameWorkPropeties();
        setFinalFrameworkProperties(environment);
        SetupHelper.initializeConfiguration();
        printFrameworkConfigurations();
        System.setProperty(ESCAPE_PROPERTY, "false");
        System.out.println("12345678"+environment);
    }

    private static String getValue(final String key, final String value) {

        // TestNG
        if (!value.isEmpty()) {
            return value;
        }

        //Implement logic for data
        return null;
    }

}
