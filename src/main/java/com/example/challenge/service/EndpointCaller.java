package com.example.challenge.service;

import com.example.challenge.counter.CounterDto;
import com.example.challenge.counter.IdCountHandler;
import com.example.challenge.rest.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class EndpointCaller {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IdCountHandler idCountHandler;

    // calls the remote provided endpoint with a post request
    public HttpStatus callEndpoint(URI endpoint) {

        // get http status and log
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, new CounterDto(idCountHandler.get()), String.class);

        return response.getStatusCode();
    }

}
