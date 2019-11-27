package com.kumuluzee.blocker.provider.api;

import javax.ws.rs.*;

@Path("/call")
public class ProviderApi {
    @GET
    @Path("/here")
    public String hello() {
        return "hello i'm here";
    }

    @GET
    @Path("stuff")
    public String getResources() {
        return "string123";
    }
}
