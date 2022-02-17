package com.unloadbrain.assignement.qardio.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class to read JSON HTTP request body.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptDataRequest {

    private double scriptInFahrenheit;

    /**
     * In case device go offline and send request once online.
     */
    private long unixTimestamp;
}
