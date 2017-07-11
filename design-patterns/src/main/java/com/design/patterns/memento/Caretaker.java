package com.design.patterns.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责人
 * Created by 7le on 2017/6/15 .
 */
public class Caretaker {

    //简单的单次保存
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    private List<Memento> mementoList = new ArrayList<>();

    public Memento getMementoList(int step) {
        return mementoList.get(step);
    }

    public void setMementoList(Memento memento) {
        this.mementoList.add(memento);
    }

}
