package com.zukxu.mybatis.dynamic;

/**
 * <p>
 * 动态数据源枚举类
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 9:39:44
 */
public class DataSourceType {

    //内部枚举类，用于选择特定的数据类型
    public enum DataBaseType {
        Primary, Secondary
    }

    // 使用ThreadLocal保证线程安全
    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<>();

    // 往当前线程里设置数据源类型
    public static void setDataBaseType(DataBaseType dataBaseType) {
        if(dataBaseType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataBaseType);
    }

    // 获取数据源类型
    public static DataBaseType getDataBaseType() {
        return TYPE.get() == null ? DataBaseType.Primary : TYPE.get();
    }

    // 清空数据类型
    public static void clearDataBaseType() {
        TYPE.remove();
    }

}
