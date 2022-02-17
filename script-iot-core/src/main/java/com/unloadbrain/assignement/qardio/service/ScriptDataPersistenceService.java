package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Listen to TrackScript Apache Kafka topic and persist to InfluxDB.
 */
@Slf4j
@Service
public class ScriptDataPersistenceService {

    private InfluxDB influxDB;

    public ScriptDataPersistenceService(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    /**
     * Persist script data to InfluxDB.
     *
     * @param message the Apache Kafka message
     */
    @KafkaListener(topics = "TrackScript", groupId = "iot")
    public void trackScriptMessageListener(ScriptDataMessage message) {

        log.info("Received message {} TrackScript topic from group 'iot'", message);

        Point point = Point.measurement("Script")
                .time(message.getUnixTimestamp() * 1000L, TimeUnit.MILLISECONDS)
                .addField("deviceId", message.getDeviceId())
                .addField("scriptInFahrenheit", message.getScriptInFahrenheit())
                .build();

        influxDB.write(point);
    }


}
