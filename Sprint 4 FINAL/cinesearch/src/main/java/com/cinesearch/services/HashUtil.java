package com.cinesearch.services;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {
    public static String hashMD5(String input) {
        return DigestUtils.md5Hex(input);
    }
}
