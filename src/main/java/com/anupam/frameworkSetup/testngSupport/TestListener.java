package com.anupam.frameworkSetup.testngSupport;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

/**
 * TestInterceptor implements ITestListner of testNG This class helps in
 * printing values in Report
 */

public class TestListener implements ITestListener {

    private static Logger log = Logger.getLogger(String.valueOf(TestInterceptor.class));
    private final boolean LogToStandardOutput = true;
    private static int count = 1;

    private String getTimeReport() {
        final Calendar calendar = new GregorianCalendar();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        final int seconds = calendar.get(Calendar.SECOND);
        return ":: " + hour + ":" + minutes + ":" + seconds;
    }


    public void onStart(ITestContext context) {
        Reporter.log("Starting with suite :: " + context.getSuite().getParallel().toString());
        Reporter.log("*** Test Suite " + context.getName() + " started ***");

    }

    public void onFinish(ITestContext context) {
        System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log(("*** Running test method " + result.getMethod().getMethodName() + "..."));
        ExtentTestManager.startTest(result.getMethod().getMethodName());
        Reporter.setCurrentTestResult(result);
        Reporter.log("<br>");
        Reporter.log("----------------------------------------------------------", LogToStandardOutput);
        log.info("----------------------------------------------------------");
        Reporter.log("</br>");
        final int no = count++;
        Reporter.log(no + "::Initiating TestCase::" + result.getName() + " (" + result.getMethod().getId() + ")",
                LogToStandardOutput);
        log.info(no + "::Initiating TestCase::" + result.getName() + " (" + result.getMethod().getId() + ")");

        Reporter.log("</br>");
        Reporter.log("Start Time::" + getTimeReport(), LogToStandardOutput);
        log.info("Start Time::" + getTimeReport());
        Reporter.log("</br>");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }

}