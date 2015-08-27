package com.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBLayer;
import com.model.User;

public class MessageCompose extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			getMessageContent(req, resp);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private void getMessageContent(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
		ServletContext context = getServletContext();
		User currentUser = (User) context.getAttribute("currentUser");
		Connection con = (Connection) context.getAttribute("connection");
		HashMap<Integer, String> users = DBLayer.allUsers(con);
		PrintWriter pw = resp.getWriter();
		
		pw.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
				    "<tr>"+
				        "<td>"+
				            "<img src=\"header.jpg\" width=\"100%\" height=\"65\" border=\"0\" alt=\"header\"></a>"+
				        "</td>"+
				    "</tr>"+
				    "<tr>"+
				       "<td align=\"right\">"+
				            "<a href=\"showUser\">Profile</a> |"+          
				            "<a href=\"sent\">Sent</a> |"+
				           	"<a href=\"#\">Compose</a> |"+
				           	"<a href=\"inbox\">Inbox</a> |"+
				           	"<a href=\"drafts\">Drafts</a> |"+
				           	"<a href=\"logout\">Logout</a>"+
				        "</td>"+
				    "</tr>"+
				"</table>");
		
		
		
		pw.write("<h2 style='color:blue' align= center>Compose Mail</h2>");
		pw.write("<h4 align = right>You are logged in as "+currentUser.getUsername()+"</h4>");
		
		pw.write("<form action='composemail' method='post'>");
		pw.write("<table style=\"background-color: #F0F8FF;border: 1px solid blue; width: 50%\" align=\"center\">"+
					"<tr><td>To :</td>"+
					"<td>");
		pw.write("<select name='to_id'>");

				Iterator<Entry<Integer,String>> it = users.entrySet().iterator();
				while(it.hasNext()){
					Entry<Integer, String> e = it.next();
					pw.write("<option value="+e.getKey()+">"+e.getValue()+"</option>");					
				}								
				
		pw.write("</select>");
		pw.write("</td></tr>");		
		
		pw.write("<tr><td>Subject: </td><td><input type=\"text\" name =\"subject\"></td></tr>"+
				"<tr><td>Message : </td><td><textarea  rows=\"20\" cols=\"50\" name=\"message\"></textarea></td>"+
				"<tr><td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Send\" name=\"action\" style='background-color: LightSkyBlue '>"+
														"<input type=\"submit\" value=\"Save\" name=\"action\" style='background-color: LightSkyBlue '>"+
				"</td></tr>");
		
		pw.write("</table></form>");
		pw.write("<div id=\"footer\" style=\"position:auto;background:#6cf;text-align: center;bottom:0; width:100%;height:25px;\">Copyright 2015-2017 by Application Developer. All Rights Reserved.</div>");
	}
	void getUsers(Connection con) throws SQLException{
		HashMap<Integer, String> users = DBLayer.allUsers(con);
		
			
	}
}
