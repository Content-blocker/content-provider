package com.kumuluzee.blocker.provider.api;

import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.xml.ws.Response;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService(value = "provider", ttl = 40, pingInterval = 20, environment = "test", version = "1.0.0")
@ApplicationPath("/provider")
public class RestApplication extends Application {

}
