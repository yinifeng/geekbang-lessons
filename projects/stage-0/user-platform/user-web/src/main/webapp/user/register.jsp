<jsp:directive.include
		file="/WEB-INF/jsp/prelude/include-taglibs.jspf" />
<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>用户注册页面</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->
		<form action="/user/register" method="post">
			用户名:<input type="text" name="userName"/><br />
			用户密码:<input type="password" name="userPassword"/><br />
			用户邮箱:<input type="text" name="userEmail"/><br />
			用户电话:<input type="text" name="userPhoneNumber"/><br />
			<input type="submit" value="用户注册"/>
		</form>
	</div>
</body>