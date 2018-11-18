<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section class="fullscreen spacer-navbar spacer-footer">
	<div class="container spacer" >
		<div id="map"></div>
	</div>
</section>

<script>
function initMap() {
	
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 12,
		center: new google.maps.LatLng(<c:out value='${userLat}'/>, <c:out value='${userLng}'/>),
	});
	
	var infoWin = new google.maps.InfoWindow();
	
	var markers = locations.map(function(location, i) {
		var contentString = 
			'<div id="content" class="d-flex flex-row align-items-top">' +
				'<img class="map-image mr-2" src="' + covers[i] + '">' +
		        '<div class="d-flex flex-column">' +
		        	'<h3 class="m-0 ml-3">' + titles[i] + '</h3>' +
		        	'<p class="m-0 ml-3">' + authors[i] + '</p>' + 		        	
		        	'<div class="map-user ml-2 p-2">' +
						'<div class="ui card">' +
							'<div class="content d-flex flex-row align-items-center">' +
								'<img class="ui avatar image" src="' + avatars[i] + '">' +
								'<div class="header">' + usernames[i] + '</div>' +
							'</div>' +
							'<div class="extra content">' +
								'<div class="ui two buttons">' + 
									'<a class="ui basic button" href="${pageContext.request.contextPath}/app/email?id=' + userIds[i] + '&bookId=' + bookIds[i] + '"><i class="fas fa-envelope-open"></i> Email</a>' +
									'<a class="ui basic button" href="${pageContext.request.contextPath}/app/profile?id=' + userIds[i] + '"><i class="fas fa-external-link-alt"></i> Profile</a>' +
								'</div>' +
							'</div>' +
						'</div>' +
		        	'</div>' +
		        '</div>' +
	        '</div>';		
		var marker = new google.maps.Marker({
			position: location
		});
		google.maps.event.addListener(marker, 'click', function(evt) {
		      infoWin.setContent(contentString);
		      infoWin.open(map, marker);
		});
		return marker;
	});
	var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: '${pageContext.request.contextPath}/resources/images/map/m', gridSize: 50, minimumClusterSize: 3});
	
}
var locations = [];
<c:forEach var="location" items="${locations}">
	<c:set var="lat"><c:out value='${location.getLat()}'/></c:set>
	<c:set var="lng"><c:out value='${location.getLng()}'/></c:set>
	locations.push({lat: ${lat}, lng: ${lng}});
</c:forEach>
var titles = [];
<c:forEach var="title" items="${titles}">
	titles.push("${title}");
</c:forEach>
var covers = [];
<c:forEach var="cover" items="${covers}">
	covers.push("${cover}");
</c:forEach>
var authors = [];
<c:forEach var="author" items="${authors}">
	authors.push("${author}");
</c:forEach>
var bookIds = [];
<c:forEach var="bookId" items="${bookIds}">
	bookIds.push("${bookId}");
</c:forEach>
var usernames = [];
var avatars = [];
var userIds = [];
<c:forEach var="owner" items="${owners}">
	<c:set var="username"><c:out value='${owner.getUsername()}'/></c:set>
	usernames.push("${username}");
	<c:set var="id"><c:out value='${owner.getIdString()}'/></c:set>
	userIds.push("${id}");
	<c:set var="avatar"><c:out value='${owner.getAvatar()}'/></c:set>
	<c:choose>
		<c:when test="${not empty avatar}">
			avatars.push("${avatar}");
		</c:when>
		<c:otherwise>
			avatars.push("${pageContext.request.contextPath}/resources/images/user_turquoise.png");
		</c:otherwise>
	</c:choose>	
</c:forEach>
</script>

<%@ include file="../partials/footer.jsp" %>

<script src="${pageContext.request.contextPath}/resources/scripts/markerclusterer.js"></script>
	
<!-- Google API -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjtXfwU4ouU7RnfoT7tXheh6wBVV8UvZA&callback=initMap" async defer></script>

<%@ include file="../partials/footer-end.jsp" %>
