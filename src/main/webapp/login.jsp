<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<div class="row no-gutters fullscreen">
	<div class="col-12 col-md-6 fullscreen-mobile d-flex flex-column justify-content-center dark-background">
		<div class="d-flex flex-column p-3 p-lg-5 white">
			<div class="d-flex flex-row justify-content-center align-items-center">
				<img class='logo' src="${pageContext.request.contextPath}/resources/images/logo-mini.png" alt="Book Exchange Logo"></img>
				<div><h2><strong>BOOK</strong> Exchange</h2></div>
			</div>
			<div class="error-message">${message}</div>
			<form class="form-signin" action="login" method="post">
				<div class="form-group">
					<input type="email" name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
				</div>
				<div class="form-group">
					<input type="password" name="password" class="form-control" id="password" placeholder="Password">
				</div>
				<button type="submit" class="btn btn-primary btn-gradient btn-block">Sign in</button>
			</form>
			<p class="text-center mt-5">
				Are you new to BookExchange? <a href="register">Register here</a>
			</p>
			<div class="ui horizontal divider white">Or</div>
			<button class="btn btn-block btn-login btn-google">Sign in with Google</button>
			<button class="btn btn-block btn-login btn-twitter">Sign in with Twitter</button>
			<button class="btn btn-block btn-login btn-facebook">Sign in with Facebook</button>
		</div>
	</div>
	<div class="col-12 col-md-6  book-background fullscreen-mobile d-flex flex-column justify-content-center">
		<div class="d-flex flex-column align-items-center p-3 p-lg-5">
			<h1 class='hero-item'>
				<i class="fas fa-search"></i> Search for books
			</h1>
			<h1 class='hero-item'>
				<i class="fas fa-users"></i> Exchange
			</h1>
			<h1 class='hero-item'>
				<i class="fas fa-book-reader"></i> Find events near you
			</h1>
		</div>
	</div>
</div>

<%@ include file="partials/footer.jsp" %>
<%@ include file="partials/footer-end.jsp" %>
