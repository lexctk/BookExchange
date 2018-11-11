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
		zoom: 10,
		center: new google.maps.LatLng(<c:out value='${userLat}'/>, <c:out value='${userLng}'/>),
	});
	
	var infoWin = new google.maps.InfoWindow();
	
	var markers = locations.map(function(location, i) {
		var contentString = 
			'<div id="content">' +
		        '<h1 id="firstHeading" class="firstHeading">' + titles[i] + '</h1>' +
		        '<div id="bodyContent d-flex flex-row align-items-center">'+
		        	'<img class="map-image" src="' + covers[i] + '">'+
		        	'<div class="map-user">Proof of concept book info: TODO: add user info</div>'
		        '</div>'+
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
</script>

<%@ include file="../partials/footer.jsp" %>

<script src="${pageContext.request.contextPath}/resources/scripts/markerclusterer.js"></script>
	
<!-- Google API -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjtXfwU4ouU7RnfoT7tXheh6wBVV8UvZA&callback=initMap" async defer></script>

<%@ include file="../partials/footer-end.jsp" %>
