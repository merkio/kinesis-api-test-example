package com.company.client.kinesis;

import com.amazonaws.services.kinesis.model.Record;

import java.util.Date;
import java.util.List;

public interface IKinesisClient {

    List<Record> getRecordsFromTimestamp(String streamName, Date timestamp);

    List<Record> getRecordsLatest(String streamName);
}
