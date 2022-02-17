package com.unloadbrain.assignement.qardio.util;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScriptDataMessageDeserializerTest {

    @Test
    public void shouldDeserializeScriptDataMessageByteArrayToObject() {

        // Given

        ScriptDataMessageDeserializer deserializer = new ScriptDataMessageDeserializer();

        byte[] data = "{\"deviceId\":\"123\",\"scriptInFahrenheit\":10.0,\"unixTimestamp\":1563142799}".getBytes();

        // When
        ScriptDataMessage message = deserializer.deserialize("any topic", data);

        // Then
        assertNotNull(message);
        assertEquals("123", message.getDeviceId());
        assertEquals(10.0, message.getScriptInFahrenheit(), 1e-15);
        assertEquals(1563142799, message.getUnixTimestamp());
    }
}
