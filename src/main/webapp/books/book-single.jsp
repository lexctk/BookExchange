<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="../partials/header.jsp" %>
<%@ include file="../partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
	<div class="container-fluid" >
		<div class="row spacer">
			<div class="col-12 col-md-8 offset-md-2 spacer"><div class="ui tall stacked segment">
				<div class="ui divided items">
					<div class="ui item">
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
						
						<div class="content">
							<div class="header"><h2><c:out value='${book.getTitle()}'/></h2></div>
							<div class="meta"><c:out value='${book.getAllAuthors()}'/></div>
							<div class="description spacer"><p><c:out value='${book.getDescription()}'/></p></div>
	
							<div class="extra spacer">
								<c:forTokens items="${book.getAllCategories()}" delims="," var="category">
									<div class="ui basic label"><c:out value="${category}"/></div>
								</c:forTokens>
								<div class="ui basic label">
									<span class="flag-icon flag-icon-<c:out value='${book.getLanguage()}'/>"></span> <c:out value='${book.getLanguageName()}'/>
								</div>
								<c:forTokens items="${book.getAllIndustryIdentifiers()}" delims=", " var="identifier">
									<div class="ui basic label"><c:out value="${identifier}"/></div>
								</c:forTokens>
							</div>
						</div>
	
					</div>
				</div>		  
			</div></div>
		</div>
		<div class="row spacer">
			<div class="col-12 col-md-8 offset-md-2 spacer">
				<div class="ui cards">
					<c:forEach var="user" items="${users}">
						<div class="ui card">
							<div class="content">
								<div class="header"><c:out value='${user.getUsername()}'/></div>
								<div class="meta">
									<span class="right floated time">2 days ago</span>
									<span class="category">Location</span>
								</div>
							</div>
							<div class="extra content">
								<div class="center aligned author">
									<img class="ui avatar image" src="../resources/images/user_turquoise.png"> ${user.getFirstname()}
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>	
	</div>
</section>

<%@ include file="../partials/footer.jsp" %>