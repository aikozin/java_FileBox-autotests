package org.example.filebox.helpers;

import java.sql.Timestamp;

public class DataHepler {

    public static long calculateStartTimeInSec() {
        return System.currentTimeMillis() / 1000;
    }

    public static long calculateEndTimeInSec(long startTime, long minutes) {
        return startTime + minutes * 60;
    }

    public static long convertTimeStampToSec(Timestamp timestamp) {
        return timestamp.getTime() / 1000;
    }

    public static long convertStringTimeStampToSec(String time) {
        return Timestamp.valueOf(time).getTime() / 1000;
    }
}
