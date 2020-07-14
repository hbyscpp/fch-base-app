package com.seaky.fch.utils;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

public final class HashUtils {

    private HashUtils() {

    }

    public static byte[] sha256(String str) {
        return DigestUtils.sha256(str);
    }

    public static byte[] sha256sha256(String str) {
        return DigestUtils.sha256(DigestUtils.sha256(str));
    }

    public static String sha256Hex(String str) {
        return ByteArrayUtils.bytesToHex(DigestUtils.sha256(str));
    }

    public static String sha256sha256Hex(String str) {
        return ByteArrayUtils.bytesToHex(DigestUtils.sha256(DigestUtils.sha256(str)));
    }


    public static byte[] sha256(byte[] bytes) {
        return DigestUtils.sha256(bytes);
    }

    public static byte[] sha256sha256(byte[] bytes) {
        return DigestUtils.sha256(DigestUtils.sha256(bytes));
    }

    public static String sha256Hex(byte[] bytes) {
        return ByteArrayUtils.bytesToHex(DigestUtils.sha256(bytes));
    }
    public static String sha256HexReverse(byte[] bytes) {
        return ByteArrayUtils.bytesToHexReverse(DigestUtils.sha256(bytes));
    }

    public static String sha256sha256Hex(byte[] bytes) {
        return ByteArrayUtils.bytesToHex(DigestUtils.sha256(DigestUtils.sha256(bytes)));
    }

    public static String sha256sha256HexReverse(byte[] bytes) {
        return ByteArrayUtils.bytesToHexReverse(DigestUtils.sha256(DigestUtils.sha256(bytes)));
    }

}
