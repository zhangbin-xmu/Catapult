package com.catapult;

import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 *     炮手类（观察者）
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
public class Artillery implements Observer {

    private AmmoBox ammoBox;

    private boolean canFire;

    public Artillery(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
    }

    public void update(Observable o, Object arg) {
        if ((Integer) arg > 0) {
            System.out.println("炮兵：弹药充足。");
            canFire = true;
        } else {
            System.out.println("炮兵：弹药告罄。");
            canFire = false;
        }
    }

    public void fire() {
        if (! canFire) {
            System.out.println("炮兵：上刺刀。");
            return;
        }
        System.out.println("炮兵：意大利炮。");
        ammoBox.consume();
    }
}
