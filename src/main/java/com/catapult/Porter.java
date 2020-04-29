package com.catapult;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
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

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public Porter(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                delivery();
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

    public void delivery() {
        System.out.println("搬运工：运输弹药。");
        ammoBox.supplement(new Ammunition());
    }

    public void update(Observable o, Object arg) {
        if ((Integer) arg > 0) {
            System.out.println("搬运工：弹药充足。");
        } else {
            System.out.println("搬运工：弹药告罄。");
        }
    }
}
