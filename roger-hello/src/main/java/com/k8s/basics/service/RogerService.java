package com.k8s.basics.service;

import com.k8s.basics.config.RogerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class RogerService {
    private final static String ROGER_MSG = "I got it, roger";

    private final RogerConfig rogerConfig;
    private final RestTemplate restTemplate;

    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public RogerService(final RogerConfig rogerConfig) {
        this.rogerConfig = rogerConfig;
        this.restTemplate = new RestTemplate();

        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    void init() {
        this.scheduledExecutorService.scheduleWithFixedDelay(this::sendRogerIt,
                2,
                this.rogerConfig.getMessageInterval(),
                TimeUnit.SECONDS);
    }

    @PreDestroy
    void preDestroy() {
        this.scheduledExecutorService.shutdown();
        try {
            if (!this.scheduledExecutorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                this.scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            this.scheduledExecutorService.shutdownNow();
        }
    }

    private void sendRogerIt() {
        try {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            final HttpEntity<String> entity = new HttpEntity<String>(ROGER_MSG, httpHeaders);

            restTemplate.exchange(this.getHelloPostUrl(), HttpMethod.POST, entity, String.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getHelloPostUrl() {
        return String.format("%s://%s%s",
                "http",
                this.rogerConfig.getHelloServiceHost(),
                this.rogerConfig.getHelloServicePostUri());
    }

}
