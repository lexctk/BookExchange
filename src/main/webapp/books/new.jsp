<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>
<%
// TODO: towards end of project
// Used for debugging
// Skip this form completely if found book via API, extra step is pointless! 
// Needs input for book trade type/location
%>
<section id='selection' class='fullscreen spacer-navbar'>
	<div class="container">
		<div class="hint spacer"><h2>Step 2: Confirm</h2></div>
		<div class="row spacer">
			<div class="col-12 col-md-4 col-lg-3 d-flex justify-content-center">
			<div class="align-self-center">
				<% if (request.getAttribute("thumbnail") != null && request.getAttribute("thumbnail").toString().length()>0) { %>
					<img src="${thumbnail}">
				<% } else { %>
					<i class="fas fa-book"></i>
				<% } %>
			</div>
			</div>
			<div class="col-12 col-md-8 col-lg-9">
				<form class="ui equal width form" id="new_book" accept-charset="UTF-8" action="${pageContext.request.contextPath}/app/books" method="POST">
					<div class="fields">
						<div class="field">
							<label>Title</label>
							<input name="title" type="text" placeholder="Title" value="${title}">
						</div>
						<div class="field">
							<label>Author(s)</label>
							<input name="authors" type="text" placeholder="Authors, separated by commas" value="${authors}">
						</div>
					</div>
					<div class="fields">
						<div class="field">
						<label>Description</label>
						<textarea name="description" rows="3">${description}</textarea>
						</div>					
					</div>					
					<div class="fields">
						<div class="field">
							<label>Categories</label>
							<input name="categories" type="text" placeholder="Categories, separated by commas" value="${categories}">
						</div>					
						<div class="field">
							<label>Language</label>
							<input name="language" type="text" placeholder="Language" value="${language}">
						</div>
						<div class="field">
							<label>Industry Identifiers</label>
							<% if (request.getAttribute("identifiers") != null && request.getAttribute("identifiers").toString().length()>0) { %>
								<input name="identifiers" type="text" placeholder="ISBN, ISSN..." value="${identifiers}">
							<% } else { %>
								<input name="identifiers" type="text" placeholder="ISBN, ISSN...">
							<% } %>
						</div>
					</div>
					<input name="thumbnail" id="thumbnail" class="hidden" type="url" value="${thumbnail}">
					<input name="id" id="id" class="hidden" type="text" value="${id}">
					<button class="btn btn-light btn-lg btn-gradient btn-noicon" type="submit">Add</button>
				</form>
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="../partials/footer.jsp" %>