package tst;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class BracketSequence {
    Set<String> generateBracketSequence(int n) {
        if (n == 1) {
            return Set.of("()");
        } else {
            Set<String> oldSet = generateBracketSequence(n - 1);
            Set<String> newSet = new HashSet<>();
            for (String s : oldSet) {
                for (int i = 1; i < s.length() + 1; i++) {
                    StringBuilder sb = new StringBuilder(s);
                    sb.insert(i, "(");
                    String sTransit = sb.toString();
                    for (int j = i + 1; j < sTransit.length() + 1; j++) {
                        StringBuilder sb2 = new StringBuilder(sTransit);
                        sb2.insert(j, ")");
                        newSet.add(sb2.toString());
                    }
                }
            }
            return newSet;
        }
    }

    public static void main(String[] args) {
        Set<String> strings = new BracketSequence().generateBracketSequence(3);
        strings.stream()
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);
    }
}
