package account.type.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import account.type.pojo.Type;

@Repository
public class TypeDaoImpl implements TypeDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public List<Type> getTypesByUser(String userId) {
		return jdbcTemplate.query("select * from type where user_id=?", new RowMapper<Type>(){
			@Override
			public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
				Type type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				type.setIncome(rs.getString("income"));
				type.setUserId(rs.getString("user_id"));
				return type;
			}
		},userId);
	}
	
	@Override
	public void addType(Type t) {
		jdbcTemplate.update("insert into type (name,income,user_id) values(?,?,?)",t.getName(),t.getIncome(),t.getUserId());
	}
	
	@Override
	public void delType(Type t) {
		jdbcTemplate.update("delete from type where name = ?",t.getName());
	}
}
