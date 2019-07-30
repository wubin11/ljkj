package account.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import account.order.dao.OrderDao;
import account.order.pojo.Account;
import account.user.pojo.User;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao dao;
	@Autowired
	HttpSession session;

	/**
	 * 添加账单
	 */
	@Override
	public String addOrder(Account account) {
		/*
		 * 谁登录，记谁的账
		 */
		User u = (User) session.getAttribute("user");
		account.setUserId(u.getId());
		/*
		 * 记入数据库
		 */
		dao.addOrder(account);
		return null;
	}

	/**
	 * 查询账单
	 */
	@Override
	public List<Account> getOrder() {
		/*
		 * 谁登录，记谁的账
		 */
		User u = (User) session.getAttribute("user");
		return dao.getOrder(u.getId());
	}

	/**
	 * 查询汇总
	 */
	@Override
	public Map<String, BigDecimal> getSum() {
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		User user = (User) session.getAttribute("user");
		result.put("sumIn", dao.getSumIn(user.getId()));
		result.put("sumOut", dao.getSumOut(user.getId()));
		return result;
	}

	@Override
	public List<Account> getByType(Object param) {
		return dao.getByType(param);
	}

	/*
	 * @Override public List<Account> getByDate(Date date) { User u =
	 * (User)session.getAttribute("user"); Account account = new Account();
	 * account.setUserId(u.getId());
	 * 
	 * return dao.getByDate(date); }
	 */
	@Override
	public List<Account> searchOrders(Date startDate, Date endDate) {
		User user = (User)session.getAttribute("user");
		return dao.searchOrders(startDate,endDate,user.getId());
	}
}
