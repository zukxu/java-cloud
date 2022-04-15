package com.zukxu.java8.stream;

import com.zukxu.java8.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>
 * Stream 案例
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/2 0002 10:11
 */
public class StreamDemo {
	private static final Logger log = LoggerFactory.getLogger(StreamDemo.class);

	/**
	 * 创建stream的方法
	 */
	public static void createStream() {
		//	1、通过 java.util.Collection.stream() 方法用集合创建流
		List<String> list = Arrays.asList("a", "b", "c", "d");
		//	创建一个顺序流
		Stream<String> stream = list.stream();
		//	创建一个并行流
		Stream<String> parallelStream = list.parallelStream();

		//	2、使用 Arrays.stream(T[] array),用数组创建流
		int[] array = {1, 3, 5, 7, 9};
		IntStream intStream = Arrays.stream(array);

		//	3、使用stream的静态方法 of(),iterate(),generate()创建
		Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

		//从0开始，逐次加3，到4为止
		Stream<Integer> limit = Stream.iterate(0, (x) -> x + 3).limit(4);
		limit.forEach(System.out::println);

		Stream<Double> doubleStream = Stream.generate(Math::random).limit(3);
		doubleStream.forEach(System.out::println);
	}

	/**
	 * 遍历
	 */
	public static void forEachStream() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);

		//	遍历输出符合要求的元素
		list.stream().filter((x) -> x % 2 == 0).forEach(System.out::println);

		//	匹配符合要求的第一个元素
		Optional<Integer> first = list.stream().filter((x) -> x % 2 == 0).findFirst();
		log.info("符合要求的第一个元素:{}", first.get());

		//	匹配任意一个（适用于并行流）
		Optional<Integer> any = list.parallelStream().filter(x -> x % 2 == 0).findAny();
		log.info("符合要求的任意一个元素:{}",any.get());

		//	判断是否包含符合条件的元素
		boolean isMatch = list.stream().anyMatch(x -> x % 2 == 0);
		log.info("是否存在符合条件的元素:{}",isMatch);
	}

	/**
	 * 筛选
	 */
	public static void filterStream() {

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
		Stream<Integer> stream = list.stream();
		//	遍历输出符合要求的元素
		stream.filter((x) -> x % 2 == 0).forEach(System.out::println);

		List<Person> personList = new ArrayList<>();
		personList.add(new Person("Tom", 8900, 21, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 41, "female", "Washington"));
		personList.add(new Person("Anni", 8200, 11, "female", "New York"));
		personList.add(new Person("Owen", 9500, 26, "male", "New York"));
		personList.add(new Person("Alisa", 7900, 19, "female", "New York"));


		List<String> filterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName).collect(Collectors.toList());
		log.info("高于8000的员工姓名：{}", filterList);
	}

	/**
	 * 聚合 max/min/count
	 */
	public static void juHe() {
		List<String> list1 = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
		Optional<String> max = list1.stream().max(Comparator.comparing(String::length));
		Optional<String> min = list1.stream().min(Comparator.comparing(String::length));
		log.info("最长的字符串：{}", max.get());
		log.info("最短的字符串：{}", min.get());


		List<Integer> list2 = Arrays.asList(7, 6, 9, 4, 11, 6);
		Optional<Integer> max1 = list2.stream().max(Integer::compareTo);
		// 自定义排序
		Optional<Integer> max2 = list2.stream().max(Integer::compareTo);
		log.info("自然排序的最大值：{}", max1.get());
		log.info("自定义排序的最大值：{}", max1.get());
		log.info("自定义排序的最大值：{}", max2.get());


		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
		personList.add(new Person("Anni", 8200, 24, "female", "New York"));
		personList.add(new Person("Owen", 9500, 25, "male", "New York"));
		personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

		Optional<Person> max3 = personList.stream().max(Comparator.comparingInt(Person::getSalary));
		log.info("员工工资最大值：{}",max3.get().getSalary());


		List<Integer> list = Arrays.asList(7, 6, 4, 8, 2, 11, 9);
		long count = list.stream().filter(x -> x > 6).count();
		log.info("list中大于6的元素个数：{}", count);
	}

	/**
	 * 映射
	 */
	public static void mapStream() {
		String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
		List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

		List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
		List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

		log.info("每个元素大写：{}", strList);
		log.info("每个元素+3：{}", intListNew);


		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
		personList.add(new Person("Anni", 8200, 24, "female", "New York"));
		personList.add(new Person("Owen", 9500, 25, "male", "New York"));
		personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

		// 不改变原来员工集合的方式
		List<Person> personListNew = personList.stream().map(person -> {
			Person personNew = new Person(person.getName(), 0, 0, null, null);
			personNew.setSalary(person.getSalary() + 10000);
			return personNew;
		}).collect(Collectors.toList());
		log.info("一次改动前：{}", personList.get(0).getName() + "-->{}", personList.get(0).getSalary());
		log.info("一次改动后：{}", personListNew.get(0).getName() + "-->{}", personListNew.get(0).getSalary());

		// 改变原来员工集合的方式
		List<Person> personListNew2 = personList.stream().map(person -> {
			person.setSalary(person.getSalary() + 10000);
			return person;
		}).collect(Collectors.toList());
		log.info("二次改动前：{}:{}", personList.get(0).getName() + "-->{}", personListNew.get(0).getSalary());
		log.info("二次改动后：{}:{}", personListNew2.get(0).getName() + "-->{}", personListNew.get(0).getSalary());


		List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
		List<String> listNew = list.stream().flatMap(s -> {
			// 将每个元素转换成一个stream
			String[] split = s.split(",");
			return Arrays.stream(split);
		}).collect(Collectors.toList());

		log.info("处理前的集合：{}", list);
		log.info("处理后的集合：{}", listNew);
	}

	/**
	 * 归约
	 */
	public static void reduceStream() {
		List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
		// 求和方式1
		Optional<Integer> all1 = list.stream().reduce((x, y) -> x + y);
		// 求和方式2
		Optional<Integer> all2 = list.stream().reduce(Integer::sum);
		// 求和方式3
		Integer sum3 = list.stream().reduce(0, Integer::sum);

		// 求乘积
		Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

		// 求最大值方式1
		Optional<Integer> reduce1 = list.stream().reduce((x, y) -> x > y ? x : y);
		// 求最大值写法2
		Integer reduce2 = list.stream().reduce(1, Integer::max);

		log.info("list求和：{}+{}={}", all1.get(), all2.get() , sum3);
		log.info("list求积：{}", product.get());
		log.info("list求和：{}+{}", reduce1.get(), reduce2);


		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
		personList.add(new Person("Anni", 8200, 24, "female", "New York"));
		personList.add(new Person("Owen", 9500, 25, "male", "New York"));
		personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

		// 求工资之和方式1：
		Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);
		// 求工资之和方式2：
		Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), (sum1, sum2) -> sum1 + sum2);
		// 求工资之和方式3：
		Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

		// 求最高工资方式1：
		Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), Integer::max);
		// 求最高工资方式2：
		Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), (max1, max2) -> max1 > max2 ? max1 : max2);

		log.info("工资之和：{}+{}+{}", sumSalary.get(), sumSalary2, sumSalary3);
		log.info("最高工资：{}+{}", maxSalary, maxSalary2);
	}

	/**
	 * 归集
	 */
	public static void toCollect() {
		List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
		List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
		Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
		personList.add(new Person("Anni", 8200, 24, "female", "New York"));

		Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000).collect(Collectors.toMap(Person::getName, p -> p));
		log.info("toList:{}", listNew);
		log.info("toSet:{}", set);
		log.info("toMap:{}", map);
	}

	/**
	 * 统计
	 */
	public static void countCollect() {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

		// 求总数
		Long count = personList.stream().collect(Collectors.counting());
		// 求平均工资
		Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
		// 求最高工资
		Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
		// 求工资之和
		Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
		// 一次性统计所有信息
		DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

		log.info("员工总数：{}", count);
		log.info("员工平均工资：{}", average);
		log.info("员工工资总和：{}", sum);
		log.info("员工工资所有统计：{}", collect);
	}

	/**
	 * 分组
	 */
	public static void groupCollect() {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
		personList.add(new Person("Lisa", 9800, 28, "female", "Washington"));

		// 将员工按薪资是否高于8000分组
		Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
		// 将员工按性别分组
		Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));
		// 将员工先按性别分组，再按地区分组
		Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
		log.info("员工按薪资是否大于8000分组情况：{}", part);
		log.info("员工按性别分组情况：{}", group);
		log.info("员工按性别、地区：{}", group2);
	}

	/**
	 * 接合
	 */
	public static void joinCollect() {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

		String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
		log.info("所有员工的姓名：{}", names);
		List<String> list = Arrays.asList("A", "B", "C");
		String string = list.stream().collect(Collectors.joining("-"));
		log.info("拼接后的字符串：{}", string);
	}

	/**
	 * 归约
	 */
	public static void reducingCollect() {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("Tom", 8900, 23, "male", "New York"));
		personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

		// 每个员工减去起征点后的薪资之和（这个例子并不严谨，但一时没想到好的例子）
		Integer sum = personList.stream().collect(Collectors.reducing(0, Person::getSalary, (i, j) -> (i + j - 5000)));
		log.info("员工扣税薪资总和：{}",sum);

		// stream的reduce
		Optional<Integer> sum2 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
		log.info("员工薪资总和：{}", sum2.get());
	}

	/**
	 * 排序
	 */
	public static void sortStream() {
		List<Person> personList = new ArrayList<Person>();

		personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
		personList.add(new Person("Tom", 8900, 22, "male", "Washington"));
		personList.add(new Person("Jack", 9000, 25, "male", "Washington"));
		personList.add(new Person("Lily", 8800, 26, "male", "New York"));
		personList.add(new Person("Alisa", 9000, 26, "female", "New York"));

		// 按工资升序排序（自然排序）
		List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName).collect(Collectors.toList());
		// 按工资倒序排序
		List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).map(Person::getName).collect(Collectors.toList());
		// 先按工资再按年龄升序排序
		List<String> newList3 = personList.stream().sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName).collect(Collectors.toList());
		// 先按工资再按年龄自定义排序（降序）
		List<String> newList4 = personList.stream().sorted((p1, p2) -> {
			if (p1.getSalary() == p2.getSalary()) {
				return p2.getAge() - p1.getAge();
			} else {
				return p2.getSalary() - p1.getSalary();
			}
		}).map(Person::getName).collect(Collectors.toList());

		log.info("按工资升序排序：{}", newList);
		log.info("按工资降序排序：{}", newList2);
		log.info("先按工资再按年龄升序排序：{}", newList3);
		log.info("先按工资再按年龄自定义降序排序：{}", newList4);
	}

	/**
	 * 组合提取
	 */
	public static void comStream() {
		String[] arr1 = {"a", "b", "c", "d"};
		String[] arr2 = {"d", "e", "f", "g"};

		Stream<String> stream1 = Stream.of(arr1);
		Stream<String> stream2 = Stream.of(arr2);
		// concat:合并两个流 distinct：去重
		List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
		// limit：限制从流中获得前n个数据
		List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
		// skip：跳过前n个数据
		List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

		log.info("流合并：{}", newList);
		log.info("limit：{}",collect);
		log.info("skip：{}",collect2);
	}
}
