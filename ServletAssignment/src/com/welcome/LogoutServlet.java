package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		HttpSession session = req.getSession();
		session.setAttribute("currentLogin", "");
		session.removeAttribute("currentLogin");
		System.out.println("Seessiiooo in logout---"+session.getAttribute("currentLogin"));
		session.setMaxInactiveInterval(0);
		session.invalidate();
		
		pw.write("<h4 style= 'color:red'>Logged Out Successfully!</h4>");
		RequestDispatcher rd = req.getRequestDispatcher("/login.html");
		rd.include(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
