package com.company.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public final class LogsUtils {

    public static String getLogFilename(String methodName, String logUUID) {
        String root = System.getProperty("user.dir");
        String fileName = methodName + "-" + logUUID + "-" + Thread.currentThread().getName();
        Path path = Paths.get(root, "target", "logs", fileName);
        return path.toAbsolutePath().toString();
    }
}
