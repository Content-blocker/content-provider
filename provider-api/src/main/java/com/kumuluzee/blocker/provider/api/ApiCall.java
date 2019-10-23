package com.kumuluzee.blocker.provider.api;

import javax.ws.rs.*;

@Path("/rest")
public class ApiCall {
    @GET
    public String getResources() {
        return "string";
    }
}
