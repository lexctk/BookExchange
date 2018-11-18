<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
	<div class="container-fluid" >
		<div class="row">
			<div class="col-12 col-md-4 col-lg-3 spacer">
				<div class="ui raised card">
					<div class="image">
						<c:set var="avatar"><c:out value='${user.getAvatar()}'/></c:set>
						<c:choose>
							<c:when test="${not empty avatar}">
								<img class="userAvatar" src="${avatar}">
							</c:when>
							<c:otherwise>
								<img class="userAvatar" src="${pageContext.request.contextPath}/resources/images/user_turquoise.png">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="content text-center">
						<div class="header">${user.getUsername()}</div>
						<div class="description">${user.getFirstname()} ${user.getLastname()}</div>						
						<div class="meta">
							<span class="date">Joined in ${user.getRegisteredMonth()} ${user.getRegisteredYear()}</span>
						</div>
					</div>
					<div class="extra content text-center">
						<i class="map marker icon"></i>${user.getLocation().getLocality()}, ${user.getLocation().getCountry()}
					</div>
					<c:choose>
						<c:when test="${visitor}">
							<div class="ui bottom attached button">
								<a href="${pageContext.request.contextPath}/app/email?id=${user.getIdString()}"><i class="envelope open icon"></i> Email</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="ui bottom attached button">
								<a href="${pageContext.request.contextPath}/app/profile/edit"><i class="edit icon"></i> Edit</a>
							</div>	
						</c:otherwise>
					</c:choose>
			  
				</div>				
			</div>
			<div class="col-12 col-md-8 col-lg-9 spacer" id="viewport">
				<div class="hint spacer d-flex align-items-center justify-content-between flex-sm-row flex-column">
					<c:choose>
						<c:when test="${visitor}">
							<div class="p-2"><h2>${user.getUsername()}'s Books</h2></div>
						</c:when>
						<c:otherwise>
							<div class="p-2"><h2>My Books</h2></div>
							<div class="p-2"><a class="btn btn-light btn-lg btn-icon btn-gradient" role="button" href="${pageContext.request.contextPath}/app/books/newsearch">Add Book <i class="fas fa-plus"></i></a></div>
						</c:otherwise>
					</c:choose>
				</div>
				<c:choose>
					<c:when test="${visitor}">
						<div class="row list-visitor id-${user.getIdString()}" id="book-list">
						</div>
					</c:when>
					<c:otherwise>
						<div class="row list-user" id="book-list">
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="partials/footer.jsp" %>
<%@ include file="partials/footer-end.jsp" %>
