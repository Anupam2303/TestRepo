package com.anupam.frameworkSetup.testngSupport;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.anupam.frameworkSetup.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * TestInterceptor implements ITestListner of testNG This class helps in
 * printing values in Report

 */
public class TestInterceptor implements ITestListener {

    private final boolean LogToStandardOutput = true;
    private static int count = 1;
    private static Logger log = Logger.getLogger(String.valueOf(TestInterceptor.class));

    @Override
    public void onTestStart(final ITestResult arg0) {
        Reporter.setCurrentTestResult(arg0);
        Reporter.log("<br>");
        Reporter.log("----------------------------------------------------------", LogToStandardOutput);
        log.info("----------------------------------------------------------");
        Reporter.log("</br>");
        final int no = count++;
        Reporter.log(no + "::Initiating TestCase::" + arg0.getName() + " (" + arg0.getMethod().getId() + ")",
            LogToStandardOutput);
        log.info(no + "::Initiating TestCase::" + arg0.getName() + " (" + arg0.getMethod().getId() + ")");

        Reporter.log("</br>");
        Reporter.log("Start Time::" + getTimeReport(), LogToStandardOutput);
        log.info("Start Time::" + getTimeReport());
        Reporter.log("</br>");

    }

    @Override
    public void onTestSuccess(final ITestResult arg0) {
        log.info(String.valueOf(TestInitialization.apilogs));
        Reporter.log("End Time " + getTimeReport(), LogToStandardOutput);
        log.info("End Time " + getTimeReport());
        Reporter.log("<br>");
        final long ms = arg0.getEndMillis() - arg0.getStartMillis();
        Reporter.log("Execution Time :: " + ms / 1000 + "." + ms % 1000 + " Seconds", LogToStandardOutput);
        log.info("Execution Time :: " + ms / 1000 + "." + ms % 1000 + " Seconds");
        Reporter.log("</br>");
        Reporter.log("Completed TestCase :: " + arg0.getName() + " => Status: PASS", LogToStandardOutput);
        log.info("Completed TestCase :: " + arg0.getName() + " => Status: PASS");
        Reporter.log("</br>");
        Reporter.log("----------------------------------------------------------", LogToStandardOutput);
        log.info("----------------------------------------------------------");

    }

    @Override
    public void onTestFailure(final ITestResult arg0) {
        log.info(String.valueOf(TestInitialization.apilogs));
        System.out.println(TestInitialization.apilogs);
        Reporter.log("End Time " + getTimeReport(), LogToStandardOutput);
        log.info("End Time " + getTimeReport());

        Reporter.log("</br>");
        final long ms = arg0.getEndMillis() - arg0.getStartMillis();
        Reporter.log("Execution Time :: " + ms / 1000 + "." + ms % 1000 + " Seconds", LogToStandardOutput);
        log.info("Execution Time :: " + ms / 1000 + "." + ms % 1000 + " Seconds");
        Reporter.log("</br>");
        Reporter.log("Completed TestCase :: " + arg0.getName()
                     + " =>  "
                     + Utils.ANSI_RED_BACKGROUND
                     + "Status: FAIL"
                     + Utils.ANSI_RESET, LogToStandardOutput);
        log.info("Completed TestCase :: " + arg0.getName() + " => Status: FAIL");
        Reporter.log("</br>");

    }

    @Override
    public void onTestSkipped(final ITestResult arg0) {

        Reporter.log("End Time " + getTimeReport(), LogToStandardOutput);
        log.info("End Time " + getTimeReport());

        Reporter.log("</br>");
        final long ms = arg0.getEndMillis() - arg0.getStartMillis();
        Reporter.log("Execution Time :: " + ms / 1000 + "." + ms % 1000 + " Seconds", LogToStandardOutput);
        log.info("Execution Time :: " + ms / 1000 + "." + ms % 1000 + " Seconds");
        Reporter.log("</br>");
        Reporter.log("Completed TestCase :: " + arg0.getName() + " => Status: SKIPPED", LogToStandardOutput);
        log.info("Completed TestCase :: " + arg0.getName() + " => Status: SKIPPED");
        Reporter.log("</br>");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(final ITestContext context) {

        Reporter.log("Starting with suite :: " + context.getSuite().getParallel().toString());
    }

    private String getTimeReport() {
        final Calendar calendar = new GregorianCalendar();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        final int seconds = calendar.get(Calendar.SECOND);
        return ":: " + hour + ":" + minutes + ":" + seconds;
    }

    @Override
    public void onFinish(final ITestContext arg0) {
        // TODO Auto-generated method stub

    }

    public static String prettyPrintJson(final String json) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final JsonParser jp = new JsonParser();
        final JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }
}