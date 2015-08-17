package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Signup Servlet invoked");
		String action = req.getParameter("action");
		PrintWriter pw = resp.getWriter();
		
		if(action.equals("Submit")){//create user
			String username = req.getParameter("username");
			String firstname = req.getParameter("firstname");
			String lastname = req.getParameter("lastname");
			String email = req.getParameter("email");
			String phonenumber = req.getParameter("phonenumber");
			String age = req.getParameter("age");
			String gender = req.getParameter("gender");
			String password = req.getParameter("password");
			String confirmpassword = req.getParameter("confirmpassword");			
			
			try {
				if(validateSignup(username,firstname, lastname, email, phonenumber, age, gender, password, confirmpassword)){
					if(createUser(username, firstname, lastname, email, phonenumber, age, gender, password)){
						pw.write("<h4 style= 'color:blue'>New user created.</h4>");
						RequestDispatcher rd = req.getRequestDispatcher("/login.html");
						rd.include(req, resp);}
					}
				else{
					pw.write("<h4 style= 'color:red'>Sorry!! Some errors prevented from creating new user. Try again.</h4>");
					RequestDispatcher rd = req.getRequestDispatcher("/signup.html");
					rd.include(req,resp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(action.equals("Cancel")){
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private boolean validateSignup(String uname,String fname,String lname,String mailid,String phno,String year,String sex,String pwd,String cpwd) throws SQLException{
		boolean flag= false;
		String errorMessage = "success";
		ServletContext context = getServletContext();
		String sql = "SELECT * FROM users where username='"+uname+"'";
		
		Connection conn = (Connection) context.getAttribute("connection");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
				
		if((fname != null && !fname.isEmpty())&&(lname != null && !lname.isEmpty())&&(mailid != null && !mailid.isEmpty())&&(phno != null && !phno.isEmpty())&&(year != null && !year.isEmpty())&&(sex != null && !sex.isEmpty())&&(pwd != null && !pwd.isEmpty())&&(cpwd != null && !cpwd.isEmpty()))
		{
			if(!rs.next()){
				if(pwd.equals(cpwd)){
					flag =  true;
				}else
					errorMessage="Password mismatch";
			}
			else
				errorMessage ="Please use a different user name";
		}
		System.out.println(errorMessage);
	return flag;		
	}
	
	private boolean createUser(String uname,String fname,String lname,String mailid,String phno,String year,String sex,String pwd) throws SQLException{
		boolean flag= false;
		
		ServletContext context = getServletContext();
		String sql = "INSERT INTO users ( firstname, lastname, username, email, phonenumber, age, gender, password) VALUES ('"+fname+"', '"+lname+"', '"+uname+"', '"+mailid+"', '"+phno+"', "+year+", '"+sex+"', '"+pwd+"')";
		System.out.println(sql);
		Connection conn = (Connection) context.getAttribute("connection");
		Statement st = conn.createStatement();
		int result = st.executeUpdate(sql);
		
		if(result==1)
			flag=true;
		return flag;
	}
}
