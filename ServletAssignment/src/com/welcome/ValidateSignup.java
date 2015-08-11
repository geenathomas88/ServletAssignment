package com.welcome;

public class ValidateSignup {

	public static boolean validateSignup(String fname,String lname,String mailid,String phno,String year,String sex,String pwd,String cpwd){
		boolean flag= false;
		
		String firstname = fname;
		String lastname = lname;
		String email = mailid;
		String phonenumber = phno;
		String age = year;
		String gender = sex;
		String password = pwd;
		String confirmpassword = cpwd;
		
		if((firstname != null && !firstname.isEmpty())&&(lastname != null && !lastname.isEmpty())&&(email != null && !email.isEmpty())&&(phonenumber != null && !phonenumber.isEmpty())&&(age != null && !age.isEmpty())&&(gender != null && !gender.isEmpty())&&(password != null && !password.isEmpty())&&(confirmpassword != null && !confirmpassword.isEmpty()))
		{
			if(password.equals(confirmpassword)){
				User u = new User(firstname, lastname, email, phonenumber, age, gender, confirmpassword);
				flag =  true;
			}
		}
		return flag;
		
	}
}
