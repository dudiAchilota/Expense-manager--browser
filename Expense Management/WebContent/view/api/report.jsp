<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<%
String str = "{ \"items\" :" + request.getAttribute("items");
str += ", \"sum\" :" + request.getAttribute("sum");
str +=  "}";
out.println(str);
%>