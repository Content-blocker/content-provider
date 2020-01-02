package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.kumuluz.ee.discovery.annotations.DiscoverService;

import java.util.Optional;

@RequestScoped
@Path("/provider")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProviderApi {

    @GET
    public String getResources() {
        return "Hellow world! <br> I provide.";
    }

    @Inject
    @DiscoverService(value = "ai", environment = "test", version = "1.0.0")
    Optional<String> aiUrlString;

    @Inject
    @DiscoverService(value = "provider", environment = "test", version = "1.0.0")
    Optional<String> providerUrlString;

    @GET
    @Path("/integrations")
    public String integrations(){
        return "ai: " + aiUrlString.toString() + "\n" +
                "provider: " + providerUrlString.toString() + "\n";
    }

    static String ipProvider = "00";
    static String  ipAi = "11";

    @GET
    @Path("/providerip/{newIpProvider}")
    public String setIpProvider(@PathParam("newIpProvider") String newIpProvider) {
        ipProvider = newIpProvider;
        return "New provider ip is: " + ipProvider;
    }

    @GET
    @Path("/aiip/{newIpAi}")
    public String setAi(@PathParam("newIpAi") String newIpAi) {
        ipAi = newIpAi;
        return "New ai ip is: " + ipAi;
    }


    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String info() {
        String r = new StringBuilder()
                .append("\"clani\": [\"sr3182\"],")
                .append("\"opis_projekta\": \"Projekt implementira naprednega blockerja spletnih vsebin (najprej spoilerjev).\",")
                .append("\"mikrostoritve\": [\"http://" + ipProvider + ":8080/provider\", \"http://" + ipAi + ":8080/ai\"],")
                .append("\"github\": [\"https://github.com/Content-blocker/content-provider\", \"https://github.com/Content-blocker/ai\"],")
                .append("\"travis\": [\"https://travis-ci.org/Content-blocker/content-provider\", \"https://travis-ci.org/Content-blocker/ai\"],")
                .append("\"dockerhub\": [\"https://cloud.docker.com/repository/docker/sr123/provider/\", \"https://cloud.docker.com/repository/docker/sr123/ai/\"]")
                .toString();
        return "{" + r + "}";
    }
}