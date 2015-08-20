package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBLayer;
import com.model.User;

public class SignupServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//System.out.println("Signup Servlet invoked");
		String action = req.getParameter("action");
		ServletContext context = getServletContext();
		Connection con = (Connection) context.getAttribute("connection");
		
		if(action.equals("Submit")){//create user
			String username = req.getParameter("username");
			String firstname = req.getParameter("firstname");
			String lastname = req.getParameter("lastname");
			String email = req.getParameter("email");
			String phonenumber = req.getParameter("phonenumber");
			String year = req.getParameter("age");
			int age = Integer.parseInt(year.trim());
			String gender = req.getParameter("gender");
			String password = req.getParameter("password");
			String confirmpassword = req.getParameter("confirmpassword");			
			
			User user = new User(firstname, lastname, username, email, phonenumber, age, gender, password);
			try {
				if(User.validateSignUp(user,con)){
					if(DBLayer.createUser(user,con))
						sucess(req, resp);
					}
				else{
					failure(req,resp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(action.equals("Cancel")){
			cancel(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	void sucess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		PrintWriter pw = resp.getWriter();
		pw.write("<h4 style= 'color:blue'>New user created.</h4>");
		RequestDispatcher rd = req.getRequestDispatcher("/login.html");
		rd.include(req, resp);
	}
	
	void failure(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		PrintWriter pw = resp.getWriter();
		pw.write("<h4 style= 'color:red'>Sorry!! Some errors prevented from creating new user. Try again.</h4>");
		RequestDispatcher rd = req.getRequestDispatcher("/signup.html");
		rd.include(req,resp);
	}
	
	void cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		RequestDispatcher rd = req.getRequestDispatcher("/login.html");
		rd.forward(req, resp);
	}
}
