package com.unloadbrain.assignement.qardio.domain.repository;

import com.unloadbrain.assignement.qardio.domain.model.ScriptQueryResult;
import com.unloadbrain.assignement.qardio.exception.DataAccessException;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data repository class to perform actions (e.g. fetch data) on script  data from InfluxDB.
 */
@Component
public class ScriptDataRepository {

    private final InfluxDB influxDB;
    private final InfluxDBResultMapper resultMapper;

    public ScriptDataRepository(InfluxDB influxDB, InfluxDBResultMapper resultMapper) {
        this.influxDB = influxDB;
        this.resultMapper = resultMapper;
    }

    /**
     * Gets script data filtered with device id, start and end time.
     *
     * @param deviceId  the device id.
     * @param startTime the start unix timestamp.
     * @param endTime   the end unix timestamp.
     * @return the list of ScriptQueryResult DTO.
     */
    public List<ScriptQueryResult> getScripts(String deviceId, long startTime, long endTime) {

        String query = buildQuery(deviceId, startTime, endTime);

        QueryResult queryResult = influxDB.query(new Query(query));
        if (queryResult.hasError()) {
            throw new DataAccessException("Could not access influxDB because of " + queryResult.getError());
        }

        return resultMapper.toPOJO(queryResult, ScriptQueryResult.class);
    }

    /**
     * Build query string from parameters. Note: InfluxDB query is SQL Injection safe.
     *
     * @param deviceId  device id
     * @param startTime start unix timestamp
     * @param endTime   end unix timestamp
     * @return query string with parameters
     * @see <a href="https://github.com/influxdata/influxdb-java/issues/274">Github issue related to SQL Injection</a>
     */
    private String buildQuery(String deviceId, long startTime, long endTime) {

        StringBuilder query = new StringBuilder();
        query.append("Select time, scriptInFahrenheit from Script");
        query.append(" WHERE deviceId = '");
        query.append(deviceId);
        query.append('\'');
        query.append(" AND time >= ");
        query.append(startTime);
        query.append('s');
        query.append(" AND time <= ");
        query.append(endTime);
        query.append('s');

        return query.toString();
    }


}
