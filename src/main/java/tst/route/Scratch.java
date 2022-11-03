package tst.route;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scratch {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String text = "superpopitipot";
        String subStr = "oippt";
        int sbStrPos = new Scratch().getSbStrPos(text, subStr);
        System.out.println(sbStrPos);
    }

    int getSbStrPos(String text, String subStr) {
        List<Character> list = new ArrayList<>();
        for (char c : subStr.toCharArray()) {
            list.add(c);
        }
        Map<Character, Integer> ref = list.stream().collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
        list = new ArrayList<>();
        String subString1 = text.substring(0, subStr.length());
        for (char c : subString1.toCharArray()) {
            list.add(c);
        }
        Map<Character, Integer> map = list.stream().collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));

        if (mapsEquals(map, ref)) {
            return 0;
        }
        char[] chars = text.toCharArray();
        for (int i = 1; i < chars.length - subStr.length(); i++) {
            map.computeIfPresent(chars[i - 1], (character, integer) -> integer - 1);
            map.putIfAbsent(chars[i + subStr.length() - 1], 0);
            map.computeIfPresent(chars[i + subStr.length() - 1], (character, integer) -> integer + 1);
            if (mapsEquals(map, ref)) {
                return i;
            }
        }
        return 0;
    }


    private static boolean mapsEquals(Map<Character, Integer> map, Map<Character, Integer> ref) {
        for (Map.Entry<Character, Integer> entry : ref.entrySet()) {
            if (!map.containsKey(entry.getKey())) {
                return false;
            }
            if (!map.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
