<%@page import="com.hit.model.Item, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="${pageContext.request.contextPath}/FirstStartCSS.css"
	type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/FirstStart.jsp"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>

<table>
		<th>Check</th>
		<th>Assignment</th>

		<th>Category</th>  
		<% List<Item> items = (List<Item>) session.getAttribute("items");
			//request.setAttribute("listItems", items);
			if (items != null) {
				for (int i = 0; i < items.size(); i++) {
		%>
		<tr>
			<td><input type="checkbox" id="<%=i%>" class="checkIds"
				value=<%=items.get(i).getAssignment().toString()%>
				name=<%=items.get(i).getCategory().toString()%> />
				</td>
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
	

	<button id="btn" type="button" data-toggle="modal" data-target="#myModal">Add item</button> 
	
	 <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"></button>
          <h4 class="modal-title">Add Item</h4>
        </div>
        <div class="modal-body">
          <div class="formgroup">
        		assignment: <input type="text" value="" placeholder="assingment.."
          			name="assingment"  /><br/>
          </div>
          <div class="formgroup">
          		category: <input type="text" value="" placeholder="category.."
          			name="category"  /><br/>
          </div>
        </div>
       <div class="modal-footer">
         <div class="formgroup">
          		<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="window.location.href = 'FirstStart.jsp'">Done</button>
       	 </div> 
        </div>
      </div>
      
    </div>
  </div>
  <script>
</script>
</body>
</html>