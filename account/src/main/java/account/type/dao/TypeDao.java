package account.type.dao;

import java.util.List;

import account.type.pojo.Type;

public interface TypeDao {
	List<Type> getTypesByUser(String userId);
	
	void addType(Type t);
	
	void delType(Type t);
	
	
}
