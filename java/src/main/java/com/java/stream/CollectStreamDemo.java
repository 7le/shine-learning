package com.java.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 学习
 * Created by 7le on 2017/8/19 .
 */
public class CollectStreamDemo {

    //平方数
    @Test
    public void demo1(){
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums =nums.stream()
                .map(integer -> integer * integer)
                .collect(Collectors.toList());
        System.out.println(squareNums);
    }

    //求平均值
    @Test
    public void demo2(){
        List<Integer> list = Arrays.asList(1,2,3,4);
        Double result = list.stream()
                .collect(Collectors.averagingDouble(d-> d));
        System.out.println(result);
    }

    //过滤
    @Test
    public void demo3(){
        List<String> filterLists = new ArrayList<>();
        filterLists.add("");
        filterLists.add("a");
        filterLists.add("b");
        List afterFilterLists = filterLists.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        System.out.println(afterFilterLists);
    }

    //循环操作(forEach)
    @Test
    public void demo4(){
        List<String> forEachLists = new ArrayList<>();
        forEachLists.add("a");
        forEachLists.add("b");
        forEachLists.add("c");
        forEachLists.stream().forEach(s-> System.out.println(s));
    }

    //排序
    @Test
    public void demo5(){
        List<Integer> sortLists = new ArrayList<>();
        sortLists.add(1);
        sortLists.add(4);
        sortLists.add(6);
        sortLists.add(3);
        sortLists.add(2);
        List<Integer> afterSortLists = sortLists.stream().sorted((In1,In2)->
                In1-In2).collect(Collectors.toList());
        System.out.println(afterSortLists);

        List<String> distinctList = new ArrayList<>();
        distinctList.add("a");
        distinctList.add("a");
        distinctList.add("c");
        distinctList.add("d");
        List<String> afterDistinctList = distinctList.stream().distinct().collect(Collectors.toList());
        System.out.println(afterDistinctList);
    }

    //匹配 -- Match
    @Test
    public void demo6(){
        List<String> matchList = new ArrayList<>();
        matchList.add("a");
        matchList.add("a");
        matchList.add("");
        matchList.add("c");
        matchList.add("d");

        //判断集合中没有有为‘c’的元素
        boolean isExits = matchList.stream().anyMatch(s -> s.equals("c"));

        //判断集合中是否全不为空：
        boolean isNotEmpty = matchList.stream().noneMatch(s -> s.isEmpty());

        System.out.println(isExits);
        System.out.println(isNotEmpty);
    }

}
