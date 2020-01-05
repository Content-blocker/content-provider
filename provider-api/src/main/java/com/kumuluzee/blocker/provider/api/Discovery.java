package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;

import java.util.Optional;

@RequestScoped
@Path("/discovery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Discovery {

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
}
