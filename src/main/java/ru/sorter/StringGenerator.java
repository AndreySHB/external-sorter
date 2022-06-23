package ru.sorter;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
/**Performs operations with string generation*/
public class StringGenerator {
    private static final SecureRandom random = new SecureRandom();
    /**Writes n=strNumber generated strings into a given file. String generation performs by
     * randomly generated BigInteger, uniformly distributed over the range 0 to (2^maxStrBitSize - 1) and represented in string value with all letters included*/
    public static void generateStrings(long strNumber, int maxStrBitSize, Path file) throws IOException {
        try (PrintStream ps = new PrintStream(Files.newOutputStream(file))) {
            for (int i = 0; i < strNumber; i++) {
                String randomStr = new BigInteger(maxStrBitSize, random).toString(36);
                ps.println(randomStr);
            }
        }
    }
    /**Constructs a randomly generated BigInteger, uniformly distributed over the range 0 to (2^50 - 1) and returns its string representation
     * with all letters included*/
    public static String generateStr() {
        return new BigInteger(50, random).toString(36);
    }
}
