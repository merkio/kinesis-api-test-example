package com.company.client.kinesis;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KinesisTestClient implements IKinesisClient {

    private final AmazonKinesis client;

    public List<Record> getRecordsFromTimestamp(String streamName, Date timestamp) {
        log.info("Get records from stream [{}] from timestamp [{}]", streamName, timestamp);
        List<Record> records = new LinkedList<>();
        client
            .describeStream(streamName)
            .getStreamDescription()
            .getShards()
            .forEach(shard -> {
                         GetShardIteratorRequest shardIteratorRequest = new GetShardIteratorRequest()
                             .withStreamName(streamName)
                             .withShardIteratorType(ShardIteratorType.AT_TIMESTAMP)
                             .withTimestamp(timestamp)
                             .withShardId(shard.getShardId());

                         GetRecordsResult recordsResult = getRecordResultWithShardIterator(shardIteratorRequest);
                         records.addAll(recordsResult.getRecords());
                     }
            );
        return records;
    }

    public List<Record> getRecordsLatest(String streamName) {
        log.info("Get latest records from stream [{}]", streamName);
        List<Record> records = new LinkedList<>();
        client
            .describeStream(streamName)
            .getStreamDescription()
            .getShards()
            .forEach(shard -> {
                         GetShardIteratorRequest shardIteratorRequest = new GetShardIteratorRequest()
                             .withStreamName(streamName)
                             .withShardIteratorType(ShardIteratorType.LATEST)
                             .withShardId(shard.getShardId());

                         GetRecordsResult recordsResult = getRecordResultWithShardIterator(shardIteratorRequest);
                         records.addAll(recordsResult.getRecords());
                     }
            );
        return records;
    }

    private GetRecordsResult getRecordResultWithShardIterator(GetShardIteratorRequest shardIteratorRequest) {
        log.info("Get shard iterator for request \n[{}]", shardIteratorRequest);
        GetShardIteratorResult shardIteratorResult = client.getShardIterator(shardIteratorRequest);
        String shardIterator = shardIteratorResult.getShardIterator();
        GetRecordsRequest recordsRequest = new GetRecordsRequest()
            .withLimit(25)
            .withShardIterator(shardIterator);

        return client.getRecords(recordsRequest);
    }
}
