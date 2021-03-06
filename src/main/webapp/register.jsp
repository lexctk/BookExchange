<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<div class="row no-gutters fullscreen">
	<div class="col-12 col-md-6 fullscreen-mobile d-flex flex-column justify-content-center dark-background">
		<div class="d-flex flex-column p-3 p-lg-5 white">
			<a class="white" href="${pageContext.request.contextPath}/login"><i class="fas fa-long-arrow-alt-left"></i> Back to login</a>
			
			<div class="d-flex flex-row justify-content-center align-items-center">
				<img class='logo' src="${pageContext.request.contextPath}/resources/images/logo-mini.png" alt="Book Exchange Logo"></img>
				<div><h2><strong>BOOK</strong> Exchange</h2></div>
			</div>
			
			<form class="form-signin ui form spacer" action="register" method="post" enctype="multipart/form-data">
			
				<div class="field">
					<input type=text name="username" class="form-control" id="username" placeholder="Username">
				</div>
				<div class="two fields">				
					<div class="field">
						<input type="email" name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Email"> 
					</div>
					
					<div class="field">
						<input type="password" name="password" class="form-control" id="password" placeholder="Password">
					</div>
				</div>
								
				<div class="two fields">
					<div class="field">
						<input type=text name="firstname" class="form-control" id="firstname" placeholder="First Name (optional)">
					</div>
					<div class="field">
						<input type=text name="lastname" class="form-control" id="lastname" placeholder="Last Name (optional)">
					</div>
				</div>

				<div class="custom-file">
					<input type="file" class="custom-file-input" id="userAvatar" name="userAvatar">
					<label class="custom-file-label" for="userAvatar">Upload photo (optional)</label>
				</div>
				
				<div class="d-flex justify-content-between flex-column flex-md-row align-items-center" id="locationField">
					<input class="mt-3" id="autocomplete" placeholder="Enter address (optional)" type="text"></input>
					
					<div class="white mt-3 mx-3">OR</div>
					
					<button type="button" class="btn btn-primary btn-light mt-3" onclick="geolocateUser()"><i class="fas fa-location-arrow"></i> Share location</button>			
				</div>

				<table class="address-table spacer hidden" id="address">
					<tr>
						<td class="label">City</td>
						<td class="wideField" colspan="3"><input name="locality" class="field" id="locality"></input></td>
					</tr>
					<tr>
						<td class="label">Country</td>
						<td class="wideField" colspan="3"><input name="country" class="field" id="country"></input></td>
					</tr>
					<tr>
						<td class="label">Latitude</td>
						<td class="slimField"><input class="field" name="lat" id="lat"></input></td>
						<td class="label">Longitude</td>
						<td class="wideField"><input class="field" name="lng" id="lng"></input></td>
					</tr>					
				</table>
				
				<button id="registerSubmit" type="submit" class="ui button primary btn-gradient btn-block spacer">Register</button>
				
				<div class="ui error message"></div>
			</form>
			
			<div class="ui horizontal divider white">Or</div>
			<button class="btn btn-block btn-login btn-google">Register with Google</button>
			<button class="btn btn-block btn-login btn-twitter">Register with Twitter</button>
			<button class="btn btn-block btn-login btn-facebook">Register with Facebook</button>			
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

<!-- Include form validation -->
<script src="${pageContext.request.contextPath}/resources/scripts/register.js"></script>
		
<!-- Google API -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjtXfwU4ouU7RnfoT7tXheh6wBVV8UvZA&libraries=places&callback=initAutocomplete" async defer></script>

<%@ include file="partials/footer-end.jsp" %>
