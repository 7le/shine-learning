package com.java.algorithm;

/**
 * 寻找最大的K个数
 */
public class FindMaxK {

    public static void main(String[] args) {
        float a[] = {9, 5, 4, 3, 5, 6, 7, 1, 3};
        maxK(a, 3);

    }

    //输出最大的K个数
    public static void maxK(float a[], int k) {
        if (k > a.length) {
            System.out.println("k的值有误，不能大于数组长度！");
            return;
        }
        quickSort(a, 0, a.length - 1);
        System.out.print("最大的" + k + "个数为：");
        for (int i = 0; i < k; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    //快速排序的一次划分
    public static int partition(float a[], int first, int last) {
        float temp;
        int i, j;
        temp = a[first];
        i = first;
        j = last;
        while (i < j) {
            while (i < j && a[j] <= temp) {
                j--;
            }
            if (i < j) a[i++] = a[j];
            while (i < j && a[i] >= temp) {
                i++;
            }
            if (i < j) a[j--] = a[i];
        }
        a[i] = temp;
        return i;
    }

    //快速排序
    public static void quickSort(float a[], int first, int last) {
        if (first >= last)
            return;
        int i = partition(a, first, last);
        quickSort(a, first, i - 1);
        quickSort(a, i + 1, last);
    }
}