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
	 * 娉ㄥ唽锛�
	 */
	@PostMapping("/signup")
	@ResponseBody
	public ResponseInfo signup(User user){
		ResponseInfo info = new ResponseInfo();
		/*
		 * 楠岃瘉鐢ㄦ埛鍚嶃�佸瘑鐮侊細6~12浣嶄箣闂�
		 */
		if(user.getUsername().length() < 6 || user.getUsername().length() > 12) {
			info.setCode(1);
			info.setMessage("鐢ㄦ埛鍚嶉暱搴﹀簲鍦�6~12浣嶄箣闂�");
			return info;
		}
		if(user.getPassword().length() < 6 || user.getPassword().length() > 12) {
			info.setCode(1);
			info.setMessage("瀵嗙爜闀垮害搴斿湪6~12浣嶄箣闂�");
			return info;
		}
		/*
		 * 娉ㄥ唽
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
	 * 鐧诲綍锛�
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
	 * 閫�鍑�
	 */
	@PostMapping("/signout")
	@ResponseBody
	public ResponseInfo signout(HttpSession session) {
		session.removeAttribute("user");
		System.out.println("第二次");
		return new ResponseInfo()
	}
	
	/**
	 * 鑾峰彇褰撳墠鐧诲綍浜轰俊鎭�
	 */
	@GetMapping("/getSigninUser")
	@ResponseBody
	public ResponseInfo getSigninUser(HttpSession session){
		ResponseInfo info = new ResponseInfo();
		Object user = session.getAttribute("user");
		if(user == null) {
			info.setCode(1);
			info.setMessage("褰撳墠鏈櫥褰�");
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
