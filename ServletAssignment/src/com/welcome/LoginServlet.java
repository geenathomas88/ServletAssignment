package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uName = req.getParameter("uName");
		String password = req.getParameter("password");
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		
		if(User.validateLogin(uName, password)){
			pw.write("<h2 style='color:blue' align= center>Welcome</h2>");
			pw.write("<h4 align = right>You are logged in as "+uName+"</h4>");
		}else{
			System.out.println("here");
			pw.write("<h4 style= 'color:red'>Sorry!! Incorrect User Name/ Password. Try Again.</h4>");
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.include(req,resp);
		}
	}
}
