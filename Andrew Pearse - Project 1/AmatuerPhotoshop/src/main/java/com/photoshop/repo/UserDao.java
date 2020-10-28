package com.photoshop.repo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

//import org.apache.log4j.Logger;

import com.photoshop.model.User;
import com.photoshop.model.UserRole;
import com.photoshop.util.ConnectionUtil;

public class UserDao implements DaoContract<User, Integer> {

//	private static Logger logger = Logger.getLogger(UserDao.class);
	
	@Override
	public List<User> findAll() {
		List<User> users = new LinkedList<>();
		String sql = "select * from get_all_employees";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection() ){
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				users.add(new User(rs.getInt("id"), rs.getString("username"), null, rs.getString("first_name"), 
										rs.getString("last_name"), rs.getString("email"), new UserRole(rs.getInt("role_id"), rs.getString("role"))));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	@Override
	public User findById(Integer i) {
		User user = null;
		String sql = "select * from get_all_employees where id = ?";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), 
									rs.getString("last_name"), rs.getString("email"), new UserRole(rs.getInt("role_id"), rs.getString("role")));
			}
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public int create(User t) {
		String sql = "insert into ers_users (ers_username, ers_password, user_first_name, "
											+ "user_last_name, user_email, user_role_id) values (?,?,?,?,?,?)"; 
		int updated = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getUserRole().getId());
			
			updated = ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
		return updated;
	}

	@Override
	public int update(User t) {
		String sql = "update ers_users set ers_username = ?,"
										+ "ers_password = ?,"
										+ "user_first_name = ?,"
										+ "user_last_name = ?,"
										+ "user_email = ?,"
										+ "user_role_id = ? "
									 + "where ers_users_id = ?";
		int updated = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getUserRole().getId());
			ps.setInt(7, t.getId());
			
			updated = ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updated;
	}

	public boolean verifyUser(String email, String password) {
		
		String sql = "{ ? = call verifyUser(?,?) }";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection()){
			CallableStatement ps = conn.prepareCall(sql);
			ps.registerOutParameter(1, Types.BOOLEAN);
			ps.setString(2, email);
			ps.setString(3, password);
			
			ps.execute();
			boolean verified = ps.getBoolean(1);
			ps.close();
			
			return verified;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public User findByEmail(String email) {
		User user = null;
		String sql = "select * from get_all_employees where email = ?";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), 
						rs.getString("last_name"), rs.getString("email"), new UserRole(rs.getInt("role_id"), rs.getString("role")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public int delete(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
