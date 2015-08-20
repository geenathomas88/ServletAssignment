package com.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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

public class DraftsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		showMail(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	void showMail(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		ServletContext context = getServletContext();
		HttpSession  session = req.getSession();
		
		Connection con = (Connection) context.getAttribute("connection");
		int sessionId = (int) session.getAttribute("currentLogin");
		User currentUser = (User) context.getAttribute("currentUser");
		
		PrintWriter pw = resp.getWriter();
		HashMap<Mail, MailContent> messageHash = new HashMap<Mail, MailContent>();
		
		try {
			
			messageHash = getDrafts(sessionId, con);
			
			req.getRequestDispatcher("header.html").include(req, resp);
			pw.write("<h2 style='color:blue' align= center>Drafts</h2>");
			
			pw.write("<h4 align = right>You are logged in as "+currentUser.getUsername()+"</h4>");
			pw.write("<table style='background-color: #F0F8FF;border: 1px solid blue;width:50% ' align='center'><tr bgcolor='#00BFFF'><th>From</th><th>Subject</th><th>Date</th></tr>");
			
			Iterator<Entry<Mail, MailContent>> messageIterator = messageHash.entrySet().iterator();
			while(messageIterator.hasNext()){
				Entry<Mail, MailContent> messageEntry = messageIterator.next();
				Mail mail = (Mail) messageEntry.getKey();
				MailContent mailContent= (MailContent) messageEntry.getValue();
				
				pw.write("<tr><td>"+mail.get_UserName(mail.getTo_userid(), con)+"</td>");
				pw.write("<td>"+mailContent.getSubject()+"</td>");
				pw.write("<td>"+mail.getSend_date()+"</td></tr>");
			}
			
			req.getRequestDispatcher("footer.html").include(req, resp);
						
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	HashMap<Mail, MailContent> getDrafts(int sessionId, Connection con) throws SQLException{
		HashMap<Mail, MailContent> messages = new HashMap<Mail, MailContent>();
		messages = DBLayer.getSentMail(sessionId, con,0);
		return messages;
	}


}
