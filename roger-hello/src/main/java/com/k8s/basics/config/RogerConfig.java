package com.k8s.basics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RogerConfig {

    @Value("${config.msg.interval:5}")
    private int messageInterval;

    public int getMessageInterval() {
        return messageInterval;
    }

}
