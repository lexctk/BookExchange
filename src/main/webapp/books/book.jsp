<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="col-6 col-md-4 col-lg-3 col-xl-2 spacer">
	<a href="${link}">
	<div class="ui card"> 
		<div class="image">
			<% if (request.getAttribute("thumbnail") != null && request.getAttribute("thumbnail").toString().length()>0 ) { %>
			<img src="${thumbnail}">
			<% } else { %>
			<img src="${pageContext.request.contextPath}/resources/images/new_book.png">
			<% } %>
		</div>
		<div class="content">
			<div class="header">${title}</div>
			<div class="meta"><span class="date">${authors}</span></div>
		</div>
		<% if (request.getAttribute("dateAdded") != null || request.getAttribute("location") != null  ) { %>
		<div class="extra content">	
			<span class="right floated">${dateAdded}</span>			
			<span><i class="map marker icon"></i>${location}</span>		
		</div>
		<% } %>
	</div>
	</a>
</div>