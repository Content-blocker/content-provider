package com.kumuluzee.blocker.provider.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/provider")
public class ProviderApi {
    @GET
    public String getResources() {
        return "Hellow world! <br> I provide.";
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String info() {
        String r = new StringBuilder()
                .append("\"clani\": [\"sr3182\"],")
                .append("\"opis_projekta\": \"Projekt implementira naprednega blockerja spletnih vsebin (najprej spoilerjev).\",")
                .append("\"mikrostoritve\": [\"TBDhttp://35.189.96.118:8081/v1/orders\", \"TBDhttp://35.197.209.159:8080/v1/customers/\"],")
                .append("\"github\": [\"https://github.com/Content-blocker/content-provider\", \"https://github.com/Content-blocker/ai\"],")
                .append("\"travis\": [\"https://travis-ci.org/Content-blocker/content-provider\", \"https://travis-ci.org/Content-blocker/ai\"],")
                .append("\"dockerhub\": [\"https://cloud.docker.com/repository/docker/sr123/provider/\", \"https://cloud.docker.com/repository/docker/sr123/ai/\"]")
                .toString();
        return "{" + r + "}";
    }
}