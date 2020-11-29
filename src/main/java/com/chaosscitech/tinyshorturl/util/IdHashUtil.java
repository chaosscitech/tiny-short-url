package com.chaosscitech.tinyshorturl.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author zach.chow
 * @date 2020/11/12
 */
public class IdHashUtil {

    private static final String DIGITAL_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final byte[] DIGITAL;
    private static final int RADIX;
    private static final int LENGTH = 7;
    private static final int OFFSET = 27;

    static {
        try {
            DIGITAL = DIGITAL_STRING.getBytes("ascii");
            Arrays.sort(DIGITAL);
            RADIX = DIGITAL.length;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 将Long类型ID编码成String类型ID
     * @param id Long类型ID
     * @return String类型ID
     */
    public static String encode(long id) {
        long value = id;
        ByteBuffer buf = ByteBuffer.allocate(LENGTH);
        for (int i = 0; i < LENGTH; i++) {

            int mod = (int) (value % RADIX);
            int pos = (mod + (OFFSET << i)) % RADIX;
            buf.put(DIGITAL[pos]);
            value = value / RADIX;
        }
        byte[] result = buf.array();
        ArrayUtils.reverse(result);
        return new String(result);
    }

    /**
     * 将String类型ID成解码Long类型ID
     * @param code String类型ID
     * @return Long类型ID
     */
    public static long decode(String code) {
        long value = 0;
        byte[] buf = code.getBytes();
        for (int i = 0; i < LENGTH; i++) {
            byte digital = buf[i];
            int index = Arrays.binarySearch(DIGITAL, digital);

            index = index - (OFFSET << (LENGTH - i - 1));
            index = index % RADIX;
            if (index < 0) {
                index = index + RADIX;
            }
            value = value * RADIX + index;
        }
        return value;

    }

    /**
     * 校验String类型ID是否合法
     * @param code String类型ID
     * @return
     */
    public static boolean checkEncodedString(String code) {
        boolean result = true;
        if (StringUtils.isBlank(code)) {
            result = false;
        } else if (code.length() != LENGTH) {
            result = false;
        } else {
            result = StringUtils.containsOnly(code, DIGITAL_STRING);
        }
        return result;
    }
}