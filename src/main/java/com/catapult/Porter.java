package com.catapult;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     搬运工（观察者）
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
public class Porter implements Observer {

    /**
     * 搬运工负责的弹药箱。
     */
    private AmmoBox ammoBox;

    private ScheduledExecutorService scheduledExecutorService = null;

    public Porter(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
        work();
    }

    private void delivery() {
        System.out.println("搬运工：运输弹药。");
        ammoBox.supplement(new Ammunition());
    }

    private void work() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        synchronized (scheduledExecutorService) {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    delivery();
                }
            }, 0, 3, TimeUnit.SECONDS);
        }
    }

    private void rest() {
        synchronized (scheduledExecutorService) {
            if (null != scheduledExecutorService
                    && ! scheduledExecutorService.isShutdown()) {
                scheduledExecutorService.shutdown();
                scheduledExecutorService = null;
            }
        }
    }

    public void update(Observable o, Object arg) {
        if ((Integer) arg >= ammoBox.getCapacity()) {
            System.out.println("搬运工：弹药库存已满，暂停搬运。");
            rest();
            return;
        }

        int halfCapacity = ammoBox.getCapacity() / 2;
        if ((Integer) arg <= halfCapacity
                && null == scheduledExecutorService) {
            System.out.println("搬运工：弹药库存紧张，加紧搬运。");
            work();
        }
    }
}
