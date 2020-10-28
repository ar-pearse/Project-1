package com.photoshop.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.photoshop.model.Reimbursement;
import com.photoshop.model.ReimbursementStatus;
import com.photoshop.model.ReimbursementType;
import com.photoshop.util.ConnectionUtil;

public class ReimbursementDao implements DaoContract<Reimbursement, Integer> {

	private UserDao ud;
	
	public ReimbursementDao() {
		this(new UserDao());
	}
	
	public ReimbursementDao(UserDao ud) {
		this.ud = ud;
	}
	
	@Override
	public List<Reimbursement> findAll() {
		List<Reimbursement> reimbursements = new LinkedList<>();
		String sql = "select * from get_all_reimbursements";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection() ){
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				reimbursements.add(new Reimbursement(rs.getInt("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), 
										ud.findById(rs.getInt("author")), ud.findById(rs.getInt("resolver")), 
										new ReimbursementStatus(rs.getInt("status_id"), rs.getString("status")), 
										new ReimbursementType(rs.getInt("type_id"), rs.getString("type"))));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursements;
	}

	@Override
	public Reimbursement findById(Integer i) {
		Reimbursement reimbursement = null;
		String sql = "select * from get_all_reimbursements where id = ?";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				reimbursement = new Reimbursement(rs.getInt("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), 
									ud.findById(rs.getInt("author")), ud.findById(rs.getInt("resolver")), 
									new ReimbursementStatus(rs.getInt("status_id"), rs.getString("status")), 
									new ReimbursementType(rs.getInt("type_id"), rs.getString("type")));
			}
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursement;
	}

	@Override
	public int create(Reimbursement t) {
		String sql = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, "
				+ "reimb_description, reimb_author, reimb_status_id, reimb_type_id) values (?,?,?,?,?,?,?)"; 
		int updated = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, t.getAmount());
			ps.setTimestamp(2, Timestamp.valueOf(t.getDateSubmitted()));
			
			if (t.getDateResolved() != null)
				ps.setTimestamp(3, Timestamp.valueOf(t.getDateResolved()));
			else
				ps.setTimestamp(3, null);
			
			ps.setString(4, t.getDescription());
			ps.setInt(5, t.getAuthor().getId());
			ps.setInt(6, t.getStatus().getId());
			ps.setInt(7, t.getType().getId());
			
			updated = ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return updated;
	}

	@Override
	public int update(Reimbursement t) {
		String sql = "update ers_reimbursement set reimb_amount = ?, "
												+ "reimb_submitted = ?, "
												+ "reimb_resolved = ?, "
												+ "reimb_description = ?,"
												+ "reimb_author = ?, "
												+ "reimb_resolver = ?, "
												+ "reimb_status_id = ?, "
												+ "reimb_type_id = ? "
											 + "where reimb_id = ?";
		int updated = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, t.getAmount());
			ps.setTimestamp(2, Timestamp.valueOf(t.getDateSubmitted()));
			
			if (t.getDateResolved() != null)
				ps.setTimestamp(3, Timestamp.valueOf(t.getDateResolved()));
			else
				ps.setTimestamp(3, null);
			
			ps.setString(4, t.getDescription());
			ps.setInt(5, t.getAuthor().getId());
			ps.setInt(6, t.getResolver().getId());
			ps.setInt(7, t.getStatus().getId());
			ps.setInt(8, t.getType().getId());
			ps.setInt(9, t.getId());
			
			updated = ps.executeUpdate();
		
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return updated;
	}

	public List<Reimbursement> findAllByUser(int i) {
		List<Reimbursement> reimbursements = new LinkedList<>();
		String sql = "select * from get_all_reimbursements where author = ?";
		
		try (Connection conn = ConnectionUtil.getInstance().getConnection() ){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				reimbursements.add(new Reimbursement(rs.getInt("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), 
										ud.findById(rs.getInt("author")), ud.findById(rs.getInt("resolver")), 
										new ReimbursementStatus(rs.getInt("status_id"), rs.getString("status")), 
										new ReimbursementType(rs.getInt("type_id"), rs.getString("type"))));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		reimbursements.sort( (r1, r2) -> r1.getStatus().getStatus().compareTo(r2.getStatus().getStatus()) );
		
		return reimbursements;
	}

	@Override
	public int delete(Integer i) {
		int updated = 0;
		
		try(Connection conn = ConnectionUtil.getInstance().getConnection()){
			String sql = "delete from ers_reimbursement where reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			
			updated = ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
}
