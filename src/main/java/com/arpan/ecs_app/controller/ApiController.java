package com.arpan.ecs_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {
    private final HealthEndpoint healthEndpoint;
    private final HealthEndpointProperties healthEndpointProperties;

    public ApiController(HealthEndpoint healthEndpoint, HealthEndpointProperties healthEndpointProperties) {
        this.healthEndpoint = healthEndpoint;
        this.healthEndpointProperties = healthEndpointProperties;
    }

    @GetMapping("/api")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping(HttpServletRequest request) {
        Status healthStatus = healthEndpoint.health().getStatus();
        String description = healthStatus.getCode();
        String httpCode = healthEndpointProperties.getStatus().getHttpMapping().get(healthStatus.getCode()).toString();

        Map<String, String> response = Map.of(
                "responseCode", httpCode,
                "responseDescription", description
        );

        return ResponseEntity.ok(response);
    }
}
