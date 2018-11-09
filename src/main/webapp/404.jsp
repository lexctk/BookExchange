<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<div class="error404 d-flex flex-column justify-content-center align-items-center">
	<div class="error404">${message}</div>
	<c:choose>
		<c:when test="${origin == 'google'}">
			<img class="error404" src="${pageContext.request.contextPath}/resources/images/404-arrow-google.png">
		</c:when>
		<c:otherwise>
			<img class="error404 img-fluid" src="${pageContext.request.contextPath}/resources/images/404-arrow.png">
		</c:otherwise>
	</c:choose>
</div>