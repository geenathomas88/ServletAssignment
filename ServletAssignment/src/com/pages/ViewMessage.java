package com.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.DBLayer;
import com.model.Mail;
import com.model.MailContent;
import com.model.User;

public class ViewMessage extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession  session = req.getSession();
		
		Connection con = (Connection) context.getAttribute("connection");
		int sessionId = (int) session.getAttribute("currentLogin");
		User currentUser = (User) context.getAttribute("currentUser");
		
		PrintWriter pw = resp.getWriter();
		String mailid = req.getParameter("mailid");
		int mailId = Integer.parseInt(mailid);
		try {
			MailContent message = (MailContent) DBLayer.message(mailId, con) ;
			req.getRequestDispatcher("header.html").include(req, resp);
			pw.write("<h2 style='color:blue' align= center>View Message</h2>");
			
			pw.write("<h4 align = right>You are logged in as "+currentUser.getUsername()+"</h4>");
			pw.write("<table style='background-color: #F0F8FF;border: 1px solid blue;width:50% ' align='center'><tr bgcolor='#00BFFF'><th>Subject :"+message.getSubject()+"</th></tr>");
			
			pw.write("<tr><td>"+message.getMessage_content()+"</td></tr>");
			req.getRequestDispatcher("footer.html").include(req, resp);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
}
