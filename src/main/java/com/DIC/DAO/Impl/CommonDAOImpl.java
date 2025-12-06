package com.DIC.DAO.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.DIC.model.SMSModel;
//import com.DIC.model.SMSModel;


@ManagedBean(name="commonDAOImpl")
@ApplicationScoped
public class CommonDAOImpl {
	
	private static final Logger log = LogManager.getLogger(CommonDAOImpl.class);
	
	interface Constants {
		// SQL
			interface SQL {
				
				String SQL_SMS_LOG="select * from sms_log order by create_date desc";
				String SQL_USER_RANK="select * from user_map_rank where user_id = ? and is_active ='1'";
			}
		}
	
	
	public List<SMSModel> getSMSLog()
	{
		
		Connection con = null;
		PreparedStatement ps = null;
		
		List<SMSModel> sMSModelList=new ArrayList<>();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_sms_log = new StringBuilder(Constants.SQL.SQL_SMS_LOG);
			log.info("###: Qury sms log : "+sql_sms_log.toString());
			
			ps = con.prepareStatement(sql_sms_log.toString());
			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				
				SMSModel sMSModel=new SMSModel();
				
				sMSModel.setSmsId(rs.getInt("sms_id"));
				sMSModel.setStatusCode(rs.getInt("status_code"));
				sMSModel.setReturnStatus(rs.getString("return_status"));
				sMSModel.setRequestId(rs.getString("request_id"));
				sMSModel.setStatus(rs.getString("status"));
				sMSModel.setToNumber(rs.getString("to_number"));
				sMSModel.setAgentName(rs.getString("agent_name"));
				sMSModel.setCustName(rs.getString("cust_name"));
				sMSModel.setCustNumber(rs.getString("cust_number"));
				sMSModel.setCreateDate(rs.getString("create_date"));
				System.out.println("sms log :"+rs.getInt("sms_id"));
					
				sMSModelList.add(sMSModel);					
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
		}
		
		return sMSModelList;
	}
	
	//************************* get User rank **********************
	public int getUserRank(int userId)
	{
		
		Connection con = null;
		PreparedStatement ps = null;
		int rank=0;
			

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_user_rank = new StringBuilder(Constants.SQL.SQL_USER_RANK);
			log.info("###: Qury user rank  : "+sql_user_rank.toString());
			
			ps = con.prepareStatement(sql_user_rank.toString());
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
		

			while ( rs.next() ) {
				
				rank=rs.getInt("rank");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
		}
		
		return rank;
	}
		
		


}
