package com.kumuluzee.blocker.provider.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;
import com.kumuluz.ee.logs.*;
import com.kumuluz.ee.logs.cdi.*;
import com.kumuluzee.blocker.provider.ContentService.ContentEntity;
import com.kumuluzee.blocker.provider.ContentService.ContentService;
import org.eclipse.microprofile.metrics.annotation.Timed;
import java.util.*;


@Log
@RequestScoped
@CrossOrigin
@Path("/api")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class ProviderApi {
    static Optional<WebTarget> aiTarget = CustomDiscovery.discover("ai", "test", "1.0.0");
    static Optional<WebTarget> providerTarget = CustomDiscovery.discover("provider", "test", "1.0.0");
    static Optional<WebTarget> fetcherTarget = CustomDiscovery.discover("fetcher", "test", "1.0.0");
    static String aiString = (aiTarget.isPresent() ? aiTarget.get().getUri().toString() : "Empty");
    static String providerString = (providerTarget.isPresent() ? providerTarget.get().getUri().toString() : "Empty");
    static String fetcherString = (fetcherTarget.isPresent() ? fetcherTarget.get().getUri().toString() : "Empty");

    @Inject
    @DiscoverService(value = "provider", environment = "test", version = "1.0.0", accessType = AccessType.GATEWAY)
    Optional<WebTarget> providerLocalTarget;

    @Inject
    ContentService contentService;

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getResources() {
        String links = "";
        if(providerTarget.isPresent()){
            links += "<a href='"+ providerString + "/provider/api/integrations'>provider/api/integrations</a><br>";
            links += "<a href='"+ providerString + "/provider/api/integrations'>provider/api/makelog</a><br>";
            links += "<a href='"+ providerString + "/provider/discovery'>provider/discovery</a><br>";
            links += "<a href='"+ providerString + "/provider/health'>provider/health</a><br>";
            links += "<br>";
            links += "<a href='"+ providerString + "/provider/api/give-ebola'>provider/api/give-ebola</a><br>";
            links += "<a href='"+ providerString + "/provider/api/give-cancer'>provider/api/give-cancer</a><br>";
            links += "<a href='"+ providerString + "/provider/api/cure-ebola'>provider/api/cure-ebola</a><br>";
            links += "<a href='"+ providerString + "/provider/api/cure-cancer'>provider/api/cure-cancer</a><br>";
            links += "<br>";
            links += "<a href='"+ providerString + "/provider/api/getBlockContent'>provider/api/getBlockContent</a><br>";
        }
        return "Hellow world! <br> I provide. <br><br>" + links;
    }

    @GET
    @Timed
    @Path("/give-ebola")
    public String giveebola() {
        CustomHealthCheck.has_ebola = true;
        if(providerLocalTarget.isPresent()){
            String healthCheck = providerLocalTarget.get().path("/provider/health/live").request().get().readEntity(String.class);
            return "Gave it ebola. \n" + healthCheck;
        }
        return "Did not discover provider.";
    }

    @GET
    @Timed
    @Path("/give-cancer")
    public String givecancer() {
        CustomHealthCheck.has_cancer = true;
        if(providerLocalTarget.isPresent()){
            String healthCheck = providerLocalTarget.get().path("/provider/health/live").request().get().readEntity(String.class);
            return "Gave it cancer. \n" + healthCheck;
        }
        return "Did not discover provider.";
    }

    @GET
    @Timed
    @Path("/cure-ebola")
    public String cureebola() {
        CustomHealthCheck.has_ebola = false;
        if(providerLocalTarget.isPresent()){
            String healthCheck = providerLocalTarget.get().path("/provider/health/live").request().get().readEntity(String.class);
            return "Cured it from ebola. \n" + healthCheck;
        }
        return "Did not discover provider.";
    }

    @GET
    @Timed
    @Path("/cure-cancer")
    public String curecancer() {
        CustomHealthCheck.has_cancer = false;
        if(providerLocalTarget.isPresent()){
            String healthCheck = providerLocalTarget.get().path("/provider/health/live").request().get().readEntity(String.class);
            return "Cured it from cancer. \n" + healthCheck;
        }
        return "Did not discover provider.";
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
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/integrations")
    public String integrations() {
        String out = "<body> <h5> ai integrations </h5>";

        out += "<div> ai: ";
        if (aiTarget.isPresent()) {
            out += "ai: " + aiString + "<br>";
            out += "<a href='"+ aiString + "/ai/api'>ai/api</a><br>";
        }
        else out+= "missing <br>";
        out += "</div>";

        out += "<div> provider: ";
        if (aiTarget.isPresent()) {
            out += "provider: " + providerString + "<br>";
            out += "<a href='"+ providerString + "/provider/api'>provider/api</a><br>";
        }
        else out+= "missing <br>";
        out += "</div>";

        out += "<div> fetcher: ";
        if (aiTarget.isPresent()) {
            out += "fetcher: " + fetcherString + "<br>";
            out += "<a href='"+ fetcherString + "/fetcher/api'>fetcher/api</a><br>";
        }
        else out+= "missing <br>";
        out += "</div>";

        out += "</body>";
        return out;
    }

    @GET
    @Timed
    @Path("/getBlockContent")
    public String getBlockContent() {
        List<ContentEntity> blockContent = contentService.getContentEntities();
        String out = "{";
        out += blockContent
                .stream()
                .map(e -> e.to_string())
                .reduce("", (c, e) -> c + e + "\n");
        out += "}";
        return out;
    }
}