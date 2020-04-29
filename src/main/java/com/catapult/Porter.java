package com.catapult;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class Porter implements Observer {

    /**
     * 搬运工的名字。
     */
    @Getter
    @Setter
    private String name;

    /**
     * 搬运工负责的弹药箱。
     */
    private AmmoBox ammoBox;

    private ScheduledExecutorService scheduledExecutorService = null;

    public Porter(String name, AmmoBox ammoBox) {
        this.name = name;
        this.ammoBox = ammoBox;
        work();
    }

    private void delivery() {
        log.info("{}：运输弹药。", name);
        ammoBox.supplement(new Ammunition());
    }

    private void work() {
        if (null == scheduledExecutorService) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                    new ThreadFactoryBuilder().setNameFormat("porter-schedule-pool-%d").build());
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    delivery();
                }
            }, 0, 5, TimeUnit.SECONDS);
            log.info("{}：开工。", name);
        }
    }

    private void rest() {
        if (null != scheduledExecutorService
                && ! scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
            log.info("{}：休息。", name);
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
