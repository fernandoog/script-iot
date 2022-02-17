package com.unloadbrain.assignement.qardio.util;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;

/**
 * Script  data Apache Kafka message deserializer class.
 */
public class ScriptDataMessageDeserializer extends DataMessageDeserializer<ScriptDataMessage> {

    public ScriptDataMessageDeserializer() {
        super(ScriptDataMessage.class);
    }

}
