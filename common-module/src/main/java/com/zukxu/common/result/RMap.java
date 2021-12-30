package com.zukxu.common.result;

import java.util.HashMap;

/**
 * @author xupu
 * @since 2021/11/27 15:29:40
 */
public class RMap extends HashMap<String, Object> {

    public RMap() {
    }

    public RMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
