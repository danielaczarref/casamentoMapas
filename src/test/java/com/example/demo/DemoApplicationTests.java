package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;


@SpringBootTest
class DemoApplicationTests {

    @BeforeTestClass
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    void contextLoads() {
    }

}
