package com.anupam.frameworkSetup.caller;
import static io.restassured.RestAssured.given;
import net.minidev.json.JSONObject;

import com.anupam.frameworkSetup.SetupHelper;
import com.anupam.frameworkSetup.Utils;

public class BaseCaller {

    public String doPost(final String endPoint,String contentType, final JSONObject jsonBody,
                         final int statusCode) {
        Utils.log("End Point: " + endPoint);
        Utils.log(Utils.ANSI_MAGENTA_BACKGROUND + "Request Payload -->--> " + Utils.ANSI_RESET + jsonBody.toString());
        final String response = given().headers("Content-Type",contentType)
                .auth().basic(SetupHelper.authId, SetupHelper.token)
                .body(jsonBody.toJSONString()).when().post(endPoint).then()
                .statusCode(statusCode)
                .extract().asString();

        Utils.log(Utils.ANSI_MAGENTA_BACKGROUND + "Response Payload <--<-- " + Utils.ANSI_RESET
                        + response.toString());
        return response;
    }

    public String doGet(final String endPoint,String contentType, final JSONObject jsonBody,
                         final int statusCode) {
        Utils.log("End Point: " + endPoint);
        Utils.log(Utils.ANSI_MAGENTA_BACKGROUND + "Request Payload -->--> " + Utils.ANSI_RESET + jsonBody.toString());
        final String response = given().headers("Content-Type",contentType)
                .auth().basic(SetupHelper.authId, SetupHelper.token)
                .body(jsonBody.toJSONString()).when().get(endPoint).then()
                .statusCode(statusCode)
                .extract().asString();

        Utils.log(Utils.ANSI_MAGENTA_BACKGROUND + "Response Payload <--<-- " + Utils.ANSI_RESET
                        + response.toString());
        return response;
    }

    public String doGet(final String endPoint,String contentType, final int statusCode) {
        Utils.log("End Point: " + endPoint);
        final String response = given().headers("Content-Type",contentType)
                .auth().basic(SetupHelper.authId, SetupHelper.token)
                .when().get(endPoint).then().statusCode(statusCode).extract()
                .asString();
        Utils.log( Utils.ANSI_MAGENTA_BACKGROUND + "Response Payload <--<-- " + Utils.ANSI_RESET
                + response.toString());
        return response;
    }

    public String doGet(final String endPoint,String contentType) {
        Utils.log("End Point: " + endPoint);
        final String response = given().headers("Content-Type",contentType)
                .auth().basic(SetupHelper.authId, SetupHelper.token)
                .when().get(endPoint).then().extract()
                .asString();

        final String response1 = given().headers("Content-Type",contentType)
                .auth().basic(SetupHelper.authId, SetupHelper.token)
                .when().get(endPoint).getHeaders().toString();
        System.out.println(response1);
        Utils.log( Utils.ANSI_MAGENTA_BACKGROUND + "Response Payload <--<-- " + Utils.ANSI_RESET
                + response.toString());
        return response;
    }

    public String doGet(final String endPoint) {
        Utils.log("End Point: " + endPoint);
        final String response = given()
                .when().get(endPoint).then().extract()
                .asString();
        System.out.println(response);
        Utils.log( Utils.ANSI_MAGENTA_BACKGROUND + "Response Payload <--<-- " + Utils.ANSI_RESET
                + response.toString());
        return response;
    }
}
