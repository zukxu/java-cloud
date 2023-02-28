package com.zukxu.design.model;

/**
 * <p>
 * 长城
 * </p>
 *
 * @author xupu
 * @since 2023/2/28 15:50:39
 */
public class CCTravel extends Travel {

    @Override
    protected void makePlan() {
        System.out.println("制作去长城的攻略");
    }

    @Override
    protected void packUp() {
        System.out.println("收拾去长城的行李");
    }

    @Override
    protected void toDestination() {
        System.out.println("去长城");
    }

    @Override
    protected void play() {
        System.out.println("在长城玩耍");
    }

    @Override
    protected void backHome() {
        System.out.println("从长城回家");
    }

}
