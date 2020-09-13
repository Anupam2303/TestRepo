package com.anupam.frameworkSetup.API;

import net.minidev.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.anupam.frameworkSetup.SetupHelper;
import com.anupam.frameworkSetup.caller.PlivoServicesCaller;
import com.anupam.frameworkSetup.caller.RestHelper;
import com.anupam.frameworkSetup.creator.PlivoJsonCreator;
import com.anupam.frameworkSetup.exception.FrameworkException;

public class PlivoServiceAPI extends BaseAPI{

    //Service context endpoints
    static String getNumbersEndpoint = String.format("v1/Account/%s/Number/",SetupHelper.authId);
    static String getMyAccountEndpoint = String.format("v1/Account/%s/",SetupHelper.authId);
    static String sendMessageEndpoint = String.format("v1/Account/%s/Message/",SetupHelper.authId);
    static String getpricingEndpoint = String.format("v1/Account/%s/Pricing/",SetupHelper.authId);
    private  final String PlivoServiceJsonTemplatePath = "jsonRequestTemplate/plivoServiceAPI/";


    //Json Path should go here
    public static String messageJsonPath = "$.message";
    public static String absoluteNumberJsonPath = "$..number";
    public static String singleNumberJsonPath = "$.objects[%s].number";

    //Json Path for MyAcounts Endpoints
    public static String cashCreditsJsonPath = "$..cash_credits";

    //JsonPath for sendmessage request & response
    public static String srcJsonPath = "$..src";
    public static String dstJsonPath = "$..dst";
    public static String textMessageJsonPath = "$..text";
    public static String messageuuidJsonPath = "$..message_uuid";

    //JsonPath for message status details
    public static String messagestateJsonPath = "$..message_state";
    public static String total_rateJsonPath = "$..total_rate";
    public static String total_amountJsonPath = "$..total_amount";

    //JsonPath for message pricing details
    public static String outBoundRateJsonPath = "$..message.outbound.rate";


    //Modulor functions
    private File getJsonFile(final String path) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = null;
        try {
            file = new File(classLoader.getResource(PlivoServiceJsonTemplatePath + path).toURI());
        } catch (final URISyntaxException e) {

            throw new FrameworkException("Please check path for Brand Json file " + e.toString());
        }

        return file;

    }


    // Concrete functions for API methods
    public String getPlivoURL(){
        return new PlivoServicesCaller().doGet(SetupHelper.plivoServer, RestHelper.JSON_HEADER,200);
    }

    public String getNumbers(int statusCode){
        return new PlivoServicesCaller().doGet(SetupHelper.plivoServer+getNumbersEndpoint,
                RestHelper.JSON_HEADER,statusCode);
    }

    public String getMyAccountDetails(int statusCode){
        return new PlivoServicesCaller().doGet(SetupHelper.plivoServer+getMyAccountEndpoint,
                RestHelper.JSON_HEADER,statusCode);
    }

    public String sendMessageFromMyAccount(Map<String, Object> map,int statusCode){
        final PlivoJsonCreator plivoJsonCreator = new PlivoJsonCreator();
        JSONObject sendmessageJson = plivoJsonCreator.createJSON(getJsonFile("sendMessageTemplate.json"));
        sendmessageJson = insertValues(sendmessageJson, map);
        return new PlivoServicesCaller().doPost(SetupHelper.plivoServer+sendMessageEndpoint,RestHelper.JSON_HEADER,
                sendmessageJson,statusCode);
    }

    public String getMessageStatusDetails(String messageuuid, int statusCode){
        return new PlivoServicesCaller().doGet(SetupHelper.plivoServer+sendMessageEndpoint+messageuuid,
                RestHelper.JSON_HEADER,statusCode);
    }

    public String getMessagePricingDetails(Map<String, Object> qParams, int statusCode){
        final PlivoJsonCreator plivoJsonCreator = new PlivoJsonCreator();
        plivoJsonCreator.url = SetupHelper.plivoServer+getpricingEndpoint;
        plivoJsonCreator.queryString(qParams);
        return new PlivoServicesCaller().doGet(plivoJsonCreator.url, RestHelper.JSON_HEADER,statusCode);
    }


}
