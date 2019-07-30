package account.type.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import account.type.dao.TypeDao;
import account.type.pojo.Type;
import account.user.pojo.User;

@Service
public class TypeServiceImpl implements TypeService{

	@Autowired
	HttpSession session;
	@Autowired
	TypeDao dao;
	@Override
	public List<Type> getTypes() {
		User user = (User)session.getAttribute("user");
		return dao.getTypesByUser(user.getId());
	}
	
	@Override
	public void addType(Type type) {
		User user = (User)session.getAttribute("user");
		type.setUserId(user.getId());
		dao.addType(type);
	}
	public void delType(Type type) {
		User user = (User)session.getAttribute("user");
		type.setUserId(user.getId());
		dao.delType(type);
	}
}
