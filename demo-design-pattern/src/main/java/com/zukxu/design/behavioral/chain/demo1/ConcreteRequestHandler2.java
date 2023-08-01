package com.zukxu.design.behavioral.chain.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:54:58
 */
public class ConcreteRequestHandler2 implements RequestHandler {

    private RequestHandler nextHandler;

    public RequestHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(RequestHandler handler) {
        nextHandler = handler;
    }

    public void handleRequest(Request request) {
        if (request.getRequestType() == RequestType.TYPE2) {
            System.out.println("Handling request of type 2 by handler 2.");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("Can't handle request of type " + request.getRequestType());
        }
    }

}






