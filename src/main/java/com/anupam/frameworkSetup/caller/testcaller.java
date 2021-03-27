package com.anupam.frameworkSetup.caller;

import static io.restassured.RestAssured.given;
import net.minidev.json.JSONObject;

import com.anupam.frameworkSetup.SetupHelper;
import com.anupam.frameworkSetup.Utils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class testcaller  {

    String body = "{\n"
            +"\t“user_name”: ”abc”,\n"
            +"\t“password”: ”def”\n"
            +"}";
    String url1 = "http://webhook.site/53e21180-88c3-4412-8bee-0bb5fe3d4fb6/login";
    String url2 = "http://webhook.site/87f4fd2c-d246-46a6-bbdb-a87e76a0abf0/abc";

public String dopost(){

    return given().header("Content-Type", "application/json").post(url1).then().statusCode(200).extract().asString();
}

public String doget(){
    return given().header("Authorization", "Bearer "+gettoken(dopost())).get(url2).then()
            .statusCode(200).extract().asString();
}

public String gettoken(String postresponse){
    return JsonPath.read(postresponse,"$.session-token");
}

}
