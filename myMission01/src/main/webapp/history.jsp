<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
    	table, th, td {
		  border: 1px solid;
		}
    </style>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	
	<div>
		<a href="/">홈</a>|<a href="/history">위치 히스토리 목록</a>|<a href="/init">Open API 정보 가져오기</a> 
	</div> 
    <table>
    <thead>
	    <th>ID</th>
	    <th>X좌표</th>
	    <th>Y좌표</th>
	    <th>조회일자</th>
	  	<th>비고</th>
    </thead>
    <tbody>
    <c:if test="${empty historyList}">
    <tr> <td colspan="17" style="text-align:center;"> 검색 이력이 없습니다. </td></tr>
    </c:if>
    <c:if test="${!empty historyList}">
        <c:forEach var="history" items="${historyList}">
        <tr>
        	<td>${history.id}</td>
			<td>${history.lat}</td>
			<td>${history.lnt}</td>
			<td>${history.registDt}</td>
			<td><button onclick="location.href='/history/delete?id=${history.id}'">삭제</button></td>
        </tr>
    	</c:forEach>
    </c:if>
    </tbody>
    </table>

</body>
</html>