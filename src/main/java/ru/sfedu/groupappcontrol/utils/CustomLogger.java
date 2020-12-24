package ru.sfedu.groupappcontrol.utils;

import org.apache.logging.log4j.LogManager;
import ru.sfedu.groupappcontrol.Constants;

import java.io.IOException;
import java.util.Arrays;

public class CustomLogger {


    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CustomLogger.class);

    public static String startFunc(Object... args) {
        return String.format(Constants.Logger_Start,
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                prepareArgs(args));
    }

    public static String endFunc(Object... args) {
        return String.format(Constants.Logger_END,
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                prepareArgs(args));
    }


    private static String prepareArgs(Object[] args) {
        StringBuilder argsStringBuilder = new StringBuilder();
        if (args.length > 0) {
            argsStringBuilder.append("with arguments: ");
            Arrays.stream(args).forEach(arg -> {
                try {
                    argsStringBuilder.append(arg);
                    argsStringBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.POINT_FOR_LOGGER));
                } catch (IOException e) {
                    log.error(e);
                }
            });
            argsStringBuilder.delete(argsStringBuilder.length() - 1, argsStringBuilder.length());
        }
        return argsStringBuilder.toString();
    }
}
