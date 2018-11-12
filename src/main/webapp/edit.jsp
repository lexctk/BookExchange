<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
<div class="container spacer"><div class="row">
	<div class="col-12 d-flex flex-column justify-content-center">
		<div class="d-flex flex-column p-3 p-lg-5">
			
			<form class="form-edit ui form spacer" action="edit" method="post" enctype="multipart/form-data">
				<div class="row">
					<div class="col-4">
						<div class="image">
							<c:set var="avatar"><c:out value='${user.getAvatar()}'/></c:set>
							<c:choose>
								<c:when test="${not empty avatar}">
									<img id="userAvatarImg" class="userAvatar" src="${avatar}">
								</c:when>
								<c:otherwise>
									<img id="userAvatarImg" class="userAvatar" src="${pageContext.request.contextPath}/resources/images/user_turquoise.png">
								</c:otherwise>
							</c:choose>
						</div>					
					</div>
					<div class="col-8">
						<div class="field">
							<input type=text name="username" class="form-control" id="username" placeholder="${user.getUsername()}" value="${user.getUsername()}">
						</div>
						<div class="two fields">				
							<div class="field">
								<input type="email" name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="${user.getEmail()}" value="${user.getEmail()}"> 
							</div>
							
							<div class="field">
								<input type="password" name="password" class="form-control" id="password" placeholder="Password">
							</div>
						</div>
									
						<div class="two fields">
							<div class="field">
								<input type=text name="firstname" class="form-control" id="firstname" placeholder="${user.getFirstname()}" value="${user.getFirstname()}">
							</div>
							<div class="field">
								<input type=text name="lastname" class="form-control" id="lastname" placeholder="${user.getLastname()}" value="${user.getLastname()}">
							</div>
						</div>
	
						<div class="custom-file">
							<input type="file" class="custom-file-input" id="userAvatar" name="userAvatar">
							<label class="custom-file-label" for="userAvatar">Change profile photo</label>
						</div>
						<div class="d-flex justify-content-between flex-column flex-md-row align-items-center" id="locationField">
							<input class="mt-3" id="autocomplete" placeholder="${user.getLocation().getLocality()}, ${user.getLocation().getCountry()}" type="text"></input>
							
							<div class="mt-3 mx-3">OR</div>
							
							<button type="button" class="btn btn-primary btn-light mt-3" onclick="geolocateUser()"><i class="fas fa-location-arrow"></i> Share location</button>			
						</div>
	
						<table class="address-table mt-3 table table-borderless hidden" id="address">
							<tr>
								<td class="label">City</td>
								<td class="wideField" colspan="3"><input name="locality" class="field" id="locality" placeholder="${user.getLocation().getLocality()}"></input></td>
							</tr>
							<tr>
								<td class="label">Country</td>
								<td class="wideField" colspan="3"><input name="country" class="field" id="country" placeholder="${user.getLocation().getCountry()}"></input></td>
							</tr>
							<tr>
								<td class="label">Latitude</td>
								<td class="slimField"><input class="field" name="lat" id="lat" placeholder="${user.getLocation().getLat()}"></input></td>
								<td class="label">Longitude</td>
								<td class="wideField"><input class="field" name="lng" id="lng" placeholder="${user.getLocation().getLng()}"></input></td>
							</tr>					
						</table>

						<button id="editSubmit" type="submit" class="ui button primary btn-gradient btn-block spacer">Edit</button>
					
						<div class="ui error message"></div>						
					</div>
				</div>
			</form>	
		</div>
	</div>
</div></div>
</section>

<%@ include file="partials/footer.jsp" %>

<!-- Include form validation -->
<script src="${pageContext.request.contextPath}/resources/scripts/register.js"></script>
		
<!-- Google API -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjtXfwU4ouU7RnfoT7tXheh6wBVV8UvZA&libraries=places&callback=initAutocomplete" async defer></script>

<%@ include file="partials/footer-end.jsp" %>
