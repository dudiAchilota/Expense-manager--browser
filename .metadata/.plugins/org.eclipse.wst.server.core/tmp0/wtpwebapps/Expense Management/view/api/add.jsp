<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<%
	String str = "{ \"ok\" :" + request.getAttribute("ok");
	str += ", \"message\" :" + " \" " + request.getAttribute("message") + " \" " ;
	str +=  "}";
	out.println(str);
%>