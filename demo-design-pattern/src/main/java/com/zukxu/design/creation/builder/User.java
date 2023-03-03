package com.zukxu.design.creation.builder;

/**
 * <p>
 * 用户类 建造者模式
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:00:48
 */
public class User {

    private String firstName;

    private String lastName;

    private int age;

    private String address;

    private String email;

    // User 类的构造方法
    private User(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        age = builder.age;
        address = builder.address;
        email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    // getters and setters

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 定义 User 类的 Builder 静态内部类
    public static class Builder {

        private String firstName;

        private String lastName;

        private int age;

        private String address;

        private String email;

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        // 用于构建 User 对象
        public User build() {
            return new User(this);
        }

    }

}
