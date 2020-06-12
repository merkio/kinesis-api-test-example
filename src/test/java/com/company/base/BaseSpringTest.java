package com.company.base;

import com.company.config.SpringTestConfiguration;
import com.company.utils.LogsUtils;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.slf4j.MDC;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@ContextConfiguration(classes = SpringTestConfiguration.class)
public class BaseSpringTest extends AbstractTestNGSpringContextTests {

    private final ThreadLocal<String> logUUID = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        logUUID.set(UUID.randomUUID().toString());
        MDC.put("fileName", LogsUtils.getLogFilename(method.getName(), logUUID.get()));
        log.info("\n****************\nStarting test {}\n****************", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method, ITestResult testResult) {
        logStopTestName(testResult);
        attachLogsFile(method.getName(), logUUID.get());
    }

    private void logStopTestName(ITestResult result) {
        String resultName;
        switch (result.getStatus()) {
            case ITestResult.SUCCESS: {
                resultName = "PASSED";
                break;
            }
            case ITestResult.FAILURE: {
                resultName = "FAILED";
                break;
            }
            case ITestResult.SKIP: {
                resultName = "SKIPPED";
                break;
            }
            default: {
                resultName = "UNDEFINED";
                break;
            }
        }
        log.info("\n****************\nFinished test {} with result: {}\n****************",
                 result.getMethod().getMethodName(), resultName);
        MDC.remove("fileName");
    }

    @SneakyThrows(IOException.class)
    @Attachment(value = "Logs", type = "text/plain")
    private static byte[] attachLogsFile(String methodName, String logUUID) {
        Path path = Paths.get(LogsUtils.getLogFilename(methodName, logUUID) + ".log");
        return FileUtils.readFileToByteArray(path.toFile());
    }
}
