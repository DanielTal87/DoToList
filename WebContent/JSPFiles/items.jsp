<%@ page language="java" import="com.hit.model.Item, java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/firstStartCSS.css"
	type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/firstStart.jsp"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
	<!-- items list -->
	<div class="container">
	<!--<h3>Hello <!%=session.getAttribute("username")%></h3>  -->
		<h2>your To-Do list</h2>
		<table class="table table-striped table-hover" style="width: 80%">
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

					<td style="width: 4%"><input type="checkbox" id="<%=i%>"
						class="checkIds" value="<%=items.get(i).getAssignment()%>"
						name="<%=items.get(i).getCategory()%>" /></td>
					<td style="width: 45%">
						<%
							out.print(items.get(i).getAssignment());
						%>
					</td>
					<td style="width: 40%">
						<%
							out.print(items.get(i).getCategory());
						%>
					</td>
					<td style="width: 11%">
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
	<table style="width:150%" align="center">
	<tr>
			<td style="width: 33%">
				<!-- updateItem button -->
				<button class="button" id="UpdateItem" type="button" data-toggle="modal">Update Item</button>
			</td>
			
			<td style="width: 33%">
				<!-- addItem button -->
				<button class="button" id="AddItem" type="button" data-toggle="modal" data-target="#AddIteModel">Add item</button>
			</td>
			
			<td style="width: 33%">
				<!-- updateUser button -->
				<button  class="button" type="button" data-toggle="modal" data-target="#UpdateUserModel">Update User</button>
			</td>
		</tr>
		<tr>
			<td style="width: 33%">
				<!-- deleteItem button -->
				<form method="get" id="delForm"
					action="${pageContext.request.contextPath}/Controller/DeleteItem">
					<input type="hidden" name="idAssignmentDelete" id="deleteId"
						value="" />
					<button class="button" id="DeleteItem" type="button">Delete Item</button>
				</form>
			</td>
			
			<td style="width: 33%">
				<!-- deleteAllItem button -->
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/DeleteAllItem">
					<button class="button" id="DeleteAllItem" type="submit">Delete All Item</button>
				</form>
			</td>
			
			<td style="width: 33%">
			 <!-- deleteUser button -->
				<form method="get"
					action="${pageContext.request.contextPath}/Controller/DeleteUser">
					<button class="button" type="submit">Delete User</button>
				</form>
				
			</td>
		</tr>
	</table>
	
    <!-- dailyTip button -->
	<form method="get" id="dailyTipForm" class="up" action="${pageContext.request.contextPath}/Controller/Tip">
	<a href="#hiddenType" id="DailyTip"><i title="Daily Tip" class="fa fa-commenting-o fa-2x " aria-hidden="true"></i></a>
		<!--<a type="button" id="tipButton">
	      <span  class="fa fa-commenting-o fa-2x"></span> Daily Tip
	    </a>  -->
	    <div id="hiddenType">
  	<input id="dailyInput" type="text" placeholder="please enter the day.." name="day" > 
 	<button id="dailySubmit" type="submit">Done</button></div>
	</form>
	
	<!-- logOut button -->
	<form method="get" id="logOutForm" class="up" action="${pageContext.request.contextPath}/Controller/LogOut">
		<!--<button class="button" type="submit">Log Out</button>  -->
			<a id="logOut"><i title="Log Out" class="fa fa-power-off fa-2x" aria-hidden="true"></i></a>
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
								name="assingmentToUpdate" /><br />
						</div>
						<div class="formgroup">
							category: <input type="text" value="" name="categoryToUpdate"
								id="categoryToUpdate" /><br />
						</div>
					</div>
					<div class="modal-footer">
						<div class="formgroup">
							<input type="hidden" name="idAssignmentUpdate" id="updateId"
								value="" /> <input type="submit" value="Done" name="submit" />

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
								placeholder="assingment.." name="assingment" /><br />
						</div>
						<div class="formgroup">
							category: <input type="text" value="" placeholder="category.."
								name="category" /><br />
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
								name="newUserName" /><br />
						</div>
						<div class="formgroup">
							password: <input type="text"
								value=<%=session.getAttribute("password").toString()%>
								name="password" /><br />
						</div>

						<div class="formgroup">
							email:<br /> <input type="text"
								value=<%=session.getAttribute("email").toString()%> name="email" /><br />
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
<script>

$('#dailyInput').hide();	
$('#dailySubmit').hide();

		$('#DailyTip').on('click',function(){
			$('#dailyInput').show();	
			$('#dailySubmit').show();	
		});
		
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		};
		
		$('#UpdateItem').on('click',function(){
			var id=$('#deleteId').val();
			$('#assingmentToUpdate').val($('#'+id).val());
			$('#categoryToUpdate').val($('#'+id).attr('name'));
			$(this).attr( 'data-target','#UpdateIteModel');
		});
		
		$('#DeleteItem').on('click',function(){
			$('#delForm').submit();
		});
		
		 $(document).on('click','.checkIds',function(){
			if($(this).prop('checked')){
				$('#deleteId').val($(this).attr('id'));
				$('#updateId').val($(this).attr('id'));
			}else{
				$('#deleteId').val("");
				$('#updateId').val("");
			}
		});
		 
		 $('input[type="checkbox"]').on('change', function() {
			   $('input[type="checkbox"]').not(this).prop('checked', false);
		});
		 
		 $('#logOut').on('click',function(){
			 $('#logOutForm').submit();
		 });
		 
		window.onload = function() {
			<%String btnClick = (String) session.getAttribute("ButtonToClick").toString();
			if (!btnClick.equals("null")) {%>
				var butnClick = document.getElementById('<%=btnClick%>');
		butnClick.click();
<%}%>
	};
</script>
</html>