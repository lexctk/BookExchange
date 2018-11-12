<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
	<div class="container spacer" >
		<c:forEach var="event" items="${events}">
			<div class="ui raised segment"><div class="row">
				<div class="col-4">
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
				</div>
				<div class="col-8">
					<h2><c:out value='${event.getTitle()}'/></h2>
					${event.getLang()} <br>
					${event.getTitle()} <br>
					${event.getUid()} <br>
					${event.getPlacename()} <br>
					${event.getPricing_info()} <br>
					${event.getDate_start()} <br>
					${event.getDepartment()} <br>
					${event.getCity()} <br>
					${event.getLink()} <br>
					${event.getFree_text()} <br>
					${event.getAddress()} <br>
					${event.getRegion()} <br>
					${event.getDate_end()} <br>
					${event.getDescription()} <br>
					<c:out value='${event.getLocation().getLat()}'/>
					<c:out value='${event.getLocation().getLng()}'/>
				</div>
			</div></div>
		</c:forEach>
	</div>
</section>

<%@ include file="../partials/footer.jsp" %>
<%@ include file="../partials/footer-end.jsp" %>