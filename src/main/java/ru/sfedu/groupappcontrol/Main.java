package ru.sfedu.groupappcontrol;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.api.DataProviderCsv;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.util.ArrayList;
import java.util.List;

public class Main extends TestEmployee{
    private static Logger log = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws Exception{
        log.info("Info");
        log.error("Error");
        log.debug("Debug");

    }
}
