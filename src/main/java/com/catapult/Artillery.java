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
 *     炮手类（观察者）
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
@Slf4j
public class Artillery implements Observer {

    /**
     * 炮兵的名字。
     */
    @Getter
    @Setter
    private String name;

    /**
     * 炮兵可用的弹药箱。
     */
    private AmmoBox ammoBox;

    private ScheduledExecutorService scheduledExecutorService = null;

    public Artillery(String name, AmmoBox ammoBox) {
        this.name = name;
        this.ammoBox = ammoBox;
    }

    private void fire() {
        log.info("{}：意大利炮！", name);
        ammoBox.consume();
    }

    private void work() {
        if (null == scheduledExecutorService) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                    new ThreadFactoryBuilder().setNameFormat(name + "-schedule-pool-%d").build());
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    fire();
                }
            }, 0, 5, TimeUnit.SECONDS);
            log.info("{}：就位。", name);
        }
    }

    private void rest() {
        if (null != scheduledExecutorService
                && ! scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
            log.info("{}：隐蔽。", name);
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
