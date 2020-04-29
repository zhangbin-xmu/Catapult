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
        // 搬运工观察弹药箱。
        ammoBox.addObserver(new Porter("搬运工A", ammoBox));
        ammoBox.addObserver(new Porter("搬运工B", ammoBox));
        // 炮兵观察弹药箱。
        ammoBox.addObserver(new Artillery("炮兵甲", ammoBox));
    }
}
