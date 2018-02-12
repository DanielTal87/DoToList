<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${pageContext.request.contextPath}/FirstStartCSS.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.css"
	rel="stylesheet" type='text/css' />
<link rel="stylesheet"
	href="http://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.css" />
<title>My To-Do List</title>
</head>
<h1>
	<i class="fa fa-list-ol h1Icon" aria-hidden="true"></i> My To-Do List
</h1>
<body>
	<div id="tabsFor" class="container">
		<ul class="nav nav-tabs nav-justified">
			<li class="active"><a data-toggle="tab" href="#LogIn">Log In</a></li>
			<li><a data-toggle="tab" href="#SignUp">Sign Up</a></li>
		</ul>
		<div class="tab-content">

			<!-- Log In Tab -->
			<div id="LogIn" class="tab-pane fade in active borderTabLogin">
				<h1 class="title">Log in</h1>
				<div class="center">
					<form method="get"
						action="${pageContext.request.contextPath}/Controller/LogIn">

						<!-- User field -->
						<div class="col-md-1 formgroup">
							<i class="glyphicon glyphicon-user moveIconL"></i>
						</div>
						<div class="formgroup moveRightInp">
							<input type="text" required pattern="\w+"
								title="Please enter a valid user name" placeholder="User name.."
								name="username" size="25" />
						</div>

						<!-- Password field -->
						<div class="col-md-1 formgroup ">
							<i class="fa fa-lock pasIconL" aria-hidden="true"></i>
						</div>
						<div class="formgroup moveRightInp ">
							<input type="password" value="" placeholder="Password.." required
								name="password" size="25" />
						</div>

						<!-- Submit field -->
						<br /> <input type="submit" value="Log in" name="submit"
							class="center" />
					</form>
				</div>
			</div>

			<!-- Sing Up Tab -->
			<div id="SignUp" class="tab-pane fade borderTabSign">
				<div class="center">
					<h1 class="title">Sign up</h1>
					<form method="get"
						action="${pageContext.request.contextPath}/Controller/SignUp">
						<!-- User field -->
						<div class="col-md-1 formgroup">
							<i class="glyphicon glyphicon-user moveIconS"></i>
						</div>
						<div class="formgroup moveRightInp">
							<input type="text"
								title="The user name must contain only letters and numbers"
								required required pattern="\w+" placeholder="User name.."
								name="username" size="25" />
						</div>

						<!-- Email field -->
						<div class="col-md-1 formgroup">
							<i class="fa fa-envelope moveIconS" aria-hidden="true"></i>
						</div>
						<div class="formgroup moveRightInp">
							<input type="email" required value="" placeholder="Email.."
								name="email" size="25" />
						</div>

						<!-- Password field -->
						<div class="col-md-1 formgroup">
							<i class="fa fa-lock pasIconS" aria-hidden="true"></i>
						</div>
						<div class="formgroup moveRightInp">
							<input type="password"
								title="Password must contain at least 6 characters, including UPPER/lowercase and numbers"
								required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" required
								value="" placeholder="Password.." name="password" size="25"
								onchange=check() />

						</div>

						<!-- confirm Password field -->
						<div class="col-md-1 formgroup">
							<i class="fa fa-lock pasIconS" aria-hidden="true"></i>
						</div>
						<div class="formgroup moveRightInp">
							<input title="Please enter the same Password as above"
								type="password" required value=""
								placeholder="Confirm password.." name="confirm_password"
								size="25" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}"
								onchange="this.setCustomValidity(this.validity.patternMismatch ? this.title : '');" />
						</div>

						<!-- Submit field -->
						<br /> <input type="submit" value="Sign Up" name="submit"
							class="center" />
					</form>
					<script type="text/javascript">
						// polyfill for RegExp.escape
						if (!RegExp.escape) {
							RegExp.escape = function(s) {
								return String(s).replace(/[\\^$*+?.()|[\]{}]/g,
										'\\$&');
							};
						}

						var check = function() {
							this
									.setCustomValidity(this.validity.patternMismatch ? this.title
											: '');
							if (this.checkValidity())
								form.confirm_password.pattern = RegExp
										.escape(this.value);
						}
					</script>
				</div>
			</div>
		</div>
	</div>
</body>
</html>