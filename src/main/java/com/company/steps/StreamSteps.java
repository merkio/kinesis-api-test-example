package com.company.steps;

import com.amazonaws.services.kinesis.model.Record;
import com.company.messages.SeedMessage;
import com.company.utils.RecordUtils;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.testng.Assert.assertEquals;

@Slf4j
public class StreamSteps {

    @Step("Messages has uuid {uuid} and seed {seed}")
    public static void messagesHasUUIDAndSeed(String uuid, String seed, List<Record> records) {
        records.forEach(record -> {
            SeedMessage message = RecordUtils.loadData(record.getData(), SeedMessage.class);
            log.info("Check data from record [{}]", message);
            assertEquals(message.getUuid(), uuid, String.format("Wrong UUID in message [%s]", record.getSequenceNumber()));
            assertEquals(message.getSeed(), Long.valueOf(seed), String.format("Wrong seed in message [%s]", record.getSequenceNumber()));
        });
    }
}
