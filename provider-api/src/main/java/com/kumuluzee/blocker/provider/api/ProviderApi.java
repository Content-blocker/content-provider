package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.kumuluz.ee.logs.*;
import com.kumuluz.ee.logs.cdi.*;
import org.eclipse.microprofile.metrics.annotation.Timed;
import java.util.Optional;

@Log
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
    @Path("/makelog")
    public String makelog() {
        final Logger LOG = LogManager.getLogger(ProviderApi.class.getName());
        LOG.info("WroteCustomLogMessage");
        return "Wrote custom log.";
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