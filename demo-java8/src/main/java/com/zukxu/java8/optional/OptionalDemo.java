package com.zukxu.java8.optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * <p>
 * Optional案例
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 09:16
 */
public class OptionalDemo {
	public static void testOf() {
		// 传入正常值，正常返回一个 Optional 对象
		Optional<String> optional1 = Optional.of("mydlq");
		System.out.println(optional1);

		// 传入参数为 null，抛出 NullPointerException.
		Optional optional2 = Optional.of(null);
		System.out.println(optional2);
	}

	public static void testOfNullable() {
		// 传入正常值，正常返回一个 Optional 对象
		Optional<String> optional1 = Optional.ofNullable("mydlq");
		System.out.println(optional1);
		// 传入 null 参数，正常返回 Optional 对象
		Optional optional2 = Optional.ofNullable(null);
		System.out.println(optional2);
	}

	public static void testIsPresent() {
		// 传入正常值，正常返回一个 Optional 对象，并使用 isPresent 方法
		Optional optional1 = Optional.ofNullable("mydlq");
		System.out.println("传入正常值返回：" + optional1.isPresent());

		// 传入参数为 null 生成一个 Optional 对象，并使用 isPresent 方法
		Optional optional2 = Optional.ofNullable(null);
		System.out.println("传入 null 值返回：" + optional2.isPresent());
	}

	public static void testGet() {
		// 传入正常值，正常返回一个 Optional 对象，并使用 get 方法获取值
		Optional optional1 = Optional.ofNullable("mydlq");
		System.out.println(optional1.get());

		// 传入参数为 null 生成一个 Optional 对象，并使用 get 方法获取值
		Optional optional2 = Optional.ofNullable(null);
		System.out.println(optional2.get());
	}

	public static void testIfPresent() {
		// 创建 Optional 对象，然后调用 Optional 对象的 ifPresent 方法，传入 Lambda 表达式
		Optional optional1 = Optional.ofNullable("mydlq1");
		optional1.ifPresent((value) -> System.out.println("Optional 的值为：" + value));

		// 创建 Optional 对象，调用 Optional 对象的 ifPresent 方法，传入实现 Consumer 匿名内部类
		Optional optional2 = Optional.ofNullable("mydlq2");
		Consumer<String> consumer = new Consumer() {
			@Override
			public void accept(Object value) {
				System.out.println("Optional 的值为：" + value);
			}
		};
		optional2.ifPresent(consumer);
	}

	public static void testOrElse() {
		// 传入正常参数，获取一个 Optional 对象，并使用 orElse 方法设置默认值
		Optional optional1 = Optional.ofNullable("mydlq");
		Object object1 = optional1.orElse("默认值");
		System.out.println("如果值不为空：" + object1);

		// 传入 null 参数，获取一个 Optional 对象，并使用 orElse 方法设置默认值
		Optional optional2 = Optional.ofNullable(null);
		Object object2 = optional2.orElse("默认值");
		System.out.println("如果值为空：" + object2);
	}

	public static void testOrElseGet() {
		// 传入正常参数，获取一个 Optional 对象，并使用 orElse 方法设置默认值
		Optional optional1 = Optional.ofNullable("mydlq");
		Object object1 = optional1.orElseGet(() -> {
			String defaultVal = "执行逻辑和生成的默认值";
			return defaultVal;
		});
		System.out.println("输出的值为：" + object1);

		// 传入 null 参数，获取一个 Optional 对象，并使用 orElse 方法设置默认值
		Optional optional2 = Optional.ofNullable(null);
		Object object2 = optional2.orElseGet(() -> {
			String defaultVal = "执行逻辑和生成的默认值";
			return defaultVal;
		});
		System.out.println("输出的值为：" + object2);
	}

	public static void testOrElseThrow() {
		// 传入正常参数，获取一个 Optional 对象，并使用 orElseThrow 方法
		try {
			Optional optional1 = Optional.ofNullable("mydlq");
			Object object1 = optional1.orElseThrow(() -> {
				System.out.println("执行逻辑，然后抛出异常");
				return new RuntimeException("抛出异常");
			});
			System.out.println("输出的值为：" + object1);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		// 传入 null 参数，获取一个 Optional 对象，并使用 orElseThrow 方法
		try {
			Optional optional2 = Optional.ofNullable(null);
			Object object2 = optional2.orElseThrow(() -> {
				System.out.println("执行逻辑，然后抛出异常");
				return new RuntimeException("抛出异常");
			});
			System.out.println("输出的值为：" + object2);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public static void testMap1() {
		// 创建 map 对象
		Map<String, String> userMap = new HashMap<>();
		userMap.put("name1", "mydlq");
		userMap.put("name2", null);

		// 传入 Map 对象参数，获取一个 Optional 对象，获取 name1 属性
		Optional<String> optional1 = Optional.of(userMap).map(value -> value.get("name1"));

		// 传入 Map 对象参数，获取一个 Optional 对象，获取 name2 属性
		Optional<String> optional2 = Optional.of(userMap).map(value -> value.get("name2"));

		// 获取 Optional 的值
		System.out.println("获取的 name1 的值：" + optional1.orElse("默认值"));
		System.out.println("获取的 name2 的值：" + optional2.orElse("默认值"));
	}

	public static void testMap2() {
		// 创建一个对象，设置姓名属性而不设置性别，这时候性别为 null
		User user1 = new User("测试名称");
		User user2 = new User();

		// 使用 Optional 存储 User 对象
		Optional<User> optional1 = Optional.ofNullable(user1);
		Optional<User> optional2 = Optional.ofNullable(user2);

		// 获取对象的 name 属性值
		String name1 = optional1.map(User::getName).orElse("未填写");
		String name2 = optional2.map(User::getName).orElse("未填写");

		// 输出结果
		System.out.println("获取的名称：" + name1);
		System.out.println("获取的名称：" + name2);
	}

	public static void testFlatMap() {
		// 创建 map 对象
		Map<String, String> userMap = new HashMap<>();
		userMap.put("name", "mydlq");
		userMap.put("sex", "男");

		// 传入 Map 对象参数，获取一个 Optional 对象
		Optional<Map<String, String>> optional1 = Optional.of(userMap);

		// 使用 Optional 的 flatMap 方法，获取 Map 中的 name 属性
		// 然后通过获取的值手动创建一个新的 Optional 对象
		Optional optional2 = optional1.flatMap(value -> Optional.ofNullable(value.get("name")));

		// 获取 Optional 的 value
		System.out.println("获取的 Optional 的值：" + optional2.get());
	}

	public static void testFilter() {
		// 创建一个测试的 Optional 对象
		Optional<String> optional = Optional.ofNullable("mydlq");
		// 调用 Optional 的 filter 方法，设置一个满足的条件，然后观察获取的 Optional 对象值是否为空
		Optional optional1 =optional.filter((value) -> value.length() > 2);
		System.out.println("Optional 的值不为空：：" + optional.isPresent());

		// 调用 Optional 的 filter 方法，设置一个不满足的条件，然后观察获取的 Optional 对象值是否为空
		Optional optional2 =optional.filter((value) -> value.length() <2);
		System.out.println("Optional 的值不为空：：" + optional2.isPresent());
	}

	public static void main(String[] args) {
		// testOf();
		// testOfNullable();
		// testIsPresent();
		// testGet();
		// testIfPresent();
		// testOrElse();
		// testOrElseGet();
		// testOrElseThrow();
		// testMap1();
		// testMap2();
		// testFlatMap();
		testFilter();
	}

}
