package com.company.data;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "seedsPositive")
    public Object[][] seedsPositive() {
        return new Object[][]{
            {"1", KinesisStream.ODD, HttpStatus.SC_OK},
            {"2", KinesisStream.EVEN, HttpStatus.SC_OK},
            {"0", KinesisStream.EVEN, HttpStatus.SC_OK},
            {"-1", KinesisStream.ODD, HttpStatus.SC_OK},
            {"-22", KinesisStream.EVEN, HttpStatus.SC_OK},
            {String.valueOf(Long.MAX_VALUE), KinesisStream.ODD, HttpStatus.SC_OK},
            {String.valueOf(Long.MIN_VALUE), KinesisStream.EVEN, HttpStatus.SC_OK}
        };
    }

    @DataProvider(name = "seedsNegative")
    public Object[][] seedsNegative() {
        return new Object[][]{
            {"a", HttpStatus.SC_BAD_REQUEST},
            {"", HttpStatus.SC_NOT_FOUND},
            {null, HttpStatus.SC_BAD_REQUEST}
        };
    }
}
