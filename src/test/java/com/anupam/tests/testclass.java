package com.anupam.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.anupam.frameworkSetup.API.PlivoServiceAPI;
import com.anupam.frameworkSetup.caller.BaseCaller;

public class testclass extends BaseTest {

    BaseCaller baseCaller = new BaseCaller();
    public static String objectstationName  = "$.root.station[%s].name";
    public static String objectestimateMinute  = "$.root.station[0].etd[%s].estimate[%s].minutes";
    public static String objectdestination  = "$.root.station[0].etd[%s].destination";


    @Test(groups = {"smoke","P1"})
    public void UrlTest(){
        String response = baseCaller.doGet("https://api.bart.gov/api/etd.aspx?cmd=etd&orig=ALL&key=MW9S-E7SL-26DU-VV8V&json=y");
        String stationName = getValueFromResponse(response, String.format(objectstationName,0));
        String et = getValueFromResponse(response,
                String.format(objectestimateMinute, 0, 0));
        System.out.println(stationName);
        System.out.println(et);
        List<String> etddestinationList = new ArrayList<>();
        for (int i=0;i<4;i++){
            int estimattime = Integer.parseInt(getValueFromResponse(response, getValueFromResponse(response,
                    String.format(objectestimateMinute, i, 0))));
            if (estimattime<60)
                etddestinationList.add(getValueFromResponse(response, getValueFromResponse(response,
                        String.format(objectdestination, i))));
        }
        System.out.println(etddestinationList.toString());

    }
}
