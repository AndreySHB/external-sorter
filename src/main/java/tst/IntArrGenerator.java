package tst;

import java.util.Random;

public class IntArrGenerator {
    private final Random random = new Random();

    int[] getRandomIntArr(int size, int minInclusive, int maxExcluded) {
        int[] ints = new int[size];
        int delta = maxExcluded - minInclusive;
        for (int i = 0; i < size; i++) {
            ints[i] = random.nextInt(delta) + minInclusive;
        }
        return ints;
    }
}
