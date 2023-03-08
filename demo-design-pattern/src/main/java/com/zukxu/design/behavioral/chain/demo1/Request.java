package com.zukxu.design.behavioral.chain.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:56:26
 */
public class Request {

    private RequestType requestType;

    public Request(RequestType type) {
        requestType = type;
    }

    public RequestType getRequestType() {
        return requestType;
    }

}
