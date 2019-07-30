package account.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import account.user.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int add(User user) {
		return jdbcTemplate.update("insert into user(id,name,username,password) values(?,?,?,?)",
				user.getId(),user.getName(),user.getUsername(),user.getPassword());
	}

	/**
	 * 根据username查询user
	 * 查询出id、username属性
	 * 查不到则为null
	 */
	@Override
	public User getByUsername(String username) {
		List<User> userList =  jdbcTemplate.query("select * from user where username=?", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User result = new User();
				result.setId(rs.getString("id"));
				result.setName(rs.getString("name"));
				result.setUsername(rs.getString("username"));
				result.setPassword(rs.getString("password"));
				result.setPic(rs.getString("pic"));
				return result;
			}
		},username);
		if(userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	@Override
	public void changeName(User newname) {
		jdbcTemplate.update("update user set name =? where username =?",newname.getName(),newname.getUsername());
	}
	@Override
	public String getNow(String picture) {
		List<User> userList =  jdbcTemplate.query("SELECT pic FROM `user` WHERE id=?", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User result = new User();
				result.setPic(rs.getString("pic"));
				return result;
			}
		},picture);
		if(userList.size() == 0) {
			return null;
		}
		return userList.get(0).getPic();
	}

	@Override
	public void upPic(String userId, String fileName) {
		jdbcTemplate.update("UPDATE `user` SET pic=? WHERE id = ?",
				fileName,userId);
	}

}
