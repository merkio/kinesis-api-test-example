package com.company.steps;

import com.company.client.Headers;
import com.company.client.Paths;
import com.company.client.RestAssuredRequest;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeedSteps {

    private final RestAssuredRequest<?> request;

    @Step("Get transactionId and check response code {responseCode} for seed {seed}")
    public String getTransactionIdAndCheckResponseCode(String seed, int responseCode) {
        return request.path(String.format("%s/%s", Paths.ROUTE, seed))
            .readAsResponse()
            .then()
            .statusCode(responseCode)
            .extract()
            .response()
            .getHeader(Headers.X_TRANSACTION_ID);
    }
}
