package com.welcome;

import java.util.HashMap;

public class User {

	String firstname,lastname, email,phonenumber,age,gender, password;
	static HashMap<String, String> users = new HashMap<String,String>();
	
	public User(String firstname, String lastname, String email, String phonenumber, String age, String gender,
			String password) {

		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.age = age;
		this.gender = gender;
		this.password = password;
		users.put(firstname, password);
	}
	
	public static boolean validateLogin(String fname,String pwd){
		boolean flag = false;
		String fn = fname;		
		String p = pwd;		
		
		if(users.containsKey(fn) && (users.get(fname).equals(p))){
			flag=true;}
		
		return flag;
		
	}
}
