package com.k8s.basics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {

    @Value("${config.roger.host:localhost:5001}")
    private String rogerServiceHost;

    @Value("${config.roger.post.uri:/roger/post}")
    private String rogerServicePostUri;

    @Value("${config.msg.interval:5}")
    private int messageInterval;

    public String getRogerServiceHost() {
        return rogerServiceHost;
    }

    public String getRogerServicePostUri() {
        return rogerServicePostUri;
    }

    public int getMessageInterval() {
        return messageInterval;
    }

}
