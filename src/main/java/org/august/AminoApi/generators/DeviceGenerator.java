package org.august.AminoApi.generators;

import java.io.IOException;
import java.security.InvalidKeyException;

public final class DeviceGenerator extends GeneratorsCore {

    private static final String device_hex = "E7309ECC0953C6FA60005B2765F99DBBC965C8E9";
    private static final byte[] DEVICE_KEY = parseHexBinary(device_hex);

    private static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String genDeviceId() {
        byte[] combined;
        try {
            combined = combineBytes(PREFIX, urandom());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String hexHmac;
        try {
            hexHmac = bytesToHex(hmacSha1(combined, DEVICE_KEY));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        String sb = bytesToHex(combined);
        String deviceId = sb+hexHmac;
        return deviceId.toUpperCase();
    }
}
