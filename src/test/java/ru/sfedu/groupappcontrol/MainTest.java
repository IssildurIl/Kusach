package ru.sfedu.groupappcontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;

import java.io.IOException;
import java.lang.module.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static Logger log = LogManager.getLogger(Main.class);
    @Test
    void main() {
        try {
            log.info(ConfigurationUtil.getConfigurationEntry(Constants.TEST_NAME));
        } catch (IOException e) {
            log.error(e);
        }
    }
}