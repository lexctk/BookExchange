<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String link = request.getParameter("link");
if (link != null && link.length()>0) { 
%>
<a href="${param.link}"> <!-- Eclipse warning is obsolete, this is valid in HTML5 -->
<%
}
%>
	<div class="ui card">
		<div class="image">
			<img src="${param.thumbnail}">
		</div>
		<div class="content">
			<div class="header">${param.title}</div>
			<% 
			String author = request.getParameter("author");
			if (author != null && author.length()>0) { 
			%>
				<div class="meta"><span class="date">${param.author}</span></div>
			<%
			}
			%>
		</div>
		<div class="extra content">
			<% 
			String dateAdded = request.getParameter("dateAdded");
			if (dateAdded != null && dateAdded.length()>0) { 
			%>		
				<span class="right floated">${param.dateAdded}</span>
			<%
			}
			String location = request.getParameter("location");
			if (location != null && location.length()>0) { 
			%>				
				<span><i class="map marker icon"></i>${param.location}</span>
			<%
			}
			%>			
		</div>
	</div>
<% 
if (link != null && link.length()>0) { 
%>
</a>
<%
}
%>