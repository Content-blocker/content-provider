package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;

import java.util.Optional;

@RequestScoped
@Path("/provider")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProviderApi {

    @Inject
    @DiscoverService(value = "ai", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<String> aiUrlString;

    @Inject
    @DiscoverService(value = "ai", environment = "test", version = "1.0.0", accessType = AccessType.DIRECT)
    Optional<String> aiUrlStringDirect;

    @Inject
    @DiscoverService(value = "provider", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<String> providerUrlString;

    @Inject
    @DiscoverService(value = "provider", environment = "test", version = "1.0.0", accessType = AccessType.DIRECT)
    Optional<String> providerUrlStringDirect;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getResources() {
        String links = "";
        if(providerUrlString.isPresent()){
            links += "<a href='"+ providerUrlString.get() + "/provider/integrations'>provider/integrations</a><br>";
        }
        if(aiUrlString.isPresent()){
            links += "<a href='"+ aiUrlString.get() + "/ai'>ai</a>";
        }
        return "Hellow world! <br> I provide. <br>" + links;
    }

    @GET
    @Path("/integrations")
    public String integrations(){
        return "ai gateway: " + aiUrlString.toString() + "\n" +
                "provider gateway: " + providerUrlString.toString() + "\n" +
                "ai direct: " +  aiUrlStringDirect.toString() + "\n" +
                "provider direct: " + providerUrlStringDirect.toString() + "\n";
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