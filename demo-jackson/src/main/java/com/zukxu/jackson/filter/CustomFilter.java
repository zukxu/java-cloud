package com.zukxu.jackson.filter;

/**
 * <p>
 * 自定义序列化规则
 * </p>
 *
 * @author xupu
 * @since 2022/5/26 17:19:11
 */
public class CustomFilter {

    @Override
    public boolean equals(Object obj) {
        // null，或者不是字符串就返回true，意味着不被序列化
        if (null == obj || !(obj instanceof String)) {
            return true;
        }

        // 长度大于2就返回true，意味着不被序列化
        return ((String) obj).length() > 2;
    }

}
