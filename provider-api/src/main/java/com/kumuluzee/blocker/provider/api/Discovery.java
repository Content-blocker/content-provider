package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;

import java.util.Optional;
import java.util.WeakHashMap;

@RequestScoped
@Path("/discovery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Discovery {

    @Inject
    @DiscoverService(value = "ai", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<WebTarget> aiTarget;

    @Inject
    @DiscoverService(value = "fetcher", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<WebTarget> fetcherTarget;

    @Inject
    @DiscoverService(value = "ai", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<String> aiUrlStringGateway;

    @Inject
    @DiscoverService(value = "ai", environment = "test", version = "1.0.0", accessType = AccessType.DIRECT)
    Optional<String> aiUrlStringDirect;

    @Inject
    @DiscoverService(value = "provider", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<String> providerUrlStringGateway;

    @Inject
    @DiscoverService(value = "provider", environment = "test", version = "1.0.0", accessType = AccessType.DIRECT)
    Optional<String> providerUrlStringDirect;

    @Inject
    @DiscoverService(value = "fetcher", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<String> fetcherUrlStringGateway;

    @Inject
    @DiscoverService(value = "fetcher", environment = "test", version = "1.0.0", accessType = AccessType.DIRECT)
    Optional<String> fetcherUrlStringDirect;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getResources() {
        String adresses = "";
        adresses += "provider gateway: ";
        if(providerUrlStringGateway.isPresent()){
            adresses += providerUrlStringGateway.get().toString();
        }
        adresses += "\n";
        adresses += "provider direct: ";
        if(providerUrlStringDirect.isPresent()){
            adresses += providerUrlStringDirect.get().toString();
        }
        adresses += "\n";
        adresses += "ai gateway: ";
        if(aiUrlStringGateway.isPresent()){
            adresses += aiUrlStringGateway.get().toString();
        }
        adresses += "\n";
        adresses += "ai direct: ";
        if(aiUrlStringDirect.isPresent()){
            adresses += aiUrlStringDirect.get().toString();
        }
        adresses += "\n";
        return adresses;
    }

    @GET
    @Path("/ai")
    public Response proxyAi() {
        if(aiTarget.isPresent()){
            WebTarget t = aiTarget.get().path("/ai/api");
            Response r = t.request().get();
            return Response
                    .status(Response.Status.OK)
                    .entity(r.readEntity(String.class))
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
        return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("NOT FOUND")
                    .build();
    }

    @GET
    @Path("/fetcher")
    public Response proxyFetcher() {
        if(fetcherTarget.isPresent()){
            WebTarget t = fetcherTarget.get().path("/fetcher/api");
            Response r = t.request().get();
            return Response
                    .status(Response.Status.OK)
                    .entity(r.readEntity(String.class))
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity("NOT FOUND")
                .build();
    }
}
