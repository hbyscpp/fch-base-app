package com.seaky.fch.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public final class ByteArrayUtils {

    private ByteArrayUtils() {

    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static byte[] hexToBytes(String hex) {
        try {
            return Hex.decodeHex(hex);
        } catch (DecoderException e) {
            throw new RuntimeException(hex + " to bytes errors");
        }
    }

    public static byte[] hexToBytesReverse(String hex) {
        try {
            byte[] byts= Hex.decodeHex(hex);
            byte[] tmpBytes=new byte[byts.length];
            for(int i=0;i<byts.length;++i)
            {
                tmpBytes[i]=byts[byts.length-i-1];
            }
            return tmpBytes;
        } catch (DecoderException e) {
            throw new RuntimeException(hex + " to bytes errors");
        }
    }


    public static String bytesToHexReverse(byte[] bytes) {
        int len = bytes.length;
        byte[] tmparray = new byte[len];
        for (int i = 0; i < len; ++i) {
            tmparray[i] = bytes[len - i - 1];
        }

        return Hex.encodeHexString(tmparray);
    }


    public static byte[] concatBytes(byte[] bytesOne, byte[] bytesTwo) {
        byte[] newBytes = new byte[bytesOne.length + bytesTwo.length];

        System.arraycopy(bytesOne, 0, newBytes, 0, bytesOne.length);
        System.arraycopy(bytesTwo, 0, newBytes, bytesOne.length, bytesTwo.length);
        return newBytes;
    }

    public static int bytesToShortLE(byte[] bytes) {
        return (int) ((bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8));
    }
    public static int bytesToIntLE(byte[] bytes) {
        return (int) ((bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24));
    }

    public static long bytesToLongLE(byte[] bytes) {
        return readInt64(bytes, 0);
    }

    public static long bytesToUnsignedIntLE(byte[] bytes) {
        return readUint32(bytes, 0);
    }

    public static int readUint16(byte[] bytes, int offset) {
        return (bytes[offset] & 0xff) |
                ((bytes[offset + 1] & 0xff) << 8);
    }

    /**
     * Parse 4 bytes from the byte array (starting at the offset) as unsigned 32-bit integer in little endian format.
     */
    public static long readUint32(byte[] bytes, int offset) {
        return (bytes[offset] & 0xffl) |
                ((bytes[offset + 1] & 0xffl) << 8) |
                ((bytes[offset + 2] & 0xffl) << 16) |
                ((bytes[offset + 3] & 0xffl) << 24);
    }

    public static long readInt64(byte[] bytes, int offset) {
        return (bytes[offset] & 0xffl) |
                ((bytes[offset + 1] & 0xffl) << 8) |
                ((bytes[offset + 2] & 0xffl) << 16) |
                ((bytes[offset + 3] & 0xffl) << 24) |
                ((bytes[offset + 4] & 0xffl) << 32) |
                ((bytes[offset + 5] & 0xffl) << 40) |
                ((bytes[offset + 6] & 0xffl) << 48) |
                ((bytes[offset + 7] & 0xffl) << 56);
    }

    public static void uint16ToByteArrayLE(int val, byte[] out, int offset) {
        out[offset] = (byte) (0xFF & val);
        out[offset + 1] = (byte) (0xFF & (val >> 8));
    }

    /**
     * Write 4 bytes to the byte array (starting at the offset) as unsigned 32-bit integer in little endian format.
     */
    public static void uint32ToByteArrayLE(long val, byte[] out, int offset) {
        out[offset] = (byte) (0xFF & val);
        out[offset + 1] = (byte) (0xFF & (val >> 8));
        out[offset + 2] = (byte) (0xFF & (val >> 16));
        out[offset + 3] = (byte) (0xFF & (val >> 24));
    }

    /**
     * Write 4 bytes to the byte array (starting at the offset) as unsigned 32-bit integer in big endian format.
     */
    public static void uint32ToByteArrayBE(long val, byte[] out, int offset) {
        out[offset] = (byte) (0xFF & (val >> 24));
        out[offset + 1] = (byte) (0xFF & (val >> 16));
        out[offset + 2] = (byte) (0xFF & (val >> 8));
        out[offset + 3] = (byte) (0xFF & val);
    }

    /**
     * Write 8 bytes to the byte array (starting at the offset) as signed 64-bit integer in little endian format.
     */
    public static void int64ToByteArrayLE(long val, byte[] out, int offset) {
        out[offset] = (byte) (0xFF & val);
        out[offset + 1] = (byte) (0xFF & (val >> 8));
        out[offset + 2] = (byte) (0xFF & (val >> 16));
        out[offset + 3] = (byte) (0xFF & (val >> 24));
        out[offset + 4] = (byte) (0xFF & (val >> 32));
        out[offset + 5] = (byte) (0xFF & (val >> 40));
        out[offset + 6] = (byte) (0xFF & (val >> 48));
        out[offset + 7] = (byte) (0xFF & (val >> 56));
    }

}
