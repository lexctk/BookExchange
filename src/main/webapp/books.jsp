<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section class='hero fullscreen'>
	<div class='parallax' style='background-image: url("../resources/images/parallax.jpg");'></div>
	<div class='hero-content'>
		<h1 class='hero-item'>WELCOME TO BOOK EXCHANGE</h1>
		<p class='hero-item'>Meet your next favorite book. Attend awesome events.</p>
		<div class="hero-item hero-search ui large icon input">
		  <input type="text" placeholder="Search...">
		  <i class="search icon"></i>
		</div>
		<h1 class='hero-item'><a class='white' href='#selection'><i class="fa fa-chevron-down" aria-hidden="true"></i></a></h1>
	</div>
</section>

<section id='selection' class='fullscreen'>
	<div class="container-fluid" >
		<div class="row">
			<div class="col-xs-12 col-md-8 col-lg-10">
				<h2>Latest Books</h2>
				<div class="row">
					<div class="col-6 col-md-4 col-lg-2">
						<div class="ui link card">
							<div class="image">
								<img src="https://books.google.com/books/content?id=W05ZDwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api">
							</div>
							<div class="extra content">
								<span class="right floated"> 2 days ago </span> 
								<span><i class="map marker icon"></i> Paris</span>
							</div>
						</div>
					</div>
					<div class="col-6 col-md-4 col-lg-2">
						<div class="ui link card">
							<div class="image">
								<img src="https://books.google.com/books/content?id=GBl6MWssicEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api">
							</div>
							<div class="extra content">
								<span class="right floated"> 2 days ago </span> 
								<span><i class="map marker icon"></i> Paris</span>
							</div>
						</div>
					</div>
					<div class="col-6 col-md-4 col-lg-2">
						<div class="ui link card">
							<div class="image">
								<img src="https://books.google.com/books/content?id=sgNDrcG4msgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api">
							</div>
							<div class="extra content">
								<span class="right floated"> 2 days ago </span> 
								<span><i class="map marker icon"></i> Paris</span>
							</div>
						</div>
					</div>
					<div class="col-6 col-md-4 col-lg-2">
						<div class="ui link card">
							<div class="image">
								<img src="https://books.google.com/books/content?id=UZSQAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api">
							</div>
							<div class="extra content">
								<span class="right floated"> 2 days ago </span> 
								<span><i class="map marker icon"></i> Paris</span>
							</div>
						</div>
					</div>
					<div class="col-6 col-md-4 col-lg-2">
						<div class="ui link card">
							<div class="image">
								<img src="https://books.google.com/books/content?id=GBl6MWssicEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api">
							</div>
							<div class="extra content">
								<span class="right floated"> 2 days ago </span> 
								<span><i class="map marker icon"></i> Paris</span>
							</div>
						</div>
					</div>
					<div class="col-6 col-md-4 col-lg-2">
						<div class="ui link card">
							<div class="image">
								<img src="https://books.google.com/books/content?id=GBl6MWssicEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api">
							</div>
							<div class="extra content">
								<span class="right floated"> 2 days ago </span> 
								<span><i class="map marker icon"></i> Paris</span>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="col-xs-12 col-md-4 col-lg-2">
				<h2>Events this week</h2>
			</div>
		</div>
	</div>
</section>

<div class="spacer"></div>

<%@ include file="partials/footer.jsp" %>