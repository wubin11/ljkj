package account.user.dao;

import account.user.pojo.User;

public interface UserDao {
	int add(User user);
	User getByUsername(String username);
	void changeName(User newname);
	String getNow(String picture);
	void upPic(String userId, String fileName);
}
