package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.model.Mail;
import com.model.MailContent;
import com.model.User;


public class DBLayer {

	public static User findUser(String uname, String pwd, Connection con) throws SQLException{
		
		String sql = "SELECT * FROM users where username = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		//System.out.println(sql);
		ps.setString(1, uname);
		ps.setString(2, pwd);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			User user = new User();
			user.setId(rs.getInt(1));
			user.setFirstname(rs.getString(2));
			user.setLastname(rs.getString(3));
			user.setUsername(rs.getString(4));
			user.setEmail(rs.getString(5));
			user.setPhonenumber(rs.getString(6));
			user.setAge(rs.getInt(7));
			user.setGender(rs.getString(8));
			
			return user;
		}
		else
			return null;		
	}
	
	public static boolean createUser(User user,Connection con) throws SQLException{
		
		String sql = "INSERT INTO users ( firstname, lastname, username, email, phonenumber, age, gender, password) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		
		//System.out.println(sql);
		ps.setString(1, user.getFirstname());
		ps.setString(2, user.getLastname());
		ps.setString(3, user.getUsername());
		ps.setString(4, user.getEmail());
		ps.setString(5, user.getPhonenumber());
		ps.setInt(6, user.getAge());
		ps.setString(7, user.getGender());
		ps.setString(8, user.getPassword());
		int result = ps.executeUpdate();
		
		if(result==1)
			return true;
		else
			return false;
	}
	public static boolean checkUsername(String uname, Connection con) throws SQLException{
		
		String sql = "SELECT * FROM users where username = '"+uname+"'";
		Statement st = con.createStatement();
		//PreparedStatement ps = con.prepareStatement(sql);
		//ps.setString(1, uname);
		//System.out.println(uname);
		//System.out.println(sql);
		//ResultSet rs = ps.executeQuery(sql);
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
			return true;
		else
			return false;
	}
	
	public static boolean insertMail(int to, int from, String message,String subject, int status, Connection con) throws SQLException{
		boolean flag = false;
		String sql = "INSERT INTO mail ( from_userid, to_userid,send_date,status) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, from);
		ps.setInt(2, to);
		ps.setDate(3, getCurrentDate());	
		ps.setInt(4, status);

		int result = ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		int mailid =0;
			while(rs.next()){
				mailid = rs.getInt(1);
			}
		if(mailid != 0){
			insertMessage(mailid, message,subject, con);
			flag = true;
		}
		return flag;
	}
	public static void insertMessage(int mailid, String messages,String subject, Connection con) throws SQLException{
		
		String insert_message = "INSERT INTO mail_content (message_content, mail_id,subject) VALUES (?,?,?)";
		PreparedStatement ps = con.prepareStatement(insert_message);
		
		ps.setString(1, messages);
		ps.setInt(2, mailid);
		ps.setString(3, subject);
		ps.executeUpdate();
	}

	public static HashMap<Mail, MailContent> getSentMail(int sessionId, Connection con,int status) throws SQLException{
		HashMap<Mail, MailContent> mailHashMap = new HashMap<Mail, MailContent>();
		
		String sql = "SELECT * FROM mail where from_userid = ? and status =?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, sessionId);
		ps.setInt(2, status);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			int mailId = rs.getInt(1);
			Mail mail = new Mail();
			mail.setId(rs.getInt(1));
			mail.setFrom_userid(rs.getInt(2));
			mail.setTo_userid(rs.getInt(3));
			mail.setSend_date(rs.getDate(4));
			mail.setStatus(rs.getInt(5));
			
			ResultSet result = getMailContent(mailId,con);
			while(result.next()){

				MailContent mailContent = new MailContent();
				mailContent.setId(result.getInt(1));
				mailContent.setMessage_content(result.getString(2));
				mailContent.setMail_id(result.getInt(3));
				mailContent.setSubject(result.getString(4));
				mailHashMap.put(mail, mailContent);
			}			
		}
		
		return mailHashMap;
	}
		
	public static HashMap<Mail, MailContent> getInboxMail(int sessionId, Connection con) throws SQLException{
		HashMap<Mail, MailContent> mailHashMap = new HashMap<Mail, MailContent>();
		
		String sql = "SELECT * FROM mail where to_userid = ? and status =?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, sessionId);
		ps.setInt(2, 1);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			int mailId = rs.getInt(1);
			Mail mail = new Mail();
			mail.setId(rs.getInt(1));
			mail.setFrom_userid(rs.getInt(2));
			mail.setTo_userid(rs.getInt(3));
			mail.setSend_date(rs.getDate(4));
			mail.setStatus(rs.getInt(5));
			
			ResultSet result = getMailContent(mailId,con);
			while(result.next()){

				MailContent mailContent = new MailContent();
				mailContent.setId(result.getInt(1));
				mailContent.setMessage_content(result.getString(2));
				mailContent.setMail_id(result.getInt(3));
				mailContent.setSubject(result.getString(4));
				mailHashMap.put(mail, mailContent);
			}			
		}
		
		return mailHashMap;
	}
	
	public static ResultSet getMailContent(int mailId,Connection con) throws SQLException{
		String sql1 = "SELECT * FROM mail_content where mail_id = ?";
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.setInt(1, mailId);
		ResultSet rs1 = ps1.executeQuery();
		
		return rs1;
	}
	
	public static String getUserName(int userId,Connection con) throws SQLException{
		String userName = null;
		
		String sql = "SELECT * FROM users where id= ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			userName = rs.getString(2) + " " + rs.getString(3);
		}
		return userName;
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}

	public static MailContent message(int mailId, Connection con) throws SQLException {
		
		String sql = "SELECT * FROM mail_content where mail_id= ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, mailId);
		ResultSet rs = ps.executeQuery();
		MailContent message = new MailContent();
		while(rs.next()){
			message.setId(rs.getInt(1));
			message.setMessage_content(rs.getString(2));
			message.setMail_id(rs.getInt(3));
			message.setSubject(rs.getString(4));
		}
		return message;
	}

	public static HashMap<Integer, String> allUsers(Connection con) throws SQLException{
		String sql = "SELECT * FROM users";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		HashMap<Integer, String> userMap = new HashMap<>();
		while(rs.next()){
			userMap.put(rs.getInt(1), rs.getString(4));
		}
		return userMap;
		
	}
}
