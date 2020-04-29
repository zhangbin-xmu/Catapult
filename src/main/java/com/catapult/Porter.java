package com.catapult;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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
        if (null == scheduledExecutorService) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                    new ThreadFactoryBuilder().setNameFormat("port-schedule-pool-%d").build());
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    delivery();
                }
            }, 0, 5, TimeUnit.SECONDS);
            System.out.println("搬运工：开工。");
        }
    }

    private void rest() {
        if (null != scheduledExecutorService
                && ! scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
            System.out.println("搬运工：休息。");
        }
    }

    public void update(Observable o, Object arg) {
        if ((Integer) arg >= ammoBox.getCapacity()) {
            rest();
            return;
        }

        int halfCapacity = ammoBox.getCapacity() / 2;
        if ((Integer) arg <= halfCapacity) {
            work();
        }
    }
}
