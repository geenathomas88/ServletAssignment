package com.pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.User;

public class ShowUserDetails extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		
		ServletContext context = getServletContext();
		User currentUser = (User) context.getAttribute("currentUser");
		
		HttpSession session = req.getSession(false);
		System.out.println("Seessiiooo---"+session.getAttribute("currentLogin"));
		if(session.getAttribute("currentLogin")!=null)
		{
			req.getRequestDispatcher("header.html").include(req, resp);
			pw.write("<h2 style='color:blue' align= center>Welcome</h2>");
			pw.write("<h4 align = right>You are logged in as "+currentUser.getUsername()+"</h4>");
			pw.write("<table style='background-color: #F0F8FF;border: 1px solid blue;width:50% ' align='center'><tr bgcolor='#00BFFF'><th style='text-align:center'>Columns</th><th>Values</th></tr>");
			pw.write("<tr><td>User Name:</td><td>"+currentUser.getUsername()+"</td></tr>");
			pw.write("<tr><td>Name:</td><td>"+currentUser.getFirstname()+currentUser.getLastname()+"</td></tr>");
			pw.write("<tr><td>Email:</td><td>"+currentUser.getEmail()+"</td></tr>");
			pw.write("<tr><td>Age:</td><td>"+currentUser.getAge()+"</td></tr>");
			pw.write("<tr><td>Phone Number:</td><td>"+currentUser.getPhonenumber()+"</td></tr>");
			pw.write("<tr><td>Gender:</td><td>"+currentUser.getGender()+"</td></tr>");
			req.getRequestDispatcher("footer.html").include(req, resp);
		}else{
			pw.write("<h4 style= 'color:red'>Please Login!.</h4>");
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.include(req,resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
