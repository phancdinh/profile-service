package org.ht.id.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    private static final String CHARSET = "UTF-8";

    public enum ALGORITHM {
        MD5("MD5"),
        SHA256("SHA-256");

        private final String text;

        ALGORITHM(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * get the md5 of content
     * @param data get the md5 using this content
     * @return the computed md5 hash value
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String... data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5(String.join("", data));
    }

    /**
     * get the md5 of content
     *
     * @param content get the md5 using this content
     * @return the computed md5 hash value
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String content) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return digest(content, ALGORITHM.MD5);
    }

    /**
     * get the hash value of content
     *
     * @param content   get the hash value using this content
     * @param algorithm hash algorithm
     * @return the computed hash value
     */
    public static String digest(String content, ALGORITHM algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toString());
        messageDigest.update(content.getBytes(CHARSET));
        byte[] result = messageDigest.digest();
        return byte2Hex(result);
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                // one byte to double-digit hex
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
