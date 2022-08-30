package com.zukxu.demoliteflow.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 工单相关工具类
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 16:53:40
 */
public class WFUtil {

    private WFUtil() {}

    /**
     * @return YYYYMMDD＋GZL＋3位系统编码＋8位流水号
     */
    public static String generateIdentifier() {
        return LocalDateTimeUtil.format(LocalDate.now(), DatePattern.PURE_DATE_PATTERN) + "GZL851" + RandomUtil.randomNumbers(8);
    }

    /**
     * @param dateTime LocalDateTime
     *
     * @return YYYYMMDDHHMMSS
     */
    public static String timeFormat(LocalDateTime dateTime) {
        return LocalDateTimeUtil.format(dateTime, DatePattern.PURE_DATETIME_PATTERN);
    }

    /**
     * @param dateTime String
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static LocalDateTime timeParse(String dateTime) {
        return LocalDateTimeUtil.parse(dateTime, DatePattern.PURE_DATETIME_PATTERN);
    }

    /**
     * @param dateTime LocalDateTime
     *
     * @return YYYYMMDD
     */
    public static String dateFormat(LocalDateTime dateTime) {
        return LocalDateTimeUtil.format(dateTime, DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * @param dateTime String YYYYMMDDHHMMSS
     *
     * @return yyyy-MM-dd
     */
    public static LocalDate dateParse(String dateTime) {
        if(dateTime.length() > 8) {
            return LocalDateTimeUtil.parseDate(dateTime, DatePattern.PURE_DATETIME_PATTERN);
        } else {
            return LocalDateTimeUtil.parseDate(dateTime, DatePattern.PURE_DATE_PATTERN);
        }
    }

}
