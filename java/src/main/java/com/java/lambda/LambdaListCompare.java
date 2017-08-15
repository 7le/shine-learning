package com.java.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lambda - 比较器
 * Created by 7le on 2017/8/15 .
 */
public class LambdaListCompare {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(1);
        list.add(3);
        list.add(6);

/*        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });*/


/*        list.sort(((o1, o2) -> {
            return Integer.compare(o1, o2);
        }));*/

        // 简写Lambda表达式
        list.sort((o1, o2) -> Integer.compare(o1, o2));

        System.out.println(list.toString());
    }
}
