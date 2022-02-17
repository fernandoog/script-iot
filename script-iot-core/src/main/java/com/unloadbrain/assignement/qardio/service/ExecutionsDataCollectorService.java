package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;

import java.util.List;

/**
 * Generic collector service APIs.
 */
public interface ExecutionsDataCollectorService<Data> {

    DataCollectionStatusResponse collect(Data data);

    List<DataCollectionStatusResponse> collect(List<Data> dataList);
}
