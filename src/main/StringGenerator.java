package main;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

public class StringGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static void generateStrings(long strNumber, int maxStrBitSize, Path file) throws IOException {
        long start = System.currentTimeMillis();
        try (PrintStream ps = new PrintStream(Files.newOutputStream(file))) {
            for (int i = 0; i < strNumber; i++) {
                String randomStr = new BigInteger(maxStrBitSize, random).toString(32);
                ps.println(randomStr);
            }
        }
        long end = System.currentTimeMillis();
        System.out.printf("generation time %d - s%n", (end - start) / 1000);
    }

    public static String generateStr() {
        return new BigInteger(50, random).toString(32);
    }
}
