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

    /**
     * 炮兵可用的弹药箱。
     */
    private AmmoBox ammoBox;

    /**
     * 是否具备开炮条件。
     */
    private boolean canFire;

    public Artillery(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
    }

    public void fire() {
        if (! canFire) {
            System.out.println("炮兵：上刺刀。");
            return;
        }
        System.out.println("炮兵：上意大利炮。");
        ammoBox.consume();
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
}
