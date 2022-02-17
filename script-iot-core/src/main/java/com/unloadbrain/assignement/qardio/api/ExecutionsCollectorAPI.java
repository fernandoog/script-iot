package com.unloadbrain.assignement.qardio.api;

import com.unloadbrain.assignement.qardio.dto.reqeust.ScriptDataRequest;
import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;
import com.unloadbrain.assignement.qardio.service.ExecutionsDataCollectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * This class provide API endpoints to collect script data from s.
 */
@RestController
public class ExecutionsCollectorAPI {

    private final ExecutionsDataCollectorService scriptExecutionsDataCollectorService;

    public ExecutionsCollectorAPI(ExecutionsDataCollectorService scriptExecutionsDataCollectorService) {
        this.scriptExecutionsDataCollectorService = scriptExecutionsDataCollectorService;
    }

    /**
     * Collect script from s.
     *
     * @param dataRequest the script data request.
     * @return the response entity a boolean indicating request status and HTTP Status 201.
     */
    @PostMapping("/scripts")
    public ResponseEntity<DataCollectionStatusResponse> collectScript(
            @RequestBody ScriptDataRequest dataRequest) {
        return new ResponseEntity(scriptExecutionsDataCollectorService.collect(dataRequest), HttpStatus.CREATED);
    }

    /**
     * Collect scripts in bulk from s in case device to offline.
     *
     * @param dataRequests list of script data request.
     * @return the response entity list of booleans indicating request status and HTTP Status 207.
     */
    @PostMapping("/batch/scripts")
    public ResponseEntity<List<DataCollectionStatusResponse>> collectScripts(
            @RequestBody List<ScriptDataRequest> dataRequests) {
        return new ResponseEntity(scriptExecutionsDataCollectorService.collect(dataRequests), HttpStatus.MULTI_STATUS);
    }
}
