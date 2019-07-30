package account.type.service;

import java.util.List;

import account.type.pojo.Type;

public interface TypeService {
	public List<Type> getTypes();
	
	public void addType(Type type);
	
	public void delType(Type type);
}
