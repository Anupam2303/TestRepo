package com.anupam.frameworkSetup;

import org.testng.Reporter;
import com.anupam.frameworkSetup.testngSupport.TestInitialization;

public class Utils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41;37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = (char) 27 + "[34m";
    public static final String ANSI_BLUE_BACKGROUND = (char) 27 + "[44m";
    public static final String ANSI_MAGENTA_BACKGROUND = (char) 27 + "[45;37m";

    public static void log(final String toPrint) {

        TestInitialization.apilogs.append(" INFO:: ").append(toPrint).append("\r\n ");
        Reporter.log("INFO::" + toPrint, true);
    }

}
