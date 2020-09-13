package com.anupam.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

import com.anupam.frameworkSetup.API.ServiceObjectFactory;
import com.anupam.frameworkSetup.Utils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class BaseTest {

    protected ServiceObjectFactory service;

    @BeforeClass(alwaysRun = true)
    public void initializeAPIObjectFactory() {
        service = new ServiceObjectFactory();
    }

    /**
     * Method for skipping the test case in execution
     * @param message message or defect id information
     */
    public static void skipThisTestCase(final String message) {

        Utils.log(message);
        throw new SkipException(message);
    }

    protected void assertResponse(final String response, final String jsonPath, final String key) {

        final DocumentContext responseContext = JsonPath.parse(response);
        final String responseValue = responseContext.read(jsonPath).toString().replaceAll("[\"\\[\\]]", "");
        Assert.assertEquals(responseValue, key);
    }

    protected String getValueFromResponse(final String response, final String jsonPath) {
        final DocumentContext responseContext = JsonPath.parse(response);
        return responseContext.read(jsonPath).toString().replaceAll("[\"\\[\\]]", "");
    }

    public static boolean isContains(final String response, final String jsonPath, final String key) {
        final DocumentContext responseContext = JsonPath.parse(response);
        final String responseValue = responseContext.read(jsonPath).toString().replaceAll("[\"\\[\\]]", "");
        return responseValue.contains(key);
    }



}
