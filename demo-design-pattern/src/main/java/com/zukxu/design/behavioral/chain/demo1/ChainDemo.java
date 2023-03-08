package com.zukxu.design.behavioral.chain.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:55:55
 */
public class ChainDemo {

    public static void main(String[] args) {
        RequestHandlerChain chain = new RequestHandlerChain();
        chain.addHandler(new ConcreteRequestHandler1());
        chain.addHandler(new ConcreteRequestHandler2());

        Request request1 = new Request(RequestType.TYPE1);
        chain.handleRequest(request1);

        Request request2 = new Request(RequestType.TYPE2);
        chain.handleRequest(request2);

        Request request3 = new Request(RequestType.TYPE3);
        chain.handleRequest(request3);
    }

}
