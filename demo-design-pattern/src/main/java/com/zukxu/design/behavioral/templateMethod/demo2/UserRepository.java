package com.zukxu.design.behavioral.templateMethod.demo2;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:42:42
 */
public interface UserRepository<T> {

    List<T> findAll();

    T findById(Long id);

    void save(T entity);

    void update(T entity);

    void delete(Long id);

}
