package com.catapult;

import lombok.Data;

/**
 * <p>
 *     弹药类
 * </p>
 * @author zhangbin
 * @date 2020-04-29
 */
@Data
public class Ammunition {

    /**
     * 弹药类型。
     */
    private String type;

    private String[] types = { "意大利炮", "迫击炮", "山炮" };

    public Ammunition() {
        type = types[(int) (Math.random() * types.length)];
    }
}
