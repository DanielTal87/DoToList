<%@page import="com.hit.javaBeans.DailyTip"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/JSPFiles/firstStartCSS.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="tip" scope="session" class="com.hit.javaBeans.DailyTip" />
	<jsp:setProperty name="tip" property="*" />
	
	<button id="dayTip" class="button hidden" type="button" data-toggle="modal" data-target="#dailyTip">Update User</button>
	
	<div class="modal fade" id="dailyTip" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"></button>
				</div>
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/LogIn">
					<div class="modal-body">
						<div class="formgroup">
							<input type="hidden" value="<%=session.getAttribute("username") %>"
								name="username" /><br />
						</div>
						<div class="formgroup">
							<input type="hidden" value="<%=session.getAttribute("password") %>" name="password" /><br />
						</div>
						<p>
						<%
						DailyTip tip1 = (DailyTip) (session.getAttribute("tip"));
						out.println(tip1.getTip());
						%>
						<p>
					</div>
					<div class="modal-footer">
						<button class="button" type="submit">Thank You</button>
					</div>
				</form>
			</div>
	</div>
	</div>
</body>
<script>
$('#dayTip').click();
</script>
</html>