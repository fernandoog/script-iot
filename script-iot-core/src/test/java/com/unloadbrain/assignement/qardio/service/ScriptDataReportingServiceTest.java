package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.domain.model.ScriptQueryResult;
import com.unloadbrain.assignement.qardio.domain.repository.ScriptDataRepository;
import com.unloadbrain.assignement.qardio.dto.response.ScriptDataResponse;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScriptDataReportingServiceTest {

    private LoggedInUserService loggedInUserService;
    private ScriptDataRepository repository;

    private ScriptDataReportingService scriptDataReportingService;

    @Before
    public void setUp() throws Exception {
        this.loggedInUserService = mock(LoggedInUserService.class);
        this.repository = mock(ScriptDataRepository.class);
        this.scriptDataReportingService
                = new ScriptDataReportingService(loggedInUserService, repository);
    }

    @Test
    public void shouldReturnFilteredScriptData() {

        // Given

        when(loggedInUserService.getLoggedInDeviceId()).thenReturn("1234");

        Instant instant = Instant.ofEpochSecond(1563142798);
        ScriptQueryResult queryResult
                = ScriptQueryResult.builder().time(instant).scriptInFahrenheit(10.0).build();

        when(repository.getScripts(anyString(), anyLong(), anyLong()))
                .thenReturn(Collections.singletonList(queryResult));

        // When
        ScriptDataResponse response
                = scriptDataReportingService.getReport(1563142796, 1563142800);

        // Then

        assertEquals("1234", response.getDeviceId());
        assertEquals(10.0d, response.getData().get(0).getScriptInFahrenheit(), 1e-15);
        assertEquals(instant.getEpochSecond(), response.getData().get(0).getUnixTimestamp());
    }
}