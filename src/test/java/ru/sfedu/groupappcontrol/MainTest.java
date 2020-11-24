package ru.sfedu.groupappcontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static Logger log = LogManager.getLogger(Main.class);
    @Test
    void main() {
        log.info("Info");
        log.error("Error");
    }
}