package com.example.challenge.counter;

import com.example.challenge.rest.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashSet;

@Component
public class IdCountHandler {

    // main storage of ids
    private final HashSet<BigInteger> idSet;

    private static final Logger LOG = LoggerFactory.getLogger(Endpoint.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public IdCountHandler() {
        idSet = new HashSet<>();
    }

    // add a new id and broadcast if it is a new id
    public void addId(BigInteger id) {
        synchronized (idSet) {
            if(!idSet.add(id)) {
                kafkaTemplate.send("distributedCounter", id.toString()); // only send if not already present
            }
        }
    }

    public int get() {
        synchronized (idSet) {
            return idSet.size();
        }
    }

    // reset the HashSet locally
    public void localReset() {
        synchronized (idSet) {
            idSet.clear();
        }
    }

    // get other instances' id and store them in the HashSet
    @KafkaListener(topics = "distributedCounter", groupId = "0")
    public void distributedCounterListener(String data) {
        try {
            BigInteger id = new BigInteger(data);
            synchronized (idSet) {
                idSet.add(id);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
