package com.zukxu.design.behavioral.chain.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:55:21
 */
public class RequestHandlerChain {

    private RequestHandler chain;

    public void addHandler(RequestHandler handler) {
        if (chain == null) {
            chain = handler;
        } else {
            RequestHandler lastHandler = chain;
            while (lastHandler.getNextHandler() != null) {
                lastHandler = lastHandler.getNextHandler();
            }
            lastHandler.setNextHandler(handler);
        }
    }

    public void handleRequest(Request request) {
        if (chain != null) {
            chain.handleRequest(request);
        }
    }

}
