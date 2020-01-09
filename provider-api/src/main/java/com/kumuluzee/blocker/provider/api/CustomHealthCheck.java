package com.kumuluzee.blocker.provider.api;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Liveness
@ApplicationScoped
public class CustomHealthCheck implements HealthCheck {

    public static boolean has_ebola = false;
    public static boolean has_cancer = false;

    @Override
    public HealthCheckResponse call() {
        if (has_cancer && has_ebola) {
            int[] ar = {};
            int t = ar[100];
            int k = ar[t];
            int f = ar[-k];
            return HealthCheckResponse.down("Custom check - cancer and ebola" + f);
        }
        if (has_cancer) {
            return HealthCheckResponse.down("Custom check - cancer");
        }
        if (has_ebola) {
            return HealthCheckResponse.down("Custom check - ebola");
        }
        return HealthCheckResponse.up("Custom check");
    }
}
