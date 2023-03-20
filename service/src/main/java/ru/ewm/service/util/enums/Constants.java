package ru.ewm.service.util.enums;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String APP_NAME = "ewm-main-service";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static final int COMMENT_START_DATE_OFFSET = 3;

}
