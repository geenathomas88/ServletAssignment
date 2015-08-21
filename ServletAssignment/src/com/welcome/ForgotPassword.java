package com.welcome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mailer.SendMail;

public class ForgotPassword  extends HttpServlet {
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	String action = req.getParameter("action");
        resp.setContentType("text/html;charset=UTF-8");
                       
        if(action.equals("Send")){
			sendPassword(req, resp);
		}
		else if(action.equals("Cancel")){
			cancel(req, resp);
		}	
    }
    void sendPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
    	 PrintWriter out = resp.getWriter();
    	 String to = req.getParameter("email");
         String subject = "Password Recovery";
         String message = "password";
         String user = "itstough0@gmail.com";
         String pass = "dummy123456789";
         SendMail.send(to,subject, message, user, pass);
         out.println("Mail send successfully");
         RequestDispatcher rd = req.getRequestDispatcher("/login.html");
		 rd.include(req, resp);
    }
    void cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	RequestDispatcher rd = req.getRequestDispatcher("/login.html");
		rd.forward(req, resp);
    }
}