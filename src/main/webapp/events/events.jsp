<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
	<div class="container spacer"><div class="row">
		<div class="col-12">
			<c:forEach var="event" items="${events}">
				<div class="ui raised segment mt-4"><div class="row">
					<div class="col-12 col-md-4">
						<div class="image">
							<c:set var="image"><c:out value='${event.getImage()}'/></c:set>
							<c:choose>
								<c:when test="${not empty image}">
									<img class="img-fluid" src="${image}">
								</c:when>
								<c:otherwise>
									<img class="img-fluid" src="${pageContext.request.contextPath}/resources/images/event.png">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="ui list">
							<div class="item mt-3">
								<i class="fas fa-map-marker icon"></i>
								<div class="content">
									<strong>${event.getPlacename()}</strong><br>
									${event.getAddress()}
								</div>
							</div>
							<div class="item my-3">
								<i class="far fa-calendar-alt icon"></i>
								<div class="content">
									${event.getDate_start()} - ${event.getDate_end()}
								</div>
							</div>	
							<div class="item my-3">
								<i class="fas fa-euro-sign icon"></i>
								<div class="content">
									${event.getPricing_info()}
								</div>
							</div>													
						</div>						
					</div>
					<div class="col-12 col-md-8">
						<h2>
							<a class="h2" href="${event.getLink()}"><c:out value='${event.getTitle()}'/></a><br>
							<small class="text-muted">${event.getDescription()}</small>
						</h2>
						<p>${event.getFree_text()}</p>
						<form class="form" id="new_event" accept-charset="UTF-8" action="${pageContext.request.contextPath}/app/events" method="POST">
							<input name="id" id="id" class="hidden" type="text" value="${event.getUid()}">
							<button class="btn btn-light btn-lg btn-gradient btn-noicon float-right" type="submit">I'm going</button>
						</form>
					</div>
				</div></div>
			</c:forEach>
		</div>
	</div></div>
</section>

<%@ include file="../partials/footer.jsp" %>
<%@ include file="../partials/footer-end.jsp" %>