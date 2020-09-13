package com.anupam.frameworkSetup.API;

public class ServiceObjectFactory {

    private PlivoServiceAPI plivoServiceAPI;

    public PlivoServiceAPI plivoServiceAPI(){
        if (plivoServiceAPI == null){
            plivoServiceAPI = new PlivoServiceAPI();
        }
        return plivoServiceAPI;
    }
}
