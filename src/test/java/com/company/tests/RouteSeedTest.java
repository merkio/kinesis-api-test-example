package com.company.tests;


import com.amazonaws.services.kinesis.model.Record;
import com.company.base.BaseSpringTest;
import com.company.client.kinesis.IKinesisClient;
import com.company.data.KinesisStream;
import com.company.data.TestData;
import com.company.steps.SeedSteps;
import com.company.steps.StreamSteps;
import com.company.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@Slf4j
public class RouteSeedTest extends BaseSpringTest {

    @Autowired
    private SeedSteps seedSteps;

    @Autowired
    private IKinesisClient kinesisClient;

    @Test(dataProvider = "seedsPositive", dataProviderClass = TestData.class)
    public void seedsStreamPositiveTest(String seed, KinesisStream stream, int responseCode) {
        Date startTime = DateTimeUtils.localDateTimeToDate(LocalDateTime.now());

        String transactionId = seedSteps.getTransactionIdAndCheckResponseCode(seed, responseCode);

        List<Record> records = kinesisClient.getRecordsFromTimestamp(stream.name, startTime);
        assertEquals(records.size(), 1, String.format("Got more than one message from the stream [%s]", stream.name));

        StreamSteps.messagesHasUUIDAndSeed(transactionId, seed, records);

        for (KinesisStream kinesisStream : KinesisStream.allStreamsExcept(KinesisStream.ODD)) {
            assertEquals(kinesisClient.getRecordsLatest(kinesisStream.name).size(), 0,
                         String.format("Got message for seed [%s] from stream [%s]", seed, kinesisStream.name));
        }
    }

    @Test(dataProvider = "seedsNegative", dataProviderClass = TestData.class)
    public void seedsStreamNegativeTest(String seed, int responseCode) {

        String transactionId = seedSteps.getTransactionIdAndCheckResponseCode(seed, responseCode);
        assertNull(transactionId);
    }
}
