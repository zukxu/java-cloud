package com.zukxu.design.structural.flyweight;

/**
 * <p>
 * 享元实现类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:51:48
 */
public class ConcreteFlyweight implements Flyweight {

        private int intrinsicState; // 内部状态

        public ConcreteFlyweight(int intrinsicState) {
                this.intrinsicState = intrinsicState;
        }

        @Override
        public void operation(int extrinsicState) {
                System.out.println("Intrinsic state: " + intrinsicState +
                                   ", Extrinsic state: " + extrinsicState);
        }

}
