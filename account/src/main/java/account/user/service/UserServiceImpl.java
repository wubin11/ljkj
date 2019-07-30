package account.user.service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import account.order.pojo.Account;
import account.type.dao.TypeDao;
import account.type.pojo.Type;
import account.user.dao.UserDao;
import account.user.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;
	@Autowired
	TypeDao typeDao;
	@Autowired
	HttpSession session;
	
	/**
	 * 注册功能
	 */
	@Override
	public String signup(User user) {
		synchronized (UserServiceImpl.class) {
			/*
			 * 验证用户名重复
			 */
			User u = dao.getByUsername(user.getUsername());
			if(u != null) {
				return "用户名已存在";
			}
			/*
			 * 生成ID 、 加密并插入数据库
			 */
			user.setId(UUID.randomUUID().toString());
			user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
			dao.add(user);
		}
		/*
		 * 添加默认的记账类型
		 */
		Type t = new Type();
		t.setIncome("0");
		t.setName("吃饭");
		t.setUserId(user.getId());
		typeDao.addType(t);
		t.setIncome("0");
		t.setName("喝酒");
		t.setUserId(user.getId());
		typeDao.addType(t);
		t.setIncome("1");
		t.setName("工资");
		t.setUserId(user.getId());
		typeDao.addType(t);
		t.setIncome("1");
		t.setName("捡钱");
		t.setUserId(user.getId());
		typeDao.addType(t);
		
		return null;
	}
	
	/**
	 * 登录功能：
	 * 1.验证身份
	 * 2.记录登录状态：session
	 */
	@Override
	public String signin(User user) {
		User u = dao.getByUsername(user.getUsername());// 查出的用户
		if(u == null) {
			return "用户名不存在";
		}
		if(u.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))) {
			u.setPassword(null);
			session.setAttribute("user", u);
			return null;
		}else {
			return "密码错误";
		}
	}

	@Override
	public void changeName(User newname) {
		User user = (User)session.getAttribute("user");
		user.setName(newname.getName());
		dao.changeName(user);
	}
	@Override
	public String getNow(String picture) {
		return dao.getNow(picture);
	}

	@Override
	public void upPic(String userId, String fileName) {
		dao.upPic(userId, fileName);
	}

	@Override
	public List<Account> getByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
