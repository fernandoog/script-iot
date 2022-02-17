package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.domain.model.ScriptQueryResult;
import com.unloadbrain.assignement.qardio.domain.repository.ScriptDataRepository;
import com.unloadbrain.assignement.qardio.dto.response.ScriptDataResponse;
import com.unloadbrain.assignement.qardio.dto.response.ScriptDataResponse.ScriptData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Script  data reporting service.
 */
@Slf4j
@Service
public class ScriptDataReportingService {

    private final LoggedInUserService loggedInUserService;
    private final ScriptDataRepository repository;

    public ScriptDataReportingService(LoggedInUserService loggedInUserService,
                                                 ScriptDataRepository repository) {
        this.loggedInUserService = loggedInUserService;
        this.repository = repository;
    }

    /**
     * Generate report filtered with start and end time of logged in device.
     *
     * @param startTime the start unix timestamp.
     * @param endTime   the end unix timestamp.
     * @return the DTO of aggregated data.
     */
    public ScriptDataResponse getReport(long startTime, long endTime) {

        List<ScriptQueryResult> queryResults
                = repository.getScripts(loggedInUserService.getLoggedInDeviceId(), startTime, endTime);

        List<ScriptData> dataPoints = queryResults.stream()
                .map(data -> new ScriptData(data.getTime().getEpochSecond(), data.getScriptInFahrenheit()))
                .collect(Collectors.toList());

        return ScriptDataResponse.builder()
                .deviceId(loggedInUserService.getLoggedInDeviceId())
                .data(dataPoints)
                .build();

    }


}
