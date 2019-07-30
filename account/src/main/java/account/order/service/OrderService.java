package account.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import account.order.pojo.Account;

public interface OrderService {
	String addOrder(Account account);
	
	List<Account> getOrder();
	
	Map<String,BigDecimal> getSum();
	
	List<Account> getByType(Object param);
	
	/* List<Account> getByDate(Date date); */
	List<Account> searchOrders(Date startDate, Date endDate);
	
}
