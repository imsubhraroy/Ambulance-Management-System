<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.connection.*"%>
<%
	String driverId=request.getParameter("id");
	int deleteDriver=DatabaseConnection.insertUpdateFromSqlQuery("delete from tbldriver where driver_id='"+driverId+"'");
	if(deleteDriver>0){
		response.sendRedirect("admin-view-drivers.jsp");
	}
%>