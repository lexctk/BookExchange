<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar'>
	<div class="container-fluid" >
		<div class="row">
			<div class="col-12 col-md-4 col-lg-3 spacer">
				<div class="ui raised card">
					<div class="image">
						<img src="../resources/images/user_turquoise.png">
					</div>
					<div class="content">
						<a class="header">${user.getUsername()}</a>
						<div class="description">${user.getFirstname()} ${user.getLastname()}</div>						
						<div class="meta">
							<span class="date">Joined in ${user.getRegisteredMonth()} ${user.getRegisteredYear()}</span>
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
				<div class="hint spacer d-flex align-items-center justify-content-between flex-sm-row flex-column">
					<div class="p-2"><h2>My Books</h2></div>
					<div class="p-2"><a class="btn btn-light btn-lg btn-icon" role="button" href="${pageContext.request.contextPath}/app/books/newsearch">Add Book <i class="fas fa-plus"></i></a></div>
				</div>
				<div class="row book-list list-user" id="book-list">
				</div>		
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="partials/footer.jsp" %>