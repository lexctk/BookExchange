<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<c:forEach var="book" items="${books}">
	<div class="col-6 col-md-4 col-lg-3 col-xl-2 spacer filtr-item" data-sort="value" data-category="<c:out value='${book.getAllCategories()}'/>, <c:out value='${book.getLanguageName()}'/>">
		<a href="${pageContext.request.contextPath}${linkBase}${link}?id=<c:out value='${book.getId()}'/>">
		<div class="ui card"> 
			<div class="image">
				<c:set var="thumbnail"><c:out value='${book.getThumbnail()}'/></c:set>
				<c:choose>
					<c:when test="${not empty thumbnail}">
						<img class="book-thumbnail" src="${thumbnail}">
					</c:when>
					<c:otherwise>
						<img class="book-thumbnail" src="${pageContext.request.contextPath}/resources/images/new_book.png">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="content d-flex flex-column justify-content-end">
				<div class="header"><c:out value='${book.getTitle()}'/></div>
				<div class="meta"><span class="date"><c:out value='${book.getAllAuthors()}'/></span></div>
			</div>
			<c:if test="${not empty dateAdded}">
			<div class="extra content">	
				<span class="right floated">${dateAdded}</span>			
				<span><i class="map marker icon"></i>${location}</span>		
			</div>
			</c:if>
		</div>
		</a>
	</div>
</c:forEach>