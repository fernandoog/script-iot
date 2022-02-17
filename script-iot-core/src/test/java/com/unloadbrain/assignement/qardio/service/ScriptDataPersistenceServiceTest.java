package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ScriptDataPersistenceServiceTest {

    private InfluxDB influxDB;
    private ScriptDataPersistenceService scriptDataPersistenceService;

    @Before
    public void setUp() throws Exception {
        this.influxDB = mock(InfluxDB.class);
        this.scriptDataPersistenceService = new ScriptDataPersistenceService(influxDB);
    }

    @Test
    public void shouldPersistScriptDataMessageDataInDB() throws IllegalAccessException {

        // Given

        ArgumentCaptor<Point> argumentCaptor = ArgumentCaptor.forClass(Point.class);

        ScriptDataMessage message = ScriptDataMessage.builder()
                .deviceId("1234")
                .scriptInFahrenheit(10.0)
                .unixTimestamp(1563142796L)
                .build();

        // When
        scriptDataPersistenceService.trackScriptMessageListener(message);

        // Then

        verify(influxDB).write(argumentCaptor.capture());
        Point point = argumentCaptor.getValue();

        // Point class does not have getters, hence using Reflection.

        Field timeField = ReflectionUtils.findField(Point.class, "time");
        ReflectionUtils.makeAccessible(timeField);

        Field fieldsField = ReflectionUtils.findField(Point.class, "fields");
        ReflectionUtils.makeAccessible(fieldsField);

        assertEquals(1563142796000L, timeField.get(point));
        assertEquals("1234", ((Map) fieldsField.get(point)).get("deviceId"));
        assertEquals(10.0d, (double) ((Map) fieldsField.get(point)).get("scriptInFahrenheit"), 1e-15);
    }
}