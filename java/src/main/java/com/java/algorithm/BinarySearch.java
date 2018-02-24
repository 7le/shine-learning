package com.java.algorithm;

import java.util.Arrays;

/**
 * 二分法查找
 */
public class BinarySearch {

    //二分查找的实现
    public static void main(String[] args) {
        int[] nums = {2, 5, 11, 3, 22, 6, 91, 13, 35, 4};
        //对数组进行排序
        Arrays.sort(nums, 0, nums.length - 1);
        int find = BinarySearch.search(nums, 13);
        if (find != -1) {
            System.out.println("找到数值于索引" + find);
        } else {
            System.out.println("找不到数值");
        }
    }

    /**
     * 二分查找
     * 简介:  在二分搜寻法中，从数列的中间开始搜寻，如果这个数小于我们所搜寻的数，由于数列已排序，则该数左边的数一定都小于要搜寻的对象，
     * 所以无需浪费时间在左边的数；如果搜寻的数大于所搜寻的对象，则右边的数无需再搜寻，直接搜寻左边的数。
     *
     * @param nums 待查找数组
     * @num 待查找数
     */
    public static int search(int[] nums, int num) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            //与中间值比较确定在左边还是右边区间,以调整区域
            if (num > nums[mid]) {
                low = mid + 1;
            } else if (num < nums[mid]) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}