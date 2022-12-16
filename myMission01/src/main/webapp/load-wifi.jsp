<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	* {
	text-align: center;
	}
</style>
</head>
<body>
<h1><%=request.getAttribute("listTotalCount")%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
<a href="/">홈 으로 가기</a>
</body>
</html>