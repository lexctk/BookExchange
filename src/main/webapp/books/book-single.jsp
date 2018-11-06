<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
	<div class="container-fluid" >
		<div class="row">
			<div class="col-12 col-md-4 col-lg-3 spacer">
				<div class="image">
					<c:set var="thumbnail"><c:out value='${book.getThumbnail()}'/></c:set>
					<c:choose>
						<c:when test="${not empty thumbnail}">
							<img src="${thumbnail}">
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/resources/images/new_book.png">
						</c:otherwise>
					</c:choose>
				</div>			
			</div>
			<div class="col-12 col-md-8 col-lg-9 spacer" id="viewport">
				<h2><c:out value='${book.getTitle()}'/></h2>
				<c:out value='${book.getAllAuthors()}'/>
				<c:out value='${book.getDescription()}'/>
				<c:out value='${book.getDescription()}'/>
				<c:out value='${book.getAllCategories()}'/>
				<c:out value='${book.getLanguage()}'/>
				<c:out value='${book.getAllIndustryIdentifiers()}'/>
			</div>
		</div>
		<c:choose>
			<c:when test="${not empty users}">
				<c:forEach var="user" items="${users}">
					<c:out value='${user.getUsername()}'/>
				</c:forEach>
			</c:when>
		</c:choose>		
	</div>
</section>

<%@ include file="../partials/footer.jsp" %>