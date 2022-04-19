package com.example.challenge.scheduler;

import com.example.challenge.counter.IdCountHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LoggingTask {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingTask.class);

    @Autowired
    private IdCountHandler idCountHandler;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // it runs at the beginning of each minute
    @Scheduled(cron = "${scheduling.cron}")
    public void saveId() {

        // send log to Kafka
        kafkaTemplate.send("countLog", "Counter: " + idCountHandler.get());

        // reset counter
        idCountHandler.localReset();
    }

}
