package com.k8s.basics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RogerConfig {

    @Value("${config.hello.host:localhost:5000}")
    private String helloServiceHost;

    @Value("${config.hello.post.uri:/hello/post}")
    private String helloServicePostUri;

    @Value("${config.msg.interval:5}")
    private int messageInterval;

    public int getMessageInterval() {
        return messageInterval;
    }

    public String getHelloServiceHost() {
        return helloServiceHost;
    }

    public String getHelloServicePostUri() {
        return helloServicePostUri;
    }

}
