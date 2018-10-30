<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section id='selection' class='fullscreen spacer-navbar'>
	<div class="container-fluid" >
		<div class="row">
			<div class="col-12 col-md-4 col-lg-3 spacer">
				<div class="ui raised card">
					<div class="image">
						<img src="../resources/images/user_turquoise.png">
					</div>
					<div class="content">
						<a class="header">Username</a>
						<div class="description">FirstName LastName</div>						
						<div class="meta">
							<span class="date">Joined in 2013</span>
						</div>
					</div>
					<div class="extra content">
						<a><i class="map marker icon"></i> Location</a>
					</div>
					<div class="ui bottom attached button">
						<a href="profile/edit"><i class="edit icon"></i> Edit</a>
					</div>				  
				</div>				
			</div>
			<div class="col-12 col-md-8 col-lg-9 spacer" id="viewport">
				<h2>My Books</h2>
				<div class="row">
					<%@ include file="books/book.jsp" %>
					<div class="col-6 col-md-4 col-lg-2">

					</div>
					<div class="col-6 col-md-4 col-lg-2">

					</div>
					<div class="col-6 col-md-4 col-lg-2">

					</div>
					<div class="col-6 col-md-4 col-lg-2">

					</div>
					<div class="col-6 col-md-4 col-lg-2">

					</div>
				</div>		
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="partials/footer.jsp" %>