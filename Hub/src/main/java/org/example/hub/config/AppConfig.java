package org.example.hub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private HashMap<String, String> ports;

    private String host;

    public HashMap<String, String> getPorts() {
        return ports;
    }

    public void setPorts(HashMap<String, String> ports) {
        this.ports = ports;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
