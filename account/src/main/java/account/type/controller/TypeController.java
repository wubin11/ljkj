package account.type.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import account.pojo.ResponseInfo;
import account.type.pojo.Type;
import account.type.service.TypeService;

@Controller
public class TypeController {
	
	@Autowired
	TypeService service;
	
	@GetMapping("/getTypes")
	@ResponseBody
	public ResponseInfo getTypes(){
		ResponseInfo result = new ResponseInfo();
		List<Type> types = service.getTypes();
		result.setMessage(types);
		return result;
	}
	
	@PostMapping("/addType")
	@ResponseBody
	public ResponseInfo addType(Type type) {
		if(type.getName()==null) {
			ResponseInfo info = new ResponseInfo();
			info.setCode(1);
			return info;
		}
		service.addType(type);
		return new ResponseInfo();
	}
	@PostMapping("/delType")
	@ResponseBody
	public ResponseInfo delType(Type type) {
		if(type.getName()==null) {
			ResponseInfo info = new ResponseInfo();
			info.setCode(1);
			return info;
		}
		service.delType(type);
		return new ResponseInfo();
	}
}
