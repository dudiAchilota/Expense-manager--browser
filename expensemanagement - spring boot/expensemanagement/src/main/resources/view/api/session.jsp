<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<%
	if(request.getSession().getAttribute("user") != null) {
		request.setAttribute("ok", 1); 
	}else {
		request.setAttribute("ok", 0);
	}

	String str = "{ \"user\" : " + "\"" + request.getSession().getAttribute("user")+ "\" " ;
	str += ", \"ok\" : " + "\"" + request.getAttribute("ok")+ "\" " ;
	str += "}";
	out.println(str); 

%>