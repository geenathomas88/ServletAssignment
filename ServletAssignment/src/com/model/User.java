package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;

import com.db.DBLayer;

public class User {

	int id;
	String firstname;
	String lastname;
	String username;
	String email;
	String phonenumber;
	int age;
	String gender;
	String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {
		super();
	}
	public User(String firstname, String lastname, String username, String email, String phonenumber, int age,
			String gender, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.phonenumber = phonenumber;
		this.age = age;
		this.gender = gender;
		this.password = password;
	}

	public static boolean validateLogin(String uname,String pwd,Connection con) throws SQLException{
		
		User user =null;
		user = DBLayer.findUser(uname, pwd, con);
		System.out.println("In user------"+user.getId());
		if (user== null)
			return false;
		else
			return true;	
	}
	
	public static boolean validateSignUp(User user, Connection con) throws SQLException{
		boolean flag =false;
		String errorMessage = "";
		
		if(!DBLayer.checkUsername(user.getUsername(),con)){
			if((!user.getFirstname().isEmpty()) && (!user.getLastname().isEmpty())&&(!user.getUsername().isEmpty())&&(!user.getEmail().isEmpty())&&(!user.getGender().isEmpty())&&(!user.getPhonenumber().isEmpty())&&(!user.getPassword().isEmpty()))		
			{
				errorMessage ="Success, All fields are entered";
				return true;
			}
			else			
				errorMessage ="FAiled, All fields are required.";
		}
		else{
			errorMessage ="User name already in use, please choose another username";
		}
		System.out.println(errorMessage);
		return flag;
	}
}
