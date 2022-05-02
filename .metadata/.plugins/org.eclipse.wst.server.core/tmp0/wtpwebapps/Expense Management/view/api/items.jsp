<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>

<%
String str = "{ \"items\" :"     ;
str +=  request.getAttribute("data") +"}";
out.println(str);
%>