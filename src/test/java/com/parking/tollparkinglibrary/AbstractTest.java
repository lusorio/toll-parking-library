package com.parking.tollparkinglibrary;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@Transactional
public abstract class AbstractTest
{
    // extended by tests
}