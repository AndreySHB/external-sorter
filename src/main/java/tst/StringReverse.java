package tst;

public class StringReverse {
    String reverse(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length >>> 1; i++) {
            swap(chars, i, chars.length - i - 1);
        }
        return new String(chars);
    }

    void swap(char[] arr, int i, int j) {
        char a = arr[j];
        arr[j] = arr[i];
        arr[i] = a;
    }

    public static void main(String[] args) {
        String str = "12345678";
        StringReverse stringReverse = new StringReverse();
        System.out.println(stringReverse.reverse(str));
    }
}
