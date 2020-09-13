package com.anupam.frameworkSetup.testngSupport;

import org.testng.*;

public class TestInitialization implements IInvokedMethodListener {

    public static StringBuffer apilogs = new StringBuffer();

    @Override
    public void beforeInvocation(final IInvokedMethod method, final ITestResult testResult) {

            if (method.getTestMethod().isTest()) {
                apilogs.setLength(0);

                // Pretty log settings for console with bold blinking text and yellow background
                apilogs.append((char) 27 + "[5;43;1m" + "DETAILED LOGS" + (char) 27 + "[0m" + "\r\n");

            }
    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult) {

        if (method.isTestMethod()) {

        }
    }
}
