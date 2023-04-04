package org.example.filebox.helpers;

import org.junit.jupiter.api.Assertions;

import java.util.logging.Logger;

public class LoggerHelper {

    private static String localeClassName;

    public static Logger create(String className) {
        localeClassName = className;
        return Logger.getLogger(className);
    }

    public static <T> void isEquals(String message, T value1, T value2) {
        Logger logger = create(localeClassName);
        logger.info(String.format("%s: %s == %s", message, value1, value2));
        Assertions.assertEquals(value1, value2);
    }

    public static <T> void isEqualsTime(String message, T value1, T value2) {
        Logger logger = create(localeClassName);
        long timeOffset = Math.abs((long) value1 - (long) value2);
        if (timeOffset <= 2) {
            value1 = value2;
        }
        logger.info(String.format("%s: %s == %s", message, value1, value2));
        Assertions.assertEquals(value1, value2);
    }

    public static void isTrue(String message, boolean value) {
        Logger logger = create(localeClassName);
        logger.info(String.format("%s: %s", message, value));
        Assertions.assertTrue(value);
    }
}
