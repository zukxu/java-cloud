package com.zukxu.gencode.common.response;

import java.util.HashMap;

/**
 * @author xupu
 * @date 2021/11/27 15:29:40
 */
public class RMap extends HashMap<String, Object> {

    public RMap() {
    }

    public RMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
