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

    private ScheduledExecutorService scheduledExecutorService = null;

    public Artillery(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
    }

    private void fire() {
        System.out.println("炮兵：意大利炮。");
        ammoBox.consume();
    }

    private void work() {
        if (null == scheduledExecutorService) {
            scheduledExecutorService = Executors.newScheduledThreadPool(1);/* new ScheduledThreadPoolExecutor(10,
                    new BasicThreadFactory.Builder().namingPattern("artillery-schedule-pool-%d").daemon(true).build());*/
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    fire();
                }
            }, 0, 3, TimeUnit.SECONDS);
            System.out.println("炮兵，就位。");
        }
    }

    private void rest() {
        if (null != scheduledExecutorService
                && ! scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
            System.out.println("炮兵：隐蔽。");
        }
    }

    public void update(Observable o, Object arg) {
        if ((Integer) arg <= 0) {
            rest();
        }

        int oneThirdCapacity = ammoBox.getCapacity() / 3;
        if ((Integer) arg >= oneThirdCapacity) {
            work();
        }
    }
}
