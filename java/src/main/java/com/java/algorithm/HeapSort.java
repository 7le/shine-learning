package com.java.algorithm;

public class HeapSort {
    private static String[] sort = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L",
            "E", "Z", "K"};

    public static void main(String[] args) {
        buildMaxHeap(sort);
        heapSort(sort);
        print(sort);
    }

    /**
     * 构建堆
     */
    private static void buildMaxHeap(String[] data) {
        //根据最后一个元素获取，开始调整的位置
        int start = getParentIndex(data.length - 1);
        //反复进行调整
        for (int i = start; i >= 0; i--) {
            maxHeap(data, data.length, i);
        }
    }

    /**
     * 此调整为从上到下调整，直到节点超出范围
     */
    private static void maxHeap(String[] data, int heapSize, int index) {
        //取得当前节点的左右节点，当前节点为index
        int left = getChildLeftIndex(index);
        int right = getChildRightIndex(index);
        //对左右节点和当前节点进行比较
        int largest = index;
        if (left < heapSize && data[index].compareTo(data[left]) < 0) {
            largest = left;
        }
        if (right < heapSize && data[largest].compareTo(data[right]) < 0) {
            largest = right;
        }
        //交换位置
        if (largest != index) {
            String temp = data[index];
            data[index] = data[largest];
            data[largest] = temp;
            maxHeap(data, heapSize, largest);
        }
    }

    /**
     * 排序操作
     */
    private static void heapSort(String[] data) {
        //每次循环都能取到一个最大值，该值为根节点
        for (int i = data.length - 1; i > 0; i--) {
            String temp = data[0];
            data[0] = data[i];
            data[i] = temp;
            //每次调整都是从根节点开始i不断减小，保证前一次最大节点不会参与到调整堆
            maxHeap(data, i, 0);
        }
    }

    /**
     * 获取父节点的位置
     */
    private static int getParentIndex(int current) {
        return (current - 1) >> 1;
    }

    /**
     * 获得左子节点的位置
     */
    private static int getChildLeftIndex(int current) {
        return (current << 1) + 1;
    }

    /**
     * 获得右子节点的位置
     */
    private static int getChildRightIndex(int current) {
        return (current << 1) + 2;
    }

    private static void print(String[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
    }
}