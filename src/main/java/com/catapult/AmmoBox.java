package com.catapult;

import lombok.Getter;

import java.util.Observable;
import java.util.Vector;

/**
 * <p>
 *     弹药箱类（被观察者）
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
public class AmmoBox extends Observable {

    /**
     * 弹药箱容量。
     */
    @Getter
    private int capacity = 10;

    private Vector<Ammunition> ammunitionVector = new Vector<Ammunition>();

    public void supplement(Ammunition ammunition) {
        if (ammunitionVector.size() >= capacity) {
            System.out.println(String.format("弹药箱：库存已满，现存弹药【%d】。", ammunitionVector.size()));
        } else {
            ammunitionVector.add(ammunition);
            System.out.println(String.format("弹药箱：弹药补充，现存弹药【%d】。", ammunitionVector.size()));
        }
        super.setChanged();
        super.notifyObservers(ammunitionVector.size());
    }

    public void consume() {
        if (ammunitionVector.isEmpty()) {
            System.out.println(String.format("弹药箱：库存已空，现存弹药【%d】。", ammunitionVector.size()));
        } else {
            ammunitionVector.remove(0);
            System.out.println(String.format("弹药箱：弹药消耗，现存弹药【%d】。", ammunitionVector.size()));
        }
        super.setChanged();
        super.notifyObservers(ammunitionVector.size());
    }
}
