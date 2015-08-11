package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Signup Servlet invoked");
		String action = req.getParameter("action");
		PrintWriter pw = resp.getWriter();
		
		if(action.equals("Submit")){//create user
			String firstname = req.getParameter("firstname");
			String lastname = req.getParameter("lastname");
			String email = req.getParameter("email");
			String phonenumber = req.getParameter("phonenumber");
			String age = req.getParameter("age");
			String gender = req.getParameter("gender");
			String password = req.getParameter("password");
			String confirmpassword = req.getParameter("confirmpassword");			
			
			if(ValidateSignup.validateSignup(firstname, lastname, email, phonenumber, age, gender, password, confirmpassword)){
				pw.write("<h4 style= 'color:blue'>New user created.</h4>");
				RequestDispatcher rd = req.getRequestDispatcher("/login.html");
				rd.include(req, resp);}
			else{
				pw.write("<h4 style= 'color:red'>Sorry!! Some errors prevented from creating new user. Try again.</h4>");
				RequestDispatcher rd = req.getRequestDispatcher("/signup.html");
				rd.include(req,resp);
			}
		}
		else if(action.equals("Cancel")){
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.forward(req, resp);
		}
	}
}
