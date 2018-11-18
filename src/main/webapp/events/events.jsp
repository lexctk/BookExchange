<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
	<div class="container spacer"><div class="row">
		<div class="col-12">
			<c:forEach var="event" items="${events}">
				<c:set var="attending" value="false" />
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
						
						<c:set var="users" value="${event.getUsers()}"/>
						<c:choose>
							<c:when test="${not empty users}">
								<h3>Who's going?</h3>
								<div class="ui tiny horizontal list">
									<c:forEach var="user" items="${users}">
										<c:set var="guestId"><c:out value='${user.getIdString()}'/></c:set>
										<c:set var="currentId"><c:out value='${currentUser.getIdString()}'/></c:set>
										<c:choose>
											<c:when test="${guestId eq currentId}">
												<c:set var="attending" value="true" />
											</c:when>
										</c:choose>
										
										<div class="item">
											<c:set var="avatar"><c:out value='${user.getAvatar()}'/></c:set>
											<c:choose>
												<c:when test="${not empty avatar}">
													<img class="ui avatar image" src="${avatar}">
												</c:when>
												<c:otherwise>
													<img class="ui avatar image" src="${pageContext.request.contextPath}/resources/images/user_turquoise.png">
												</c:otherwise>
											</c:choose>
											<div class="content">
												<a href="${pageContext.request.contextPath}/app/profile?id=${user.getIdString()}">
													<div class="header">${user.getUsername()}</div>
												</a>
											</div>
										</div>
																		
									</c:forEach>
								</div>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${attending}">
								<button class="btn btn-light btn-lg btn-noicon btn-green float-right" type="submit" disabled><i class="fas fa-check"></i> I'm going!</button>	
							</c:when>
							<c:otherwise>
								<form class="form" id="new_event" accept-charset="UTF-8" action="${pageContext.request.contextPath}/app/events" method="POST">
									<input name="eventId" id="eventId" class="hidden" type="text" value="${event.getUid()}">
									<button class="btn btn-light btn-lg btn-gradient btn-noicon float-right" type="submit">Attend</button>
								</form>							
							</c:otherwise>
						</c:choose>
					</div>
				</div></div>
			</c:forEach>
		</div>
	</div></div>
</section>

<%@ include file="../partials/footer.jsp" %>
<%@ include file="../partials/footer-end.jsp" %>