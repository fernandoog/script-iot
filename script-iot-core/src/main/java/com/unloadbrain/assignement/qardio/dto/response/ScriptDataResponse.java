package com.unloadbrain.assignement.qardio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class to return script reporting data as HTTP response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptDataResponse {

    private String deviceId;
    private List<ScriptData> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScriptData {

        private long unixTimestamp;
        private double scriptInFahrenheit;
    }
}
