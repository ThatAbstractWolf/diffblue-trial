package com.diffblue.interview.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * {@link HashUtil} used for all hashing of data
 */
public class HashUtil {

    /**
     * Get a hash from a string
     * @param input - input string
     * @return the hash
     */
    public static String getHash(String input) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
