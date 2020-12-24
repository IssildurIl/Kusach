package ru.sfedu.groupappcontrol;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The type Main.
 */
public class Main {
    public static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception{
        log.info("Info");
        log.error("Error");
        log.debug("Debug");

    }
}
