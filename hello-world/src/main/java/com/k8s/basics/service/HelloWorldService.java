package com.k8s.basics.service;

import com.k8s.basics.config.HelloWorldConfig;
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
public class HelloWorldService {

    private final HelloWorldConfig helloWorldConfig;
    private final RestTemplate restTemplate;

    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public HelloWorldService(final HelloWorldConfig helloWorldConfig) {
        this.helloWorldConfig = helloWorldConfig;
        this.restTemplate = new RestTemplate();

        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    void init() {
        this.scheduledExecutorService.scheduleWithFixedDelay(this::sendHelloWorld,
                3,
                this.helloWorldConfig.getMessageInterval(),
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

    public void sendHelloWorld() {
        try {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            final HttpEntity<String> entity = new HttpEntity<String>("Hello World", httpHeaders);

            restTemplate.exchange("http://localhost:5001/roger/post", HttpMethod.POST, entity, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
