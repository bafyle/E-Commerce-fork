package com.vodafone.ecommerce.util;

import java.util.Random;

public class StringUtils {
    public static String createRandomString(int length)
    {
        Random rand = new Random();
        String randomString = rand.ints(48, 123)
                .filter(num -> (num<58 || num>64) && (num<91 || num>96))
                .limit(length)
                .mapToObj(c -> (char)c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();
        return randomString;
    
    }
}
