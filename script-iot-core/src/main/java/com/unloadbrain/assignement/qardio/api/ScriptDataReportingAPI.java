package com.unloadbrain.assignement.qardio.api;

import com.unloadbrain.assignement.qardio.dto.response.ScriptDataResponse;
import com.unloadbrain.assignement.qardio.service.ScriptDataReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class provide API endpoint to processed report of script  data.
 */
@RestController
public class ScriptDataReportingAPI {

    private final ScriptDataReportingService reportingService;

    public ScriptDataReportingAPI(ScriptDataReportingService reportingService) {
        this.reportingService = reportingService;
    }

    /**
     * Return script  data with start and end time limit.
     *
     * @param startTime the start time in unix timestamp.
     * @param endTime   the end time unix timestamp.
     * @return the script  data response DTO.
     */
    @GetMapping("/scripts")
    public ScriptDataResponse getScriptData(@RequestParam long startTime, @RequestParam long endTime) {
        return reportingService.getReport(startTime, endTime);
    }
}
