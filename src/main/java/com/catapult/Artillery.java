package com.catapult;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public Artillery(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                fire();
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

    public void fire() {
        if (! canFire) {
            System.out.println("炮兵：上刺刀。");
            //executorService.shutdownNow();
            return;
        }
        System.out.println("炮兵：上意大利炮。");
        ammoBox.consume();
    }

    public void update(Observable o, Object arg) {
        if ((Integer) arg > 0) {
            System.out.println("炮兵：弹药充足。");
            canFire = true;
            if (executorService.isShutdown()) {
                executorService.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        fire();
                    }
                }, 0, 3000, TimeUnit.MILLISECONDS);
            }
        } else {
            System.out.println("炮兵：弹药告罄。");
            canFire = false;
        }
    }
}
