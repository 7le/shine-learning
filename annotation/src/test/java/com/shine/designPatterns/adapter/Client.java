package com.shine.designPatterns.adapter;

/**
 * 入口
 * Created by 7le on 017/6/14
 */
public class Client {

    public static void main(String[] args) {
        ConcreteTarget concreteTarget=new ConcreteTarget();
        concreteTarget.method();

        //将不符合标准接口的类，进行适配 --类适配器
        ClassAdapter classAdapter=new ClassAdapter();
        classAdapter.method();

        // 对象适配器
        Target adapter = new ObjectAdapter(new Adaptee());
        adapter.method();
    }

    /**
     * 贴一个大话设计模式的小故事，很有意思。
     *
     * 根据典记，魏文王曾求教于名医扁鹊：“你们家兄弟三人，都精于医术，谁是医术最好的呢？”
     * 扁鹊：“大哥最好，二哥差些，我是三人中最差的一个。” 魏王不解地说：“请你介绍的详细些。” 　
     * 扁鹊解释说：“大哥治病，是在病情发作之前，那时候病人自己还不觉得有病，但大哥就下药铲除了病根，
     * 使他的医术难以被人认可，所以没有名气，只是在我们家中被推崇备至。
     * 我的二哥治病，是在病初起之时，症状尚不十分明显，病人也没有觉得痛苦，
     * 二哥就能药到病除，使乡里人都认为二哥只是治小病很灵。
     * 我治病，都是在病情十分严重之时，病人痛苦万分，病人家属心急如焚。
     * 此时，他们看到我在经脉上穿刺，用针放血，或在患处敷以毒药以毒攻毒，或动大手术直指病灶，
     * 使重病人病情得到缓解或很快治愈，所以我名闻天下。”魏王大悟。
     */
}
