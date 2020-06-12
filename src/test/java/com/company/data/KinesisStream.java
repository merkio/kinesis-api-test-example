package com.company.data;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum KinesisStream {

    ODD("li-stream-odd"),
    EVEN("li-stream-even");

    public final String name;

    public static List<KinesisStream> allStreamsExcept(KinesisStream stream) {
        return Arrays.stream(KinesisStream.values()).filter(s -> s != stream).collect(Collectors.toList());
    }
}

