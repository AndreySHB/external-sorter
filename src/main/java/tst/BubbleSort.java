package tst;

import java.util.Arrays;

public class BubbleSort {
    void sort(int[] arr) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    sorted = false;
                }
            }
        }
    }

    void swap(int[] arr, int i, int j) {
        int a = arr[j];
        arr[j] = arr[i];
        arr[i] = a;
    }

    public static void main(String[] args) {
        int[] arr2sort = new IntArrGenerator().getRandomIntArr(100, 0, 100);
        System.out.println(Arrays.toString(arr2sort));
        new BubbleSort().sort(arr2sort);
        System.out.println(Arrays.toString(arr2sort));
    }
}
