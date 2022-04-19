package com.example.challenge.rest;

import com.example.challenge.counter.IdCountHandler;
import com.example.challenge.rest.exceptions.EndpointFailedException;
import com.example.challenge.service.EndpointCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("/api/smaato/accept")
public class Endpoint {

    private static final Logger LOG = LoggerFactory.getLogger(Endpoint.class);

    @Autowired
    private IdCountHandler idCountHandler;

    @Autowired
    private EndpointCaller endpointCaller;

    // the main get request
    @GetMapping
    public String request(
            @RequestParam(name = "id") BigInteger id,
            @RequestParam(name = "endpoint", required = false) URI endpoint
    ) {
        try {
            // add id uniquely
            idCountHandler.addId(id);

            // call endpoint if parameter available
            if(endpoint != null) {

                HttpStatus status = endpointCaller.callEndpoint(endpoint);

                LOG.info("Got status: " + status + " from endpoint: " + endpoint);
            }

            return "ok";
        } catch (Exception ex) {
            throw new EndpointFailedException();
        }
    }

}
