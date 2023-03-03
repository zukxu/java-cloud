package com.zukxu.design.creational.prototype;

/**
 * <p>
 * 原型模式-抽象原型类
 * <p>
 * 通过实现 Cloneable 接口来实现原型模式
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:46:09
 */
public abstract class Shape implements Cloneable {

    protected String type;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void draw();

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}