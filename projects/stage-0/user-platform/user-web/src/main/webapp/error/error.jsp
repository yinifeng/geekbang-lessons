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
		<h1><span style="color: red">错误原因</span></h1>
		<hr />
		<div>${errorMsg}</div><br />
		<a href="/index.jsp">回到首页</a>
	</div>
</body>