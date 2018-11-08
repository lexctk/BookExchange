<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../partials/header.jsp" %>

<%@ include file="../partials/navbar.jsp" %>

<section id='selection' class='fullscreen spacer-navbar'>
	<div class="container" >
		<div class="hint spacer d-flex align-items-center justify-content-between flex-sm-row flex-column">
			<div class="p-2"><h2>Step 1: Search for your book </h2></div>
			<div class="p-2"><a class="btn btn-light btn-gradient btn-lg btn-icon" role="button" href="${pageContext.request.contextPath}/app/books/new">Skip <i class="fas fa-chevron-right"></i></a></div>
		</div>
		<form id="searchAPI">
			<div class="ui fluid form spacer">
				<div class="field">
					<div class="ui icon input">
			  			<input type="text" name="query" placeholder="Search via Google Books...">
			  			<i class="search icon"></i>
					</div>
				</div>
			</div>
		</form>
		<div class="api-results spacer" id="searchAPIResults">
			<div class="ui list hints">
				<div class="item"><i class="lightbulb outline icon"></i>To search for an exact phrase, enclose the search in quotation marks: "exact phrase"</div>
				<div class="item"><i class="lightbulb outline icon"></i>To exclude entries that match a given term, use the form -term</div>
				<div class="item"><i class="lightbulb outline icon"></i>The search terms are case-insensitive</div>
				<div class="item"><i class="lightbulb outline icon"></i>Use keyword intitle: to search in titles only</div>
				<div class="item"><i class="lightbulb outline icon"></i>Use keyword inauthor: to search in authors only</div>
				<div class="item"><i class="lightbulb outline icon"></i>Use keyword isbn: to search by ISBN only</div>
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="../partials/footer.jsp" %>