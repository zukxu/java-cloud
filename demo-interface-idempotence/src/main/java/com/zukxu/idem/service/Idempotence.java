package com.zukxu.idem.service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/12/14 20:38:43
 */
public interface Idempotence {

    /**
     * 检查是否存在幂等号
     *
     * @param idempotenceId 幂等号
     *
     * @return 是否存在
     */
    boolean check(String idempotenceId);

    /**
     * 记录幂等号
     *
     * @param idempotenceId 幂等号
     */
    void record(String idempotenceId);

    /**
     * 记录幂等号
     *
     * @param idempotenceId 幂等号
     * @param time          过期时间
     */
    void record(String idempotenceId, Integer time);

    /**
     * 删除幂等号
     *
     * @param idempotenceId 幂等号
     */
    void delete(String idempotenceId);

}