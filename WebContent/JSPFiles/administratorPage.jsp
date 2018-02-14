<%@page import="java.util.* ,javax.servlet.http.HttpSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="reset/reset.css" />
<link href="${pageContext.request.contextPath}/firstStartCSS.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Administrator Page</title>
</head>
<body>
	<h2>Administrator Page</h2>
	<p>List of all the active users</p>
	<table class="table table-striped table-hover tabSize">
		<thead>
			<tr>
				<th>Session Name</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<HttpSession> sessions = (List<HttpSession>)session.getAttribute("sessionList");
				if (sessions != null)
				{
					for (int i = 0; i < sessions.size(); i++)
					{
			%>
			<tr>
				<td>
					<%
						if (((HttpSession)sessions.get(i)).getAttribute("username") != null)
									out.print(((HttpSession)sessions.get(i)).getAttribute("username").toString());
					%>
				</td>
			</tr>

			<%
				}
				}
			%>
		</tbody>
	</table>
</body>
</html>
