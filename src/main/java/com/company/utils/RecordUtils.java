package com.company.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class RecordUtils {

    @SneakyThrows(IOException.class)
    public static <T> T loadData(ByteBuffer buffer, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readerFor(clazz).readValue(buffer.array());
    }
}
