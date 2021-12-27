package com.zukxu.java8.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * optional 组合案例
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 09:50
 */
public class OptionalComDemo {
	/**
	 * 对集合中的对象属性进行过滤
	 */
	public static void testFilterCollections() {
		// 创建一个测试的用户集合
		List<User> userList = new ArrayList<>();

		// 创建几个测试用户
		User user1 = new User("abc");
		User user2 = new User("efg");
		User user3 = null;

		// 将用户加入集合
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);

		// 创建用于存储姓名的集合
		List<String> nameList = new ArrayList();
		// 循环用户列表获取用户信息，值获取不为空且用户以 a 开头的姓名，
		// 如果不符合条件就设置默认值，最后将符合条件的用户姓名加入姓名集合
		for (User user : userList) {
			nameList.add(Optional.ofNullable(user).map(User::getName).filter(value -> value.startsWith("a")).orElse("未填写"));
		}

		// 输出名字集合中的值
		System.out.println("通过 Optional 过滤的集合输出：");
		nameList.stream().forEach(System.out::println);
	}

	public static void main(String[] args) {
		testFilterCollections();
	}
}
