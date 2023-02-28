package com.zukxu.design.model;

/**
 * <p>
 * 西湖
 * </p>
 *
 * @author xupu
 * @since 2023/2/28 15:50:39
 */
public class XiHuTravel extends Travel {

    @Override
    protected void makePlan() {
        System.out.println("制作去西湖的攻略");
    }

    @Override
    protected void packUp() {
        System.out.println("收拾去西湖的行李");
    }

    @Override
    protected void toDestination() {
        System.out.println("去西湖");
    }

    @Override
    protected void play() {
        System.out.println("在西湖玩耍");
    }

    @Override
    protected void backHome() {
        System.out.println("从西湖回家");
    }

}
