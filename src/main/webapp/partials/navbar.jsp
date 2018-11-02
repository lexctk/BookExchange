<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<header>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark" data-toggle="affix"><div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/app/dashboard">
			<img class='logo' src="${pageContext.request.contextPath}/resources/images/logo-mini.png" alt="logo"></img>
			<strong>BOOK</strong> Exchange
		</a>
		
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse " id="navbarSupportedContent" >
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active">
					<a class="nav-link text-white" href="${pageContext.request.contextPath}/app/dashboard">Home<span class="sr-only">(current)</span></a>
				</li>			
				<li class="nav-item active">
					<a class="nav-link text-white" href="${pageContext.request.contextPath}/app/books">Library<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item active">
					<a class="nav-link text-white" href="${pageContext.request.contextPath}/app/profile">My Books<span class="sr-only">(current)</span></a>
				</li>
				
				<li class="nav-item"><a class="nav-link text-white" href="${pageContext.request.contextPath}/app/events">Events</a></li>
				
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle text-white" href="" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Account </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/app/profile">Signed in as ${username}</a> 
						<a class="dropdown-item" href="${pageContext.request.contextPath}/app/profile">My Books</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/app/profile/edit">Edit profile</a>
						<div class="dropdown-divider"></div>
						<form action="${pageContext.request.contextPath}/logout" method="POST">
							<button type="submit" class="btn btn-link dropdown-item">Logout</button>
						</form>
					</div>
				</li>
			</ul>
		</div>
	</div></nav>
</header>