package org.august.AminoApi.generators;


import java.io.IOException;
import java.security.InvalidKeyException;

public class SigGenerator extends GeneratorsCore {
    private static final String sig_hex = "DFA5ED192DDA6E88A12FE12130DC6206B1251E44";
    private static final byte[] SIG_KEY = parseHexBinary(sig_hex);

    public static String genSig(@org.jetbrains.annotations.NotNull String data) {
        byte[] dataBytes = data.getBytes();
        byte[] hashedData;
        try {
            hashedData = hmacSha1(dataBytes, SIG_KEY);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] combined;
        try {
            combined = combineBytes(PREFIX, hashedData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return b64encode(combined);
    }
}
