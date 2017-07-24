package string;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * guava 的String操作
 */
public class demo {

    /**
     * 判断字符串是否为空
     */
    @Test
    public void isNullOrEmpty(){
        String input = "";
        boolean isNullOrEmpty = Strings.isNullOrEmpty(input);
        System.out.println("input " + (isNullOrEmpty?"is":"is not") + " null or empty.");
    }

    /**
     * 获得两个字符串相同的前缀或者后缀
     */
    @Test
    public void commonPrefix(){
        String a = "com.shine.Hello";
        String b = "com.shine.Hi";
        String ourCommonPrefix = Strings.commonPrefix(a,b);
        System.out.println("a,b common prefix is " + ourCommonPrefix);

        String c = "com.google.Hello";
        String d = "com.shine.Hello";
        String ourSuffix = Strings.commonSuffix(c,d);
        System.out.println("c,d common suffix is " + ourSuffix);
    }

    /**
     * Strings的padStart和padEnd方法来补全字符串
     */
    @Test
    public void pad(){
        String padEndResult = Strings.padEnd("123", 5, '0');
        System.out.println("padEndResult is " + padEndResult);

        String padStartResult = Strings.padStart("1", 2, '0');
        System.out.println("padStartResult is " + padStartResult);
    }

    /**
     * Splitter 来拆分字符串
     */
    @Test
    public void Splitter(){
        Iterable<String> splitResults = Splitter.onPattern("[,，]{1,}")
                .trimResults() //对结果做trim
                .omitEmptyStrings() //表示忽略空字符串
                .split("hello,word,,shine，guava");

        for (String item : splitResults) {
            System.out.println(item);
        }
    }

    /**
     * Splitter做二次拆分
     */
    @Test
    public void Splitter2(){
        String toSplitString = "a=b;c=d,e=f";
        Map<String,String> kvs = Splitter.onPattern("[,;]{1,}")  //首先是使用onPattern做第一次的拆分
                .withKeyValueSeparator('=')  //然后再通过withKeyValueSeperator('')方法做第二次的拆分
                .split(toSplitString);
        for (Map.Entry<String,String> entry : kvs.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(),entry.getValue()));
        }
    }

    /**
     * 合并字符串
     */
    @Test
    public void Joiner(){
        String joinResult = Joiner.on(" ").join(new String[]{"hello","world"});
        System.out.println(joinResult);

        Map<String,String> map = new HashMap<String,String>();
        map.put("a", "b");
        map.put("c", "d");
        String mapJoinResult = Joiner.on(",").withKeyValueSeparator("=").join(map);
        System.out.println(mapJoinResult);
    }
}
