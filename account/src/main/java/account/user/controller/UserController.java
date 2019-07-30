package account.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import account.pojo.ResponseInfo;
import account.user.pojo.User;
import account.user.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService service;
	
	/**
	 * 注册：
	 */
	@PostMapping("/signup")
	@ResponseBody
	public ResponseInfo signup(User user){
		ResponseInfo info = new ResponseInfo();
		/*
		 * 验证用户名、密码：6~12位之间
		 */
		if(user.getUsername().length() < 6 || user.getUsername().length() > 12) {
			info.setCode(1);
			info.setMessage("用户名长度应在6~12位之间");
			return info;
		}
		if(user.getPassword().length() < 6 || user.getPassword().length() > 12) {
			info.setCode(1);
			info.setMessage("密码长度应在6~12位之间");
			return info;
		}
		/*
		 * 注册
		 */
		String result = service.signup(user);
		if(result == null) {
			return new ResponseInfo();
		}
		info.setCode(1);
		info.setMessage(result);
		return info;
		
	}

	/**
	 * 登录：
	 */
	@PostMapping("/signin")
	@ResponseBody
	public ResponseInfo signin(User user) {
		ResponseInfo info = new ResponseInfo();
		String message = service.signin(user);
		if(message != null) {
			info.setCode(1);
			info.setMessage(message);
		}
		return info;
	}
	
	/**
	 * 退出
	 */
	@PostMapping("/signout")
	@ResponseBody
	public ResponseInfo signout(HttpSession session) {
		session.removeAttribute("user");
		return new ResponseInfo();
	}
	
	/**
	 * 获取当前登录人信息
	 */
	@GetMapping("/getSigninUser")
	@ResponseBody
	public ResponseInfo getSigninUser(HttpSession session){
		ResponseInfo info = new ResponseInfo();
		Object user = session.getAttribute("user");
		if(user == null) {
			info.setCode(1);
			info.setMessage("当前未登录");
		}else {
			info.setMessage(user);
		}
		return info;
	}
	@PostMapping("/changeName")
	@ResponseBody
	public ResponseInfo changeName(User newname){
		service.changeName(newname);
		
		return new ResponseInfo();
	}
}
