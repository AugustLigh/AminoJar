package org.august.AminoApi.generators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class GeneratorsCore {
    private static final Mac macInstance;
    private static final String prefix_hex = "19";
    protected static final byte[] PREFIX = parseHexBinary(prefix_hex);
    private static final char[] BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();


    static {
        try {
            macInstance = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize HMAC-SHA1", e);
        }
    }

    public static String b64encode(byte[] data) {
        StringBuffer result = new StringBuffer();
        int padding = (3 - (data.length % 3)) % 3; // Calculate padding

        for (int i = 0; i < data.length; i += 3) {
            int a = data[i] & 0xFF;
            int b = i + 1 < data.length ? data[i + 1] & 0xFF : 0;
            int c = i + 2 < data.length ? data[i + 2] & 0xFF : 0;

            int index1 = a >> 2;
            int index2 = ((a & 0x03) << 4) | (b >> 4);
            int index3 = ((b & 0x0F) << 2) | (c >> 6);
            int index4 = c & 0x3F;

            result.append(BASE64_CHARS[index1]);
            result.append(BASE64_CHARS[index2]);
            result.append(BASE64_CHARS[index3]);
            result.append(BASE64_CHARS[index4]);
        }

        // Add padding characters if needed
        for (int i = 0; i < padding; i++) {
            result.setCharAt(result.length() - 1 - i, '=');
        }

        return result.toString();
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return -1;
    }

    public static byte[] parseHexBinary(String hexString) {
        final int len = hexString.length();


        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(hexString.charAt(i));
            int l = hexToBin(hexString.charAt(i + 1));

            out[i / 2] = (byte) (h * 16 + l);
        }

        return out;
    }

    public static byte[] urandom() {
        SecureRandom random = new SecureRandom();
        byte[] rBytes = new byte[20];
        random.nextBytes(rBytes);
        return rBytes;
    }

    public static byte[] hmacSha1(byte[] value, byte[] keyBytes) throws InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        macInstance.init(signingKey);

        return macInstance.doFinal(value);
    }

    public static byte[] combineBytes(byte[] one, byte[] two) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        output.write(one);
        output.write(two);

        return output.toByteArray();
    }

}
