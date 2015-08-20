package com.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import com.db.DBLayer;

public class Mail {

	int id;
	int to_userid;
	int from_userid;
	Date send_date;
	int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(int to_userid) {
		this.to_userid = to_userid;
	}
	public int getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(int from_userid) {
		this.from_userid = from_userid;
	}
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
		
	public String get_UserName(int id,Connection con) throws SQLException{
		System.out.println(id);
		String uName = DBLayer.getUserName(id, con);
		
		return uName;
	}
	
	public Mail(){
		super();
	}
}
