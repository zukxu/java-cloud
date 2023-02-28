package com.zukxu.design.model;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/2/28 15:49:55
 */
public abstract class Travel {

    public void travel() {
        //做攻略
        makePlan();

        //收拾行李
        packUp();

        //去目的地
        toDestination();

        //玩耍、拍照
        play();

        //乘坐交通工具去返回
        backHome();
    }

    protected abstract void makePlan();

    protected abstract void packUp();

    protected abstract void toDestination();

    protected abstract void play();

    protected abstract void backHome();

}