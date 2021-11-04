package com.zukxu.validator.controller;

import com.zukxu.common.result.R;
import com.zukxu.common.result.CommREnum;
import com.zukxu.validator.entity.Demo2;
import com.zukxu.validator.entity.Demo3;
import com.zukxu.validator.entity.Person;
import com.zukxu.validator.entity.User;
import com.zukxu.validator.grop.GroupA;
import com.zukxu.validator.grop.GroupB;
import com.zukxu.validator.grop.GroupOrder;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:02
 */

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private Validator validator;

	//创建用户
	@PostMapping
	public R register(@Valid @RequestBody User user, BindingResult errors) {
		//判断传入用户数据是否合法
		List<String> errs = new ArrayList<>();
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error -> {
				FieldError fieldError = (FieldError) error;
				errs.add(fieldError.getDefaultMessage());
			});
			return R.fail(CommREnum.PARAM_ERROR, null);
		}
		//输入入库
		System.out.println("数据插入成功");
		return R.ok(errs);
	}

	//修改用户
	@PutMapping
	public R update(@Valid @RequestBody User user, BindingResult errors) {
		//判断传入用户数据是否合法
		List<String> errs = new ArrayList<>();
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error -> {
				FieldError fieldError = (FieldError) error;
				errs.add(fieldError.getDefaultMessage());
			});
			return R.fail(CommREnum.PARAM_ERROR,null);
		}
		//输入入库
		System.out.println("数据修改成功");
		return R.ok(errs);
	}

	/*get请求参数校验	 */

	/**
	 * @param id
	 * @param age
	 * @return
	 * @RequestParam参数校验
	 */
	@GetMapping("/getById1")
	public R getById1(@RequestParam(name = "id") String id, @RequestParam(name = "age") Integer age) {
		System.out.println("此时需要使用MethodValidationPostProcessor ");
		System.out.println("数据查询成功：" + id + "年龄：" + age);
		return R.ok();
	}

	/**
	 * 方法所在的Controller上加注解@Validated
	 *
	 * @param id
	 * @param age
	 * @return
	 */
	@GetMapping("/getById2")
	@Validated
	public R getById2(@NotBlank(message = "ID不能为空") @RequestParam(name = "id") String id, @Range(min = 0, max = 120, message = "年龄最小为0，最大为120") @RequestParam(name = "age") Integer age) {
		System.out.println("方法所在的Controller上加注解@Validated");
		System.out.println("数据查询成功：" + id + "年龄：" + age);
		return R.ok();
	}

	/**
	 * 使用统一捕获异常处理：
	 *
	 * @param id
	 * @param age
	 * @return
	 */
	@GetMapping("/getById3")
	public R getById3(@RequestParam(name = "id") String id, @RequestParam(name = "age") Integer age) {
		System.out.println("返回验证信息提示,使用统一捕获异常处理：");
		System.out.println("数据查询成功：" + id + "年龄：" + age);
		return R.ok();
	}


	/**
	 * model校验
	 */
	@GetMapping("/demo2")
	public void demo2() {
		Demo2 demo2 = new Demo2();
		demo2.setAge("111");
		demo2.setHigh(150);
		demo2.setLength("ABCDE");
		demo2.setList(new ArrayList<String>() {{
			add("111");
			add("222");
			add("333");
		}});
		Set<ConstraintViolation<Demo2>> violationSet = validator.validate(demo2);
		for (ConstraintViolation<Demo2> model : violationSet) {
			System.out.println(model.getMessage());
		}
	}

	/**
	 * 对象级联校验
	 */
	@GetMapping("/demo3")
	public void demo3() {
		Demo2 demo2 = new Demo2();
		demo2.setExtField("22");

		Demo3 demo3 = new Demo3();
		demo3.setList(new ArrayList<String>() {{
			add("111");
			add("222");
			add("333");
		}});

		demo3.setDemo2(demo2);
		Set<ConstraintViolation<Demo2>> violationSet = validator.validate(demo2);
		for (ConstraintViolation<Demo2> model : violationSet) {
			System.out.println(model.getMessage());
		}
	}

	/**
	 * 分组验证
	 */
	@GetMapping("/demo5")
	public void demo5() {
		Person p = new Person();
		//GroupA验证不通过
		p.setUserId(-12);
		//GroupA验证通过
		//p.setUserId(12);
		p.setUserName("a");
		p.setAge(110);
		p.setSex(5);
		Set<ConstraintViolation<Person>> validate = validator.validate(p, GroupA.class, GroupB.class);
		for (ConstraintViolation<Person> item : validate) {
			System.out.println(item);
		}
	}

	@GetMapping("/demo6")
	public void demo6(@Validated({GroupA.class, GroupB.class}) Person p, BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError error : allErrors) {
				System.out.println(error);
			}
		}
	}

	/**
	 * 组序列
	 */
	@GetMapping("/demo7")
	public void demo7() {
		Person p = new Person();
		//GroupA验证不通过
		//p.setUserId(-12);
		//GroupA验证通过
		p.setUserId(12);
		p.setUserName("a");
		p.setAge(110);
		p.setSex(5);
		Set<ConstraintViolation<Person>> validate = validator.validate(p, GroupOrder.class);
		for (ConstraintViolation<Person> item : validate) {
			System.out.println(item);
		}
	}

	@GetMapping("/demo8")
	public void demo8(@Validated({GroupOrder.class}) Person p, BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError error : allErrors) {
				System.out.println(error);
			}
		}
	}

}
