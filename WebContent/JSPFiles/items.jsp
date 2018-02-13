<%@ page language="java" import="com.hit.model.Item, java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="reset/reset.css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/firstStartCSS.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
</head>
<body>
	<!-- items list -->
	<div class="container">
		<h2>your To-Do list</h2>
		<table class="table table-striped table-hover tabSize">
			<thead>
				<tr>
					<th>#</th>
					<th>Assignment</th>
					<th>Category</th>
					<th>Last Modify</th>
				</tr>
			</thead>
			<tbody>
				<%
					List<Item> items = (List<Item>) session.getAttribute("items");
					//request.setAttribute("listItems", items);
					if (items != null) {
						for (int i = 0; i < items.size(); i++) {
				%>
				<tr>

					<td class="idSize"><input type="checkbox" id="<%=i%>"
						class="checkIds" value="<%=items.get(i).getAssignment()%>"
						name="<%=items.get(i).getCategory()%>" /></td>
					<td class="assSize">
						<%
							out.print(items.get(i).getAssignment());
						%>
					</td>
					<td class="catSize">
						<%
							out.print(items.get(i).getCategory());
						%>
					</td>
					<td class="lasmSize">
						<%
							out.print(items.get(i).getLastmodify());
						%>
					</td>
				</tr>
				<%
					}
					}
				%>
			</tbody>
		</table>
	</div>

	<!----------------------------------buttons-------------------------------->
	<table class="tabButSize" align="center">
		<tr>
			<td class="tdButSize">
				<!-- updateItem button -->
				<button class="button" id="UpdateItem" type="button"
					data-toggle="modal">Update Item</button>
			</td>

			<td class="tdButSize">
				<!-- addItem button -->
				<button class="button" id="AddItem" type="button"
					data-toggle="modal" data-target="#AddIteModel">Add item</button>
			</td>

			<td class="tdButSize">
				<!-- updateUser button -->
				<button class="button" type="button" data-toggle="modal"
					data-target="#UpdateUserModel">Update User</button>
			</td>
		</tr>
		<tr>
			<td class="tdButSize">
				<!-- deleteItem button -->
				<form method="get" id="delForm"
					action="${pageContext.request.contextPath}/Controller/DeleteItem">
					<input type="hidden" name="idAssignmentDelete" id="deleteId"
						value="" required pattern="\w+" />
					<button class="button" id="DeleteItem" type="button">Delete
						Item</button>
				</form>
			</td>

			<td class="tdButSize">
				<!-- deleteAllItem button -->
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/DeleteAllItem">
					<button class="button" id="DeleteAllItem" type="submit">Delete
						All Item</button>
				</form>
			</td>

			<td class="tdButSize">
				<!-- deleteUser button -->
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/DeleteUser">
					<button class="button" type="submit">Delete User</button>
				</form>

			</td>
		</tr>
	</table>

	<!-- dailyTip button -->
	<form method="get" id="dailyTipForm" class="inLine"
		action="${pageContext.request.contextPath}/Controller/Tip">
		<a href="#hiddenType" id="DailyTip"><i title="Daily Tip"
			class="fa fa-commenting-o fa-2x " aria-hidden="true"></i></a>
		<!--<a type="button" id="tipButton">
	      <span  class="fa fa-commenting-o fa-2x"></span> Daily Tip
	    </a>  -->
		<div id="hiddenType" class="down">
			<input class="inLine" id="dailyInput" type="text"
				placeholder="please enter the day.." name="day" required
				pattern="\w+">
			<button class="inLine" id="dailySubmit" type="submit">Done</button>
		</div>
	</form>

	<!-- logOut button -->
	<form method="get" id="logOutForm" class="inLine"
		action="${pageContext.request.contextPath}/Controller/LogOut">
		<!--<button class="button" type="submit">Log Out</button>  -->
		<a id="logOut"><i title="Log Out" class="fa fa-power-off fa-2x"
			aria-hidden="true"></i></a>
	</form>

	<!----------------------------------popUps-------------------------------->

	<!-- updateItem popUp -->
	<div class="modal fade" id="UpdateIteModel" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"></button>
					<h4 class="modal-title">Update Item</h4>
				</div>
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/UpdateItem">
					<div class="modal-body">
						<div class="formgroup">
							assignment: <input type="text" id="assingmentToUpdate" value=""
								name="assingmentToUpdate" required pattern="\w+" /><br />
						</div>
						<div class="formgroup">
							category: <input type="text" value="" name="categoryToUpdate"
								id="categoryToUpdate" required pattern="\w+" /><br />
						</div>
					</div>
					<div class="modal-footer">
						<div class="formgroup">
							<input type="hidden" name="idAssignmentUpdate" id="updateId"
								value="" required pattern="\w+" /> <input type="submit"
								value="Done" name="submit" />

							<!--<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="close();">Done</button>  -->
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>

	<!-- addItem popUp -->
	<div class="modal fade" id="AddIteModel" role="dialog">
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
								placeholder="assingment.." name="assingment" required
								pattern="\w+" /><br />
						</div>
						<div class="formgroup">
							category: <input type="text" value="" placeholder="category.."
								name="category" required pattern="\w+" /><br />
						</div>
						<p id="test"></p>
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

	<!-- updateUser popUp -->
	<div class="modal fade" id="UpdateUserModel" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"></button>
					<h4 class="modal-title">Update User</h4>
				</div>
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/UpdateUser">
					<div class="modal-body">
						<div class="formgroup">
							userName: <input type="text"
								value=<%=session.getAttribute("username").toString()%>
								name="newUserName" required pattern="\w+" /><br />
						</div>
						<div class="formgroup">
							password: <input type="text"
								value=<%=session.getAttribute("password").toString()%>
								name="password" required pattern="\w+" /><br />
						</div>

						<div class="formgroup">
							email:<br /> <input type="email"
								value=<%=session.getAttribute("email").toString()%> name="email"
								required pattern="\w+" /><br />
						</div>
					</div>
					<div class="modal-footer">
						<div class="formgroup">
							<input type="submit" value="Done" name="submit" />
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
</body>

<script type="text/javascript">
<jsp:include page="/JSFiles/items.js" />
		window.onload = function() {
			<%String btnClick = (String) session.getAttribute("ButtonToClick").toString();
			if (!btnClick.equals("null")) {%>
				var butnClick = document.getElementById('<%=btnClick%>
	');
		butnClick.click();
<%}%>
	};
</script>

</html>
