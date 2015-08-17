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
import javax.servlet.http.HttpSession;

import com.common.User;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uName = req.getParameter("uName");
		String password = req.getParameter("password");
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
				
		try {
			if(validateLogin(uName, password)){
				
				ServletContext context = getServletContext();
				User currentUser = (User) context.getAttribute("currentUser");
				
				HttpSession session = req.getSession();
				session.setAttribute("currentLogin", currentUser.getUsername());
				
				RequestDispatcher rd = req.getRequestDispatcher("showUser");
				rd.forward(req,resp);
				
			}else{
				pw.write("<h4 style= 'color:red'>Sorry!! Incorrect User Name/ Password. Try Again.</h4>");
				RequestDispatcher rd = req.getRequestDispatcher("/login.html");
				rd.include(req,resp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private boolean validateLogin(String uname,String pwd) throws SQLException{
		
		ServletContext context = getServletContext();
		String sql = "SELECT * FROM users where username='"+uname+"' and password ='"+pwd+"'";

		Connection conn = (Connection) context.getAttribute("connection");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if (rs.next()){
			User user = new User();
			user.setId(rs.getInt(1));
			user.setFirstname(rs.getString(2));
			user.setLastname(rs.getString(3));
			user.setUsername(rs.getString(4));
			user.setEmail(rs.getString(5));
			user.setPhonenumber(rs.getString(6));
			user.setAge(rs.getInt(7));
			user.setGender(rs.getString(8));
			context.setAttribute("currentUser", user);
			return true;
		}
		else
			return false;		
	}
}
