<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	String lat = request.getParameter("lat");
	if (lat == null) lat = "0.0";
	String lnt = request.getParameter("lnt");
	if (lnt == null) lnt = "0.0";
%> 
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
		<form action="/wifiinfo" method="post">
	        LAT: <input type="text" id="lat" name="lat" value="<%=lat%>" /> , LNT: <input type="text" id="lnt" name="lnt" value="<%=lnt%>"/> <button id="myLocationBtn" type="button">내 위치 가져오기</button> <button type="submit">근처 WIPI 정보보기</button>
	    </form>
	</div> 
    <table>
    <thead>
	    <th>거리(Km)</th>
	    <th>관리번호</th>
	    <th>자치구</th>
	    <th>와이파이명</th>
	  	<th>도로명주소</th>
	  	<th>상세주소</th>
	  	<th>설치위치(층)</th>
	  	<th>설치유형</th>
	  	<th>설치기관</th>
	  	<th>서비스구분</th>
	  	<th>망종류</th>
	  	<th>설치년도</th>
	  	<th>실내외구분</th>
	  	<th>WIFI접속환경</th>
	  	<th>X좌표</th>
	  	<th>Y좌표</th>
	  	<th>작업일자</th>
    </thead>
    <tbody>
    <c:if test="${empty wifiInfoList}">
    <div> 위치 정보를 입력한 후에 조회해 주세요.</div>
    </c:if>
    <c:if test="${!empty wifiInfoList}">
        <c:forEach var="wifiInfo" items="${wifiInfoList}">
        <tr>
        	<td>${wifiInfo.distance}</td>
			<td>${wifiInfo.xSwifiMgrNo}</td>
			<td>${wifiInfo.xSwifiWrdofc}</td>
			<td>${wifiInfo.xSwifiMainNm}</td>
			<td>${wifiInfo.xSwifiAdres1}</td>
			<td>${wifiInfo.xSwifiAdres2}</td>
			<td>${wifiInfo.xSwifiInstlFloor}</td>
			<td>${wifiInfo.xSwifiInstlTy}</td>
			<td>${wifiInfo.xSwifiInstlMby}</td>
			<td>${wifiInfo.xSwifiSvcSe}</td>
			<td>${wifiInfo.xSwifiCmcwr}</td>
			<td>${wifiInfo.xSwifiCnstcYear}</td>
			<td>${wifiInfo.xSwifiInoutDoor}</td>
			<td>${wifiInfo.xSwifiRemars3}</td>
			<td>${wifiInfo.lat}</td>
			<td>${wifiInfo.lnt}</td>
			<td>${wifiInfo.workDttm}</td>
        </tr>
    	</c:forEach>
    </c:if>
    </tbody>
    </table>

</body>
<script type="text/javascript">
    
    function myLocationBtnClickEvent(){
        navigator.geolocation.getCurrentPosition(function(pos) {
            var latitude = pos.coords.latitude;
            var longitude = pos.coords.longitude;
       		
            var latElement = document.getElementById('lat');
            latElement.setAttribute('value', latitude);
            
            var lntElement = document.getElementById('lnt');
            lntElement.setAttribute('value', longitude);
        });
    }
    
    var myLocationBtn = document.getElementById('myLocationBtn');
    myLocationBtn.addEventListener('click', myLocationBtnClickEvent);
    
    </script>
</html>