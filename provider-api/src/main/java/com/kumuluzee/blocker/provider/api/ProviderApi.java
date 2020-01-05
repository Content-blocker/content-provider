package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.annotation.Timed;
import javax.ws.rs.core.Response;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;

import java.util.Optional;

@RequestScoped
@Path("/api")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class ProviderApi {
    static Optional<WebTarget> aiTarget = CustomDiscovery.discover("ai", "test", "1.0.0");
    static Optional<WebTarget> providerTarget = CustomDiscovery.discover("provider", "test", "1.0.0");
    static String aiString = (aiTarget.isPresent() ? aiTarget.get().getUri().toString() : "Empty");
    static String providerString = (providerTarget.isPresent() ? providerTarget.get().getUri().toString() : "Empty");

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getResources() {
        String links = "";
        if(providerTarget.isPresent()){
            links += "<a href='"+ providerString + "/provider/api/integrations'>provider/api/integrations</a><br>";
            links += "<a href='"+ providerString + "/provider/discovery'>provider/discovery</a><br>";
        }
        if(aiTarget.isPresent()){
            links += "<a href='"+ aiString + "/ai/api'>ai/api/</a><br>";
        }
        return "Hellow world! <br> I provide. <br>" + links;
    }

    @GET
    @Timed
    @Path("/integrations")
    public String integrations() {
        String out = "";
        if (aiTarget.isPresent()) {
            out += "ai: " + aiString + "\n";
        }
        else out+= "missing ai \n";
        if (providerTarget.isPresent()) {
            out += "provider: " + providerString + "\n";
        }
        else out+= "missing provider \n";
        return out;
    }
}