package account.order.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import account.order.pojo.Account;

public interface OrderDao {
	int addOrder(Account account);
	
	List<Account> getOrder(String userId);
	
	BigDecimal getSumIn(String userId);
	
	BigDecimal getSumOut(String userId);
   
	List<Account> getByType(Object param);
	
	/* List<Account> getByDate(Date date); */
	List<Account> searchOrders(Date startDate, Date endDate, String userId);
}
