<%@ page language="java" import="com.hit.model.Item, java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/FirstStartCSS.css"
	type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/FirstStart.jsp"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<!--<form method="get" action="${pageContext.request.contextPath}/Controller/AddItem">
<input type="text" required pattern="\w+" value=""
								placeholder="assingment.." name="assingment" size="25" />
<input type="text" required pattern="\w+" value=""
						placeholder="category.." name="category" size="25" />
<input type="submit" value="add" name="submit" class="center" />
</form> -->

	<!--<form method="get" action="${pageContext.request.contextPath}/Controller/UpdateItem">
<input type="text" required pattern="\w+" value="" placeholder="oldaAssingment.." name="oldaAssingment" size="25" /><!-- read only
<input type="text" required pattern="\w+" value=""
						placeholder="assingment.." name="assingment" size="25" />
<input type="text" required pattern="\w+" value=""
						placeholder="category.." name="category" size="25" />
<input type="submit" value="updateItem" name="submit" class="center" />
</form>-->

	<!--<form method="get" action="${pageContext.request.contextPath}/Controller/DeleteItem">
<!%--session.setAttribute("assingment", "test"); --%>
<input type="submit" value="delete" name="submit" class="center" />
</form>   -->

	<!--<form method="get" action="${pageContext.request.contextPath}/Controller/DeleteUser">
<input type="submit" value="delete" name="submit" class="center" />
</form>-->

	<!--<form method="get" action="${pageContext.request.contextPath}/Controller/UpdateUser">
<input type="text" required pattern="\w+" value="" placeholder="newUserName.." name="newUserName" size="25" />
<input type="text" required pattern="\w+" value=""
						placeholder="password.." name="password" size="25" />
<input type="submit" value="updareUser" name="submit" class="center" />
</form>  -->
	<button id="AddItem" type="button" data-toggle="modal"
		data-target="#myModal">Add item</button>

	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"></button>
					<h4 class="modal-title">Add Item</h4>
				</div>
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/AddItem">
					<div class="modal-body">
						<div class="formgroup">
							assignment: <input type="text" value=""
								placeholder="assingment.." name="assingment" /><br />
						</div>
						<div class="formgroup">
							category: <input type="text" value="" placeholder="category.."
								name="category" /><br />
						</div>
					</div>
					<div class="modal-footer">
						<div class="formgroup">
							<input type="submit" value="Done" name="submit" />

							<!--<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="close();">Done</button>  -->
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>

	<table>
		<th>Check</th>
		<th>Assignment</th>
		
		<th>Category</th>
		<%
			List<Item> items = (List<Item>) session.getAttribute("items");
			if (items != null) {
				for (int i = 0; i < items.size(); i++) {
		%>
		<tr>
			<td><input type="checkbox" /></td>
			<td>
				<%
					out.print(items.get(i).getAssignment());
				%>
			</td>
			<td>
				<%
					out.print(items.get(i).getCategory());
				%>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
<script>
window.onload = function() {
	<%String btnClick =(String)session.getAttribute("ButtonToClick").toString();
	if(!btnClick.equals("null")){
	%>
		var butnClick = document.getElementById('<%=btnClick%>');
		butnClick.click();
	<%}%>
};
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
</script>
</html>