package com.zukxu.java8.optional;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

/**
 * <p>
 * Optional案例
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 09:16
 */
public class OptionalSimpleDemo {
	private static final Logger log = LoggerFactory.getLogger(OptionalSimpleDemo.class);

	public static void testOf() {
		// 传入正常值，正常返回一个 Optional 对象
		Optional<User> optional1 = Optional.of(createUser("1"));
		log.info("{}", optional1);

		// 传入参数为 null，抛出 NullPointerException.
		Optional<User> optional2 = Optional.of(getNull());
		log.info("{}", optional2);
	}

	public static void testOfNullable() {
		// 传入正常值，正常返回一个 Optional 对象
		Optional<User> optional1 = Optional.ofNullable(createUser("1"));
		log.info("{}", optional1);
		// 传入 null 参数，正常返回 Optional 对象
		Optional<User> optional2 = Optional.ofNullable(getNull());
		log.info("{}", optional2);
	}

	public static void testOrElseAndOrElseGet() {
		//传入一个空值的情况
		User user1 = getNull();
		User user2 = getNull();
		user1 = Optional.ofNullable(user1).orElse(createUser("1"));
		user2 = Optional.ofNullable(user2).orElseGet(() -> createUser("2"));
		log.info("当值为null时，orElse" + JSON.toJSONString(user1));
		log.info("当值为null时，orElseGet" + JSON.toJSONString(user2));
		//传入不为空值的情况
		User user3 = createUser("10086");
		User user4 = createUser("10000");
		user3 = Optional.ofNullable(user3).orElse(createUser("3"));
		user4 = Optional.ofNullable(user4).orElseGet(() -> createUser("4"));

		log.info("当值不为null时，orElse" + JSON.toJSONString(user3));
		log.info("当值不为null时，orElseGet" + JSON.toJSONString(user4));
	}


	public static void testOrElseThrow() {
		// 传入 null 参数，获取一个 Optional 对象，并使用 orElseThrow 方法
		User user2 = getNull();
		User object2 = Optional.ofNullable(user2).orElseThrow(() -> new RuntimeException("参数为空===>抛出异常"));
		log.info("输出的值为：" + object2);
	}

	public static void testMap() {
		// 创建 map 对象
		Map<String, User> userMap = new HashMap<>();
		userMap.put("user1", createUser("1"));
		userMap.put("user2", getNull());

		//String name1 = Optional.ofNullable(userMap.get("user1")).map(value -> value.getName()).get();
		String name1 = Optional.ofNullable(userMap.get("user1")).map(User::getName).get();
		log.info("获取值===>{}", name1);
		// 传入 Map 对象参数，获取一个 Optional 对象，获取 user2 属性
		//String name2 = Optional.ofNullable(userMap.get("user2")).map(value -> value.getName()).get();
		String name2 = Optional.of(Optional.ofNullable(userMap.get("user2")).orElse(createUser("0000"))).map(User::getName).get();
		log.info("获取值===>{}", name2);
	}

	public static void testFlatMap() {
		// 创建 map 对象
		Map<String, String> userMap = new HashMap<>();
		userMap.put("name", "javaCoder");
		userMap.put("sex", "男");

		// 传入 Map 对象参数，获取一个 Optional 对象
		Optional<Map<String, String>> optional1 = Optional.of(userMap);

		// 使用 Optional 的 flatMap 方法，获取 Map 中的 name 属性
		// 然后通过获取的值手动创建一个新的 Optional 对象
		Optional<String> optional2 = optional1.flatMap(value -> Optional.ofNullable(value.get("name")));

		// 获取 Optional 的 value
		log.info("获取的 Optional 的值：" + optional2.get());
	}

	public static void testIsPresent() {
		// 传入正常值，正常返回一个 Optional 对象，并使用 isPresent 方法
		Optional<User> optional1 = Optional.of(createUser("1"));
		log.info("传入正常值返回：" + optional1.isPresent());

		// 传入参数为 null 生成一个 Optional 对象，并使用 isPresent 方法
		Optional<User> optional2 = Optional.ofNullable(getNull());
		log.info("传入 null 值返回：" + optional2.isPresent());
	}

	public static void testGet() {
		// 传入正常值，正常返回一个 Optional 对象，并使用 get 方法获取值
		Optional<User> optional1 = Optional.of(createUser("1"));
		log.info("{}", optional1.get());

		// 传入参数为 null 生成一个 Optional 对象，并使用 get 方法获取值
		Optional<User> optional2 = Optional.ofNullable(getNull());
		log.info("{}", optional2.get());
	}

	public static void testIfPresent() {
		// 创建 Optional 对象，然后调用 Optional 对象的 ifPresent 方法，传入 Lambda 表达式
		Optional<User> user1 = Optional.of(createUser("1"));
		Consumer<User> consumer = (v) -> {
			log.info("{}的年龄是{},生日是：{}", v.getName(), v.getAge(), v.getBirthday());
		};
		user1.ifPresent((value) -> log.info("直接实现：{}", value));
		//传入实现 Consumer 匿名内部类
		user1.ifPresent(consumer);
	}

	public static void testFilter() {
		// 创建一个测试的 Optional 对象
		Optional<User> optional = Optional.of(createUser("100"));

		// 调用 Optional 的 filter 方法，设置一个满足的条件，然后观察获取的 Optional 对象值是否为空
		optional = optional.filter((value) -> value.getAge() > 2);
		log.info("Optional 的值不为空：：" + optional.isPresent());

		// 调用 Optional 的 filter 方法，设置一个不满足的条件，然后观察获取的 Optional 对象值是否为空
		optional = optional.filter((value) -> value.getAge() < 2);
		log.info("Optional 的值不为空：：" + optional.isPresent());
	}
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
		log.info("通过 Optional 过滤的集合输出：");
		nameList.forEach(log::info);
	}
	private static User createUser(String id) {
		log.info("执行了生成方法:::id===>{}", id);
		return new User(id, "小米" + id, 18, LocalDate.now());
	}

	private static User getNull() {
		return null;
	}

}
