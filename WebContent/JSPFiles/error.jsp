<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/firstStartCSS.css"
	type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/firstStart.jsp"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<button id="ErrorBtn" type="button" data-toggle="modal"
		data-target="#myModal"></button>

	<!-- error popUp -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">


			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"></button>
					<h4 class="modal-title">Error!!</h4>
				</div>
				<div class="modal-body">
					<p id="textError">
						<%=session.getAttribute("Error").toString()%>
					</p>
				</div>
				<div class="modal-footer">
					<div class="formgroup">
						<form method="get"
							action="${pageContext.request.contextPath}/Controller/<%=session.getAttribute("WhereToGoError").toString()%>">
							<input type="submit" value="Done" name="submit" />
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
	<script>
		var btn = document.getElementById("ErrorBtn");
		btn.click();
	</script>
</body>
</html>