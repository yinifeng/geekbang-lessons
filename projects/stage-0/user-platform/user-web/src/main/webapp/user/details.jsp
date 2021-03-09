<jsp:directive.include
		file="/WEB-INF/jsp/prelude/include-taglibs.jspf" />
<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>用户详情页面</title>
</head>
<body>
	<div align="center" class="container-lg">
		<!-- Content here -->
		<h1>用户详情</h1>
		<hr />
		用户id：
		<div>${user.id}</div><br />
		用户名:
		<div>${user.name}</div><br />
		用户邮箱:
		<div>${user.email}</div><br />
		用户电话:
		<div>${user.phoneNumber}</div>
		<a href="/index.jsp">回到首页</a>
	</div>
</body>