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
import javax.servlet.http.HttpSession;

import com.db.DBLayer;
import com.model.User;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uName = req.getParameter("uName");
		String password = req.getParameter("password");

		Connection con = (Connection) getServletContext().getAttribute("connection");
		resp.setContentType("text/html");
				
		try {
			if(User.validateLogin(uName, password, con)){
				User user = DBLayer.findUser(uName, password, con);
				sucess(req,resp,user);					
			}else{
				failure(req,resp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	void sucess(HttpServletRequest req, HttpServletResponse resp,User user) throws ServletException, IOException{
		ServletContext context = req.getServletContext();
		HttpSession  session = req.getSession();
		session.setAttribute("currentLogin", user.getId());
		context.setAttribute("currentUser", user);
		RequestDispatcher rd = req.getRequestDispatcher("inbox");
		rd.forward(req,resp);
	}
	void failure(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		PrintWriter pw = resp.getWriter();
		pw.write("<h4 style= 'color:red'>Sorry!! Incorrect User Name/ Password. Try Again.</h4>");
		RequestDispatcher rd = req.getRequestDispatcher("/login.html");
		rd.include(req,resp);
	}
}
