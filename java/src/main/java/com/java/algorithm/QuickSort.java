package com.java.algorithm;

/**
 * 快排
 */
public class QuickSort {

    public static void main(String[] args) {
        int a[] = {9, 5, 4, 3, 5, 6, 7, 1, 3};
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(int s[], int l, int r) {
        if (l < r) {
            int i = l, j = r, x = s[l];
            while (i < j) {
                // 从右向左找第一个小于x的数
                while (i < j && s[j] >= x) {
                    j--;
                }
                if (i < j) {
                    //交换
                    s[i++] = s[j];
                }
                // 从左向右找第一个大于等于x的数
                while (i < j && s[i] < x) {
                    i++;
                }
                if (i < j) {
                    //交换
                    s[j--] = s[i];
                }
            }
            s[i] = x;
            // 递归调用
            quickSort(s, l, i - 1);
            quickSort(s, i + 1, r);
        }
    }
}
