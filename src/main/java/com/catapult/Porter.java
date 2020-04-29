package com.catapult;

/**
 * <p>
 *     搬运工
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
public class Porter {

    /**
     * 搬运工负责的弹药箱。
     */
    private AmmoBox ammoBox;

    public Porter(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
    }

    public void delivery() {
        System.out.println("搬运工：运输弹药。");
        ammoBox.supplement(new Ammunition());
    }
}
