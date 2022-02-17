package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.message.ScriptDataMessage;
import com.unloadbrain.assignement.qardio.dto.reqeust.ScriptDataRequest;
import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;
import com.unloadbrain.assignement.qardio.exception.KafkaRecordProducerException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExecutionsDataCollectorServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LoggedInUserService loggedInUserService;
    private KafkaTemplate kafkaTemplate;
    private String topic = "TrackScript";

    private ScriptDataCollectorService scriptDataCollectorService;

    @Before
    public void setUp() throws Exception {
        this.loggedInUserService = mock(LoggedInUserService.class);
        this.kafkaTemplate = mock(KafkaTemplate.class);
        this.scriptDataCollectorService
                = new ScriptDataCollectorService(loggedInUserService, kafkaTemplate, topic);
    }

    @Test
    public void shouldSendKafkaMessage() {

        // Given

        ArgumentCaptor<String> topicArgumentCaptor = ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<ScriptDataMessage> messageArgumentCaptor
                = ArgumentCaptor.forClass(ScriptDataMessage.class);

        ArgumentCaptor<ListenableFutureCallback> callbackArgumentCaptor
                = ArgumentCaptor.forClass(ListenableFutureCallback.class);

        when(loggedInUserService.getLoggedInDeviceId()).thenReturn("1234");

        ListenableFuture futureMock = mock(ListenableFuture.class);
        when(kafkaTemplate.send(anyString(), any(ScriptDataMessage.class))).thenReturn(futureMock);

        ScriptDataRequest scriptDataRequest = ScriptDataRequest.builder()
                .scriptInFahrenheit(20.0)
                .unixTimestamp(1563142796L)
                .build();

        // When
        List<DataCollectionStatusResponse> responses =
                scriptDataCollectorService.collect(Collections.singletonList(scriptDataRequest));

        // Then

        verify(futureMock).addCallback(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onSuccess(mock(SendResult.class));

        verify(kafkaTemplate, times(1))
                .send(topicArgumentCaptor.capture(), messageArgumentCaptor.capture());

        assertEquals("TrackScript", topicArgumentCaptor.getValue());

        assertEquals("1234", messageArgumentCaptor.getValue().getDeviceId());
        assertEquals(20.0, messageArgumentCaptor.getValue().getScriptInFahrenheit(), 1e-15);
        assertEquals(1563142796, messageArgumentCaptor.getValue().getUnixTimestamp());

        assertTrue(responses.get(0).isSuccess());
    }

    @Test
    public void throwExceptionWhenMessageCouldNotBeSent() {

        // Given

        ArgumentCaptor<ListenableFutureCallback> callbackArgumentCaptor
                = ArgumentCaptor.forClass(ListenableFutureCallback.class);

        when(loggedInUserService.getLoggedInDeviceId()).thenReturn("1234");

        ListenableFuture futureMock = mock(ListenableFuture.class);
        when(kafkaTemplate.send(anyString(), any(ScriptDataMessage.class))).thenReturn(futureMock);

        ScriptDataRequest scriptDataRequest = ScriptDataRequest.builder()
                .scriptInFahrenheit(20.0)
                .unixTimestamp(1563142796L)
                .build();

        thrown.expect(KafkaRecordProducerException.class);
        thrown.expectMessage("Script  data could not be sent to Kafka.");

        // When
        DataCollectionStatusResponse response =
                scriptDataCollectorService.collect(scriptDataRequest);

        // Then

        verify(futureMock).addCallback(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onFailure(mock(Throwable.class));

    }

    @Test
    public void shouldReturnSuccessStatusFalseWhenInterruptedExceptionOccursOnFutureGet()
            throws ExecutionException, InterruptedException {

        // Given

        when(loggedInUserService.getLoggedInDeviceId()).thenReturn("1234");

        ListenableFuture futureMock = mock(ListenableFuture.class);
        when(futureMock.get()).thenThrow(mock(InterruptedException.class));

        when(kafkaTemplate.send(anyString(), any(ScriptDataMessage.class))).thenReturn(futureMock);

        ScriptDataRequest scriptDataRequest = ScriptDataRequest.builder()
                .scriptInFahrenheit(20.0)
                .unixTimestamp(1563142796L)
                .build();

        // When
        DataCollectionStatusResponse response =
                scriptDataCollectorService.collect(scriptDataRequest);

        // Then
        assertFalse(response.isSuccess());

    }

    @Test
    public void shouldReturnSuccessStatusFalseWhenExecutionExceptionOccursOnFutureGet()
            throws ExecutionException, InterruptedException {

        // Given

        when(loggedInUserService.getLoggedInDeviceId()).thenReturn("1234");

        ListenableFuture futureMock = mock(ListenableFuture.class);
        when(futureMock.get()).thenThrow(mock(ExecutionException.class));

        when(kafkaTemplate.send(anyString(), any(ScriptDataMessage.class))).thenReturn(futureMock);

        ScriptDataRequest scriptDataRequest = ScriptDataRequest.builder()
                .scriptInFahrenheit(20.0)
                .unixTimestamp(1563142796L)
                .build();

        // When
        DataCollectionStatusResponse response =
                scriptDataCollectorService.collect(scriptDataRequest);

        // Then
        assertFalse(response.isSuccess());

    }

}