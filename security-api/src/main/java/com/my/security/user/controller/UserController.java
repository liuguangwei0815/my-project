package com.my.security.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.suport.SimpleResponse;
import com.my.security.user.dto.UserInfo;
import com.my.security.user.entity.User;
import com.my.security.user.service.UserService;

@RestController
@RequestMapping("/user")
//@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @RequestBody 接受json参数
	 * @param user
	 *  @Validated 才能启动model 里的 校验标签
	 * @return
	 */
	@PostMapping
	public SimpleResponse create(@RequestBody @Valid UserInfo user,BindingResult result) {
		if(result.hasErrors())
			return  result.getAllErrors().stream().map(e->BuildErroMsg(e)).findFirst().get();
		try {
			return userService.create(user);
		}catch(ConstraintViolationException e1){
			e1.printStackTrace();
			return SimpleResponse.fail(e1.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return SimpleResponse.fail("创建失败");
		}
	}

	private SimpleResponse BuildErroMsg(ObjectError e) {
		return SimpleResponse.fail(e.getDefaultMessage());
	}

	@PutMapping("/{id}")
	public SimpleResponse update(@RequestBody UserInfo user) {
		return userService.update(user);
	}

	@DeleteMapping("/{id}")
	public SimpleResponse delete(@PathVariable Long id) {
		return userService.delete(id);
	}

	@GetMapping("/{id}")
	public SimpleResponse get(@PathVariable Long id, HttpServletRequest request) {
		SimpleResponse simpleResponse = userService.get(id);

		if (simpleResponse.getData()==null) {
			//return SimpleResponse.fail("查询用户不存在");
			throw new RuntimeException("查询用户不存在");
		}

		User dbUser = (User) simpleResponse.getData();

		User user = (User) request.getAttribute("user");

		if (user == null || (user.getId().intValue() != dbUser.getId().intValue())) {
			//rreturn SimpleResponse.fail("查询用户失败，你暂无权限查询");
			throw new RuntimeException("查询用户失败，你暂无权限查询");
		}
		return userService.get(id);
	}

	@GetMapping
	public SimpleResponse query(String userName) {
		return userService.query(userName);
	}

//	public static void main(String[] args) {
//		User user1 = new User(1l, "a", "b", "c");
//		
//		UserInfo user2  = new UserInfo(1l, "a", "b", "c");
//		
//		System.out.println(user1.equals(user2));
//		
//		System.out.println(user1==user2);
//	}

}
