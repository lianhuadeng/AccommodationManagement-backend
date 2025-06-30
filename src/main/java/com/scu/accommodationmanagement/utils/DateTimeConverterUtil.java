package com.scu.accommodationmanagement.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverterUtil {

    // 输入和输出的日期时间格式
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

    /**
     * 将中文格式的日期时间字符串解析为LocalDateTime对象
     *
     * @param chineseDateTime 中文格式的日期时间字符串（例如："2025年06月26日 10:55:11"）
     * @return 解析后的LocalDateTime对象
     * @throws DateTimeParseException 当输入格式不符合中文格式时抛出异常
     */
    public static LocalDateTime parseFromChineseDateTime(String chineseDateTime) {
        return LocalDateTime.parse(chineseDateTime, OUTPUT_FORMATTER);
    }

    /**
     * 将ISO格式的日期时间字符串转换为中文日期时间格式
     *
     * @param isoDateTime 输入的ISO格式字符串（例如："2025-06-26T10:55:11"）
     * @return 中文格式的日期时间字符串（例如："2025年06月26日 10:55:11"）
     * @throws DateTimeParseException 当输入格式不符合ISO标准时抛出异常
     */
    public static String convertToChineseDateTime(String isoDateTime) {
        LocalDateTime dateTime = LocalDateTime.parse(isoDateTime, INPUT_FORMATTER);
        return dateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * 将LocalDateTime对象转换为中文日期时间格式
     *
     * @param dateTime LocalDateTime对象
     * @return 中文格式的日期时间字符串（例如："2025年06月26日 10:55:11"）
     */
    public static String convertToChineseDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * 可选：使用当前时间生成中文格式的日期时间字符串
     *
     * @return 当前时间的中文格式字符串
     */
    public static String currentTimeInChineseFormat() {
        return LocalDateTime.now().format(OUTPUT_FORMATTER);
    }
}