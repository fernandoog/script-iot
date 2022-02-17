package com.unloadbrain.assignement.qardio.util;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScriptDataMessageSerializerTest {

    @Test
    public void shouldSerializeScriptDataMessageObject() {

        // Given

        ScriptDataMessageSerializer serializer = new ScriptDataMessageSerializer();

        ScriptDataMessage message = ScriptDataMessage.builder()
                .deviceId("123")
                .scriptInFahrenheit(10)
                .unixTimestamp(1563142799L)
                .build();

        // When
        byte[] serializedObject = serializer.serialize("any topic", message);

        // Then
        assertNotNull(serializedObject);
        assertEquals("{\"deviceId\":\"123\",\"scriptInFahrenheit\":10.0,\"unixTimestamp\":1563142799}",
                new String(serializedObject));
    }
}
