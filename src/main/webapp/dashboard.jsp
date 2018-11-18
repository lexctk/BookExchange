<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section class='hero fullscreen'>
	<div class='parallax' style='background-image: url("../resources/images/parallax.jpg");'></div>
	<div class='hero-content'>
		<h1 class='hero-item'>WELCOME TO BOOK EXCHANGE</h1>
		<p class='hero-item'>Meet your next favorite book. Attend awesome events.</p>
		<h1 class='hero-item'><a class='white' href='#selection'><i class="fa fa-chevron-down" aria-hidden="true"></i></a></h1>
	</div>
</section>

<section id='selection' class='fullscreen spacer'>
	<div class="container-fluid" >
		<div class="row">
			<div class="col-xs-12 col-md-8 col-lg-9">
				<h2>Latest Books</h2>
				<div class="row list-recent" id="book-list">
				</div>		
			</div>
			<div class="col-xs-12 col-md-4 col-lg-3">
				<h2>Upcoming Events</h2>
				<div class="spacer-lg"></div>
				<c:forEach var="event" items="${events}">
					<c:set var="attending" value="false" />
					<div class="ui raised segment mt-4"><div class="row">
						<div class="col-12">
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
								<div class="item my-3">
									<i class="far fa-calendar-alt icon"></i>
									<div class="content">
										${event.getDate_start()} - ${event.getDate_end()}
									</div>
								</div>													
							</div>						
						</div>
						<div class="col-12">
							<h4>
								<a class="h4" href="${event.getLink()}"><c:out value='${event.getTitle()}'/></a>
							</h4>
						</div>
					</div></div>
				</c:forEach>
			</div>
		</div>
	</div>
</section>

<%@ include file="partials/footer.jsp" %>
<%@ include file="partials/footer-end.jsp" %>
