package tst;

import java.util.Arrays;

public class QuickSort {
    int[] sort(int[] arr, int border) {
        if (arr.length <= border) {
            Arrays.sort(arr);
            return arr;
        } else {
            int[] leftArr = Arrays.copyOfRange(arr, 0, arr.length >>> 1);
            int[] rightArr = Arrays.copyOfRange(arr, arr.length >>> 1, arr.length);
            leftArr = sort(leftArr, border);
            rightArr = sort(rightArr, border);
            return merge2arr(leftArr, rightArr);
        }
    }

    int[] merge2arr(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0;
        int j = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                result[i + j] = arr1[i];
                i += 1;
            } else {
                result[i + j] = arr2[j];
                j += 1;
            }
        }
        if (i < arr1.length) {
            System.arraycopy(arr1, i, result, i + j, arr1.length - i);
        }
        if (j < arr2.length) {
            System.arraycopy(arr2, j, result, j + i, arr2.length - j);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] randomIntArr = new IntArrGenerator().getRandomIntArr(100, 0, 100);
        System.out.println(Arrays.toString(randomIntArr));
        int[] sort = new QuickSort().sort(randomIntArr, 11);
        System.out.println(Arrays.toString(sort));
    }
}
