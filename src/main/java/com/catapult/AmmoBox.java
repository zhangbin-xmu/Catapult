package com.catapult;

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

    private Vector<Ammunition> ammunitionVector = new Vector<Ammunition>();

    public void supplement(Ammunition ammunition) {
        ammunitionVector.add(ammunition);
        System.out.println(String.format("弹药箱：弹药补充，现存弹药%d。", ammunitionVector.size()));
        super.setChanged();
        super.notifyObservers(ammunitionVector.size());
    }

    public void consume() {
        if (ammunitionVector.isEmpty()) {
            System.out.println(String.format("弹药箱：弹药不足，现存弹药%d。", ammunitionVector.size()));
            return;
        }
        ammunitionVector.remove(0);
        System.out.println(String.format("弹药箱：弹药消耗，现存弹药%d。", ammunitionVector.size()));
        super.setChanged();
        super.notifyObservers(ammunitionVector.size());
    }
}
