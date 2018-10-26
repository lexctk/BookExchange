<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="ui link card">
	<div class="image">
		<img src="${param.thumbnail}">
	</div>
	<div class="content">
		<div class="header">${param.title}</div>
		<div class="meta"><span class="date">${param.author}</span></div>
	</div>							
	<div class="extra content">
		<span class="right floated">${param.dateAdded}</span> 
		<span><i class="map marker icon"></i>${param.location}</span>
	</div>
</div>