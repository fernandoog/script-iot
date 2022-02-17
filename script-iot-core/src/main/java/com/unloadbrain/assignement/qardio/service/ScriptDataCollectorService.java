package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;
import com.unloadbrain.assignement.qardio.dto.reqeust.ScriptDataRequest;
import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;
import com.unloadbrain.assignement.qardio.exception.KafkaRecordProducerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Collect script  data and put it to Apache Kafka record.
 */
@Slf4j
@Service
public class ScriptDataCollectorService implements ExecutionsDataCollectorService<ScriptDataRequest> {

    private final LoggedInUserService loggedInUserService;
    private final KafkaTemplate kafkaTemplate;
    private final String topic;

    public ScriptDataCollectorService(LoggedInUserService loggedInUserService,
                                                 KafkaTemplate kafkaTemplate,
                                                 @Value("${app.kafka.topic.script-}") String topic) {
        this.loggedInUserService = loggedInUserService;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    /**
     * Read script  data and put it to Apache Kafka record.
     *
     * @param scriptDataRequest script  data.
     */
    @Override
    public DataCollectionStatusResponse collect(ScriptDataRequest scriptDataRequest) {

        ScriptDataMessage message = ScriptDataMessage.builder()
                .deviceId(loggedInUserService.getLoggedInDeviceId())
                .scriptInFahrenheit(scriptDataRequest.getScriptInFahrenheit())
                .unixTimestamp(scriptDataRequest.getUnixTimestamp())
                .build();

        ListenableFuture<SendResult<String, ScriptDataMessage>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ScriptDataMessage>>() {

            @Override
            public void onSuccess(SendResult<String, ScriptDataMessage> result) {
                log.info("Script  data successfully sent to Kafka.");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Script  data could not be sent to Kafka.", ex);
                throw new KafkaRecordProducerException("Script  data could not be sent to Kafka.");
            }
        });

        try {
            // Blocking call to return success status.
            future.get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error("Apache Kafka ListenableFuture get() exception.", ex);
            return DataCollectionStatusResponse.builder().success(false).build();
        }

        return DataCollectionStatusResponse.builder().success(true).build();
    }

    /**
     * Read script  data in bulk and put it to Apache Kafka record.
     *
     * @param scriptDataRequests list of script  data.
     */
    @Override
    public List<DataCollectionStatusResponse> collect(List<ScriptDataRequest> scriptDataRequests) {
        return scriptDataRequests.stream()
                .map(request -> collect(request))
                .collect(Collectors.toList());
    }
}
