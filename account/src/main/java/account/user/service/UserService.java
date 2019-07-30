package account.user.service;

import java.sql.Date;
import java.util.List;

import account.order.pojo.Account;
import account.user.pojo.User;

public interface UserService {
	public String signup(User user);
	
	public String signin(User user);
	
	void changeName(User newname);
	
	String getNow(String picture);

	void upPic(String userId, String fileName);
	
	List<Account> getByDate(Date date);
}
