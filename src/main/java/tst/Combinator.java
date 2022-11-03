package tst;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Combinator {
    //0-empty bucket, 1-filled bucket
    Set<String> distributekObjects2nBuckets(int kObj, int nBuck) {
        if (kObj > 1) {
            Set<String> newSet = new HashSet<>();
            Set<String> oldSet = distributekObjects2nBuckets(kObj - 1, nBuck);
            for (String s : oldSet) {
                char[] chars = s.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == '0') {
                        chars[i] = '1';
                        newSet.add(new String(chars));
                        chars[i] = '0';
                    }
                }
            }
            return newSet;
        } else {
            Set<String> set = new HashSet<>();
            char[] chars = "0".repeat(nBuck).toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '1';
                set.add(new String(chars));
                chars[i] = '0';
            }
            return set;
        }
    }

    public static void main(String[] args) {
        Set<String> strings = new Combinator().distributekObjects2nBuckets(3, 6);
        strings.stream()
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);
    }
}
