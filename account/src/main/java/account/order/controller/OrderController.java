package account.order.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import account.order.pojo.Account;
import account.order.service.OrderService;
import account.pojo.ResponseInfo;

@Controller
public class OrderController {

	@Autowired
	OrderService service;
	@Autowired
	HttpSession session;

	@PostMapping("/addOrder")
	@ResponseBody
	public ResponseInfo addOrder(Account account) {
		service.addOrder(account);
		return new ResponseInfo();
	}

	@GetMapping("/getOrder")
	@ResponseBody
	public ResponseInfo getOrder() {
		ResponseInfo info = new ResponseInfo();
		info.setMessage(service.getOrder());
		return info;
	}

	@GetMapping("/getSum")
	@ResponseBody
	public ResponseInfo getSum() {
		ResponseInfo info = new ResponseInfo();
		info.setMessage(service.getSum());
		return info;
	}

	@RequestMapping("/getByType")
	@ResponseBody
	public List<Account> getByType(Account param) {

		Integer id = param.getTypeId();
		return service.getByType(id);
	}

	@GetMapping("/searchOrders")
	@ResponseBody
	public ResponseInfo searchOrders(Date startDate, Date endDate) {
		ResponseInfo info = new ResponseInfo();
		List<Account> orders = service.searchOrders(startDate, endDate);
		info.setMessage(orders);
		return info;
	}
}
