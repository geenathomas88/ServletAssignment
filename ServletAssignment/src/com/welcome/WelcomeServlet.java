package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Welcome Servlet invoked");
		String action = req.getParameter("action");
		
		if(action.equals("Login")){
			RequestDispatcher rd = req.getRequestDispatcher("login");
			rd.forward(req, resp);
		}
		else if(action.equals("SignUp")){
			RequestDispatcher rd = req.getRequestDispatcher("/signup.html");
			rd.forward(req, resp);
		}		
	}
}
