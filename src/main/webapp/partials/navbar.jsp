<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<header>
	<nav class="navbar navbar-expand-lg navbar-light bg-dark" >
		<a class="navbar-brand" href="#">
			<img class='logo' src="../resources/images/logo.png" alt="logo"></img>
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
					<a class="nav-link text-white" href="#">Home<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item active">
					<a class="nav-link text-white" href="#">Library<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item active">
					<a class="nav-link text-white" href="#">My Books<span class="sr-only">(current)</span></a>
				</li>
				
				<li class="nav-item"><a class="nav-link text-white" href="#">Events</a></li>
				
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Account </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="#">Signed in as ${username}</a> 
						<a class="dropdown-item" href="#">My Books</a>
						<div class="dropdown-divider"></div>
						<form action="${pageContext.request.contextPath}/logout" method="POST">
							<button type="submit" class="btn btn-link dropdown-item">Logout</button>
						</form>
					</div>
				</li>
			</ul>
		</div>
	</nav>
</header>