<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="com.hit.model.Item, java.util.*"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/firstStartCSS.css"
	type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/firstStart.jsp"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<%List<Item> items = (List<Item>) session.getAttribute("items");%>
<!-- userHello popUp -->
<button class="button hidden" id="userH" type="button" data-toggle="modal" data-target="#UserHello"></button>

	<div class="modal fade" id="UserHello" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"></button>
					<h4 class="modal-title">Hello <%=request.getCookies()[0].getValue()%></h4>
				</div>
					<div class="modal-body">
						<p>you have to complete <%=items.size()%> more assignments</p>
					</div>
					<div class="modal-footer">
					<form method="get"
							action="${pageContext.request.contextPath}/Controller/LogIn">
						<div class="formgroup">
							<input type="submit" value="Done" name="submit" />
						</div>
					</form>
					</div>
			</div>
		</div>
	</div>
</body>

<script >
$('#userH').click();
</script>
</html>