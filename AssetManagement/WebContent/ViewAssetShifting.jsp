<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
String function=(String)session.getAttribute("function");
try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/asset";
		String user="root";
		String pass="lokesh1999";
		
		Connection con=(Connection) DriverManager.getConnection(url,user,pass);
		
		if(function.equalsIgnoreCase("ViewByAssetID"))
		{
			String sql1="Select tolocation,toroom,time from location where asset_id=?";
			String asset_id=(String)session.getAttribute("asset_id");
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(sql1);
			ps.setString(1,asset_id);
			ResultSet rs=ps.executeQuery();
			/*out.println("<table class=\"table\">");
			out.println(" <tbody>");*/
			out.println("<h1>Shifting History of "+asset_id+"</h1>");
			while(rs.next())
				out.println(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3));
			}
		if(function.equalsIgnoreCase("ViewByStaffID"))
		{
			String sql1="Select prod_id,asset_id,tolocation,toroom,time from location where UserId=?";
			String staff_id=(String)session.getAttribute("staff_id");
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(sql1);
			ps.setString(1,staff_id);
			ResultSet rs=ps.executeQuery();
			/*out.println("<table class=\"table\">");
			out.println(" <tbody>");*/
			out.println("<h1>Shifting History by "+staff_id+"</h1>");
			while(rs.next())
				out.println(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5));
		}
		if(function.equalsIgnoreCase("ViewByDate"))
		{
			
		}
}catch(Exception e)
{
	e.printStackTrace();
}

%>
</body>
</html>