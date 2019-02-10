package cn.echocow.xiaoming.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-10 21:52
 */
@SuppressWarnings("all")
public class DateUtil {
    /**
     * 获取中国的现在时间戳
     *
     * @return 字符串
     */
    public static String nowString() {
        return String.valueOf(nowLong());
    }

    /**
     * 获取中国的现在时间戳
     *
     * @return Long 类型
     */
    public static Long nowLong() {
        return Instant.now(Clock.systemUTC())
                .atOffset(ZoneOffset.ofHours(8))
                .toInstant().toEpochMilli();
    }
}
