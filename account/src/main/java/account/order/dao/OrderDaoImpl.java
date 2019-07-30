package account.order.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import account.order.pojo.Account;
import account.user.pojo.User;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	HttpSession session;
	@Override
	public int addOrder(Account account) {
		return jdbcTemplate.update("insert into account (money,type_id,date,is_del,user_id)values(?,?,?,?,?)"
				,account.getMoney(),account.getTypeId(),account.getDate(),0,account.getUserId());
	}
	
	@Override
	public List<Account> getOrder(String userId) {
		return jdbcTemplate.query(
				"select * from account where is_del=0 and user_id=? ", 
				new RowMapper<Account>() {
			@Override
			public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
				Account result = new Account();
				result.setId(rs.getInt("id"));
				result.setMoney(rs.getBigDecimal("money"));
				result.setTypeId(rs.getInt("type_id"));
				result.setDate(rs.getDate("date"));
				result.setUserId(rs.getString("user_id"));
				return result;
			}
		},userId);
	}

	@Override
	public BigDecimal getSumIn(String userId) {
		List<BigDecimal> result = jdbcTemplate.query("select sum(a.money) as money " + 
				"from account as a " + 
				"left join type as t on a.type_id = t.id " + 
				"where a.is_del=0 and a.user_id=? and t.income=1 ", new RowMapper<BigDecimal>() {
					@Override
					public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getBigDecimal("money");
					}
		},userId);
		if(result == null || result.isEmpty() || result.get(0) == null) {
			return BigDecimal.ZERO;
		}
		return result.get(0);
	}
	
	@Override
	public BigDecimal getSumOut(String userId) {
		List<BigDecimal> result = jdbcTemplate.query("select sum(a.money) as money " + 
				"from account as a " + 
				"left join type as t on a.type_id = t.id " + 
				"where a.is_del=0 and a.user_id=? and t.income=0 ", new RowMapper<BigDecimal>() {
					@Override
					public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getBigDecimal("money");
					}
		},userId);
		if(result == null || result.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return result.get(0);
	}

	@Override
	public List<Account> getByType(Object param) {
		return jdbcTemplate.query("SELECT * FROM account where type_id=?", new RowMapper<Account>(){

			@Override
			public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
				Account account = new Account();
				account.setDate(rs.getDate("date"));
				account.setMoney(rs.getBigDecimal("money"));
				account.setTypeId(rs.getInt("type_id"));
				account.setId(rs.getInt("id"));
				account.setUserId(rs.getString("user_id"));
				return account;
			}},param);
		
	}

	/*
	 * @Override public List<Account> getByDate(Date date) { SimpleDateFormat
	 * format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String time =
	 * format0.format(date.getTime());
	 * 
	 * User u = (User)session.getAttribute("user"); Account account = new Account();
	 * account.setUserId(u.getId());
	 * 
	 * return jdbcTemplate.query("SELECT * FROM account where user_id=? ", new
	 * RowMapper<Account>(){
	 * 
	 * @Override public Account mapRow(ResultSet rs, int rowNum) throws SQLException
	 * { Account account = new Account(); account.setDate(rs.getDate("date"));
	 * account.setMoney(rs.getBigDecimal("money"));
	 * account.setTypeId(rs.getInt("type_id")); account.setId(rs.getInt("id"));
	 * account.setUserId(rs.getString("user_id")); return account;
	 * }},account.getUserId());
	 * 
	 * }
	 */
	public List<Account> searchOrders(Date startDate, Date endDate, String userId) {
		List<Account> orderList = jdbcTemplate.query(
				"select * from `account`" + "WHERE  date>= ?  AND  date<= ?" + "AND is_del = '0' AND user_id = ?" + "ORDER BY date desc",
				new RowMapper<Account>() {

					@Override
					public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
						Account account = new Account();
						account.setId(rs.getInt("id"));
						account.setDate(rs.getDate("date"));
						account.setDelete(rs.getString("is_del"));
						account.setMoney(rs.getBigDecimal("money"));
						account.setTypeId(rs.getInt("type_id"));
						account.setUserId(rs.getString("user_id"));
						return account;
					}
				}, startDate, endDate, userId);

		return orderList;
	}
}
