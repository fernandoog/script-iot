package com.unloadbrain.assignement.qardio.domain.repository;

import com.unloadbrain.assignement.qardio.domain.model.ScriptQueryResult;
import com.unloadbrain.assignement.qardio.exception.DataAccessException;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScriptDataRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private InfluxDB influxDBMock;
    private ScriptDataRepository repository;

    @Before
    public void setUp() throws Exception {
        this.influxDBMock = mock(InfluxDB.class);
        this.repository = new ScriptDataRepository(influxDBMock, new InfluxDBResultMapper());
    }

    @Test
    public void shouldReturnQueryResultList() {

        // Given

        QueryResult.Series series = new QueryResult.Series();
        series.setName("Script");
        series.setColumns(Arrays.asList("time", "scriptInFahrenheit"));
        series.setValues(Arrays.asList(
                Arrays.asList("2019-07-14T22:19:58Z", 20.0),
                Arrays.asList("2019-07-14T22:19:59Z", 21.0)
        ));
        QueryResult.Result result = new QueryResult.Result();
        result.setSeries(Collections.singletonList(series));

        QueryResult queryResult = new QueryResult() {

            @Override
            public List<Result> getResults() {
                return Collections.singletonList(result);
            }
        };

        when(influxDBMock.query(any(Query.class))).thenReturn(queryResult);

        // When
        List<ScriptQueryResult> scriptQueryResultList
                = repository.getScripts("1234", 1563142796L, 1563142800L);

        // Then

        assertEquals(2, scriptQueryResultList.size());

        assertEquals("2019-07-14T22:19:58Z", scriptQueryResultList.get(0).getTime().toString());
        assertEquals(20.0, scriptQueryResultList.get(0).getScriptInFahrenheit(), 1e-15);

        assertEquals("2019-07-14T22:19:59Z", scriptQueryResultList.get(1).getTime().toString());
        assertEquals(21.0, scriptQueryResultList.get(1).getScriptInFahrenheit(), 1e-15);
    }

    @Test
    public void shouldThrowExceptionWhenQueryResultHasError() {

        // Given

        QueryResult queryResult = new QueryResult() {
            @Override
            public boolean hasError() {
                return true;
            }
        };

        when(influxDBMock.query(any(Query.class))).thenReturn(queryResult);

        thrown.expect(DataAccessException.class);
        thrown.expectMessage("Could not access influxDB because of");

        // When
        repository.getScripts("1234", 1563142796L, 1563142800L);

        // Then
        // Expect test to be passed.
    }
}