package com.anupam.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.anupam.frameworkSetup.API.PlivoServiceAPI;
import com.anupam.frameworkSetup.SetupClass;
import com.anupam.frameworkSetup.SetupHelper;

public class PlivoServiceTest extends BaseTest{

    String senderNo;
    String recieverNo;
    double existingCredit;
    String messageUUId;
    double sentmessageRate;
    double sentmessageAmount;
    double outboundRate;
    double updatedCredit;


    @Test(groups = {"smoke","P1"})
    public void UrlTest(){
        String response = service.plivoServiceAPI().getPlivoURL();
        Assert.assertTrue(isContains(response,PlivoServiceAPI.messageJsonPath,"welcome to Plivo"));
    }

    @Test(groups = {"smoke","P1"})
    public void messageRateValidationtest() throws InterruptedException {

        //Get Two numbers from my account
        String myNumberListresponse = service.plivoServiceAPI().getNumbers(200);
        senderNo = getValueFromResponse(myNumberListresponse, String.format(PlivoServiceAPI.singleNumberJsonPath,0));
        recieverNo = getValueFromResponse(myNumberListresponse, String.format(PlivoServiceAPI.singleNumberJsonPath,1));

        //Get credit before sending message
        String  myAccountDetailsresponse = service.plivoServiceAPI().getMyAccountDetails(200);
        existingCredit = Double.parseDouble(getValueFromResponse(myAccountDetailsresponse, PlivoServiceAPI.cashCreditsJsonPath));

        //Send Message
        Map<String,Object> sendMessageMap = new HashMap<>();
        sendMessageMap.put(PlivoServiceAPI.srcJsonPath,senderNo);
        sendMessageMap.put(PlivoServiceAPI.dstJsonPath,recieverNo);
        sendMessageMap.put(PlivoServiceAPI.textMessageJsonPath,"Hello There ");
        String sendMessageResponse = service.plivoServiceAPI().sendMessageFromMyAccount(sendMessageMap, 202);
        messageUUId = getValueFromResponse(sendMessageResponse,PlivoServiceAPI.messageuuidJsonPath);

        //Wait thread to send message
        Thread.sleep(1000);

        //Get Total rate of message
        String messageStatusDetailsrespomnse = service.plivoServiceAPI().getMessageStatusDetails(messageUUId, 200);
        assertResponse(messageStatusDetailsrespomnse,PlivoServiceAPI.messagestateJsonPath,"delivered");
        sentmessageRate = Double.parseDouble(getValueFromResponse(messageStatusDetailsrespomnse, PlivoServiceAPI.total_rateJsonPath));
        sentmessageAmount = Double.parseDouble(getValueFromResponse(messageStatusDetailsrespomnse, PlivoServiceAPI.total_amountJsonPath));

        //GetOutbound Rate
        Map<String, Object> outBoundParams = new HashMap<String, Object>();
        outBoundParams.put("country_iso","US");
        String messagePricingresponse = service.plivoServiceAPI().getMessagePricingDetails(outBoundParams,200);
        System.out.println(messagePricingresponse);
        outboundRate = Double.parseDouble(getValueFromResponse(messagePricingresponse, PlivoServiceAPI.outBoundRateJsonPath));

        //Verify Outbound Rate and message sent rate are same
        Assert.assertEquals(sentmessageRate,outboundRate,"OutboundRate and  MessageRate is not same");

        //Get credit after sending message
        myAccountDetailsresponse = service.plivoServiceAPI().getMyAccountDetails(200);
        updatedCredit = Double.parseDouble(getValueFromResponse(myAccountDetailsresponse, PlivoServiceAPI.cashCreditsJsonPath));

        //Verify Updatedcredit is less then existing credit
        Assert.assertTrue((updatedCredit<existingCredit),"SMS credit is not deducted ");

    }
}
