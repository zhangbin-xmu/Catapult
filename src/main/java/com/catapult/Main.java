package com.catapult;

/**
 * <p>
 *     主程序
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
public class Main {

    public static void main(String[] args) {
        // 创建一个弹药箱。
        AmmoBox ammoBox = new AmmoBox();
        // 创建一个搬运工。
        Porter porter = new Porter(ammoBox);
        // 创建一个炮兵。
        Artillery artillery = new Artillery(ammoBox);
        // 搬运工观察弹药箱。
        ammoBox.addObserver(porter);
        // 炮兵观察弹药箱。
        ammoBox.addObserver(artillery);
    }
}
