package com.mef.filter.main.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class CreateCSVImplTest {
final Logger logger= LoggerFactory.getLogger(CreateCSVImplTest.class);

    @org.junit.jupiter.api.Test
    void createCSV() {
        logger.info("CSV Created");
    }

    @org.junit.jupiter.api.Test
    void writeSCV() {
        logger.info("CSV Updated");
    }
}