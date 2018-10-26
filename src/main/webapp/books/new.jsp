<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section id='selection' class='fullscreen spacer-navbar'>
	<div class="container" >
		<div class="row spacer">
			<div class="col-xs-12 col-md-4 col-lg-3 d-flex justify-content-center">
				<div class="align-self-center"><i class="fas fa-book"></i></div>
			</div>
			<div class="col-xs-12 col-md-8 col-lg-9">
				<div class="ui equal width form">
					<div class="fields">
						<div class="field">
							<label>Title</label>
							<input name="title" type="text">
						</div>
						<div class="field">
							<label>Author(s)</label>
							<input name="authors" type="text" placeholder="Separated by commas">
						</div>
					</div>
					<div class="fields">
						<div class="field">
						<label>Description</label>
						<textarea name="description" rows="3"></textarea>
						</div>					
					</div>					
					<div class="fields">
						<div class="field">
							<label>Categories</label>
							<input name="categories" type="text" placeholder="Separated by commas">
						</div>					
						<div class="field">
							<label>Language</label>
							<input name="language" type="text">
						</div>
						<div class="field">
							<label>Industry Identifiers</label>
							<input name="identifiers" type="text" placeholder="ISBN, ISSN...">
						</div>
					</div>
				</div>
			</div>
		</div>
		<form id="searchAPI">
			<div class="ui fluid form spacer">
				<div class="field">
					<div class="ui icon input">
			  			<input type="text" name="searchquery" placeholder="Search...">
			  			<i class="search icon"></i>
					</div>
				</div>
			</div>
		</form>
		<div class="api-results" id="searchAPIResults">
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="../partials/footer.jsp" %>