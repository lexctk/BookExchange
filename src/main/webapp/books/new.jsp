<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>
<%
//TODO: form inputs should be disabled if values are already filled out from API
//TODO: needs input for book trade type/location? 
%>
<section id='selection' class='fullscreen spacer-navbar'>
	<div class="container">
		<div class="hint spacer"><h2>Step 2: Confirm</h2></div>
		<div class="row spacer">
			<div class="col-12 col-md-4 col-lg-3 d-flex justify-content-center">
			<div class="align-self-center">
				<label for="userThumbnail">
					<% if (request.getAttribute("thumbnail") != null && request.getAttribute("thumbnail").toString().length()>0) { %>
						<img src="${thumbnail}">
					<% } else { %>
						<i class="fas fa-book"></i>
					<% } %>
				</label>
				<input name="userThumbnail" id="userThumbnail" class="hidden" type="file" form="new_book">
			</div>
			</div>
			<div class="col-12 col-md-8 col-lg-9">
				<form class="ui equal width form" id="new_book" action="${pageContext.request.contextPath}/app/books" method="POST">
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
					<input name="thumbnail" id="thumbnail" class="hidden" type="text" value="${thumbnail}">
					<button class="btn btn-light btn-lg btn-skip" type="submit">Add</button>
				</form>
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="../partials/footer.jsp" %>