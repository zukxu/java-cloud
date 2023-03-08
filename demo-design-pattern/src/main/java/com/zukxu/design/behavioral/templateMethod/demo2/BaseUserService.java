package com.zukxu.design.behavioral.templateMethod.demo2;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:39:12
 */
@Service
public abstract class BaseUserService<T> {

    @Resource
    private UserRepository<T> userRepository;

    public List<T> findAll() {
        return userRepository.findAll();
    }

    public T findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(T entity) {
        beforeSave(entity);
        userRepository.save(entity);
        afterSave(entity);
    }

    public void update(T entity) {
        beforeUpdate(entity);
        userRepository.update(entity);
        afterUpdate(entity);
    }

    public void delete(Long id) {
        T entity = findById(id);
        beforeDelete(entity);
        userRepository.delete(id);
        afterDelete(entity);
    }

    protected void beforeSave(T entity) {}

    protected void afterSave(T entity) {}

    protected void beforeUpdate(T entity) {}

    protected void afterUpdate(T entity) {}

    protected void beforeDelete(T entity) {}

    protected void afterDelete(T entity) {}

}
