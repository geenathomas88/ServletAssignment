package com.pages;

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

public class ComposeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		resp.setContentType("text/html");
		String message = req.getParameter("message");
		String to_id = req.getParameter("to_id");
		String subject = req.getParameter("subject");
		String action = req.getParameter("action");
		
		int to = Integer.parseInt(to_id);
				
		if(action.equals("Send")){
			int status = 1;
			sendMail(req,resp,to,message,subject,status);
		}else if (action.equals("Save")) {
			int status = 0;
			saveDrafts(req, resp,to,message,subject,status);
			
		}		
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	private void sendMail(HttpServletRequest req,HttpServletResponse resp,int to, String message, String subject, int status) throws IOException, ServletException {
		PrintWriter pw = resp.getWriter();
				
		ServletContext context = getServletContext();
		HttpSession  session = req.getSession();
		Connection con = (Connection) context.getAttribute("connection");
		int from = (int) session.getAttribute("currentLogin");
		try {
			if(DBLayer.insertMail(to,from,message,subject,status,con)){
				//send mail code goes here---
				pw.write("Mail send successfully");
				RequestDispatcher rd = req.getRequestDispatcher("inbox");
				rd.include(req,resp);}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void saveDrafts(HttpServletRequest req,HttpServletResponse resp,int to, String message, String subject, int status) throws IOException, ServletException {
		PrintWriter pw = resp.getWriter();
			
		ServletContext context = getServletContext();
		HttpSession  session = req.getSession();
		Connection con = (Connection) context.getAttribute("connection");
		int from = (int) session.getAttribute("currentLogin");
		try {
			if(DBLayer.insertMail(to,from,message,subject,status,con)){
				pw.write("Mail saved to drafts");
				RequestDispatcher rd = req.getRequestDispatcher("inbox");
				rd.include(req,resp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
