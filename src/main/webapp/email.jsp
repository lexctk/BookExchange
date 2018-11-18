<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>

<section class='fullscreen spacer-navbar spacer-footer'>
<div class="container spacer"><div class="row">
	<div class="col-12 d-flex flex-column justify-content-center">
		<form class="ui form" action="email" method="post">
			<div class="field">
				<label>From</label>
				<input type="text" name="emailSender" value="${emailSender}" readonly>
			</div>
			<div class="field">
				<label>To</label>
				<input type="text" name="nameRecipient" value="${nameRecipient}" readonly>
			</div>	
			<div class="field hidden">
				<label>To</label>
				<input type="text" name="idRecipient" value="${idRecipient}" readonly>
			</div>					
			<div class="field">
				<label>Subject</label>
    			<input type="text" name="emailSubject" value="[Book Exchange] ${bookName}" readonly>
  			</div>  			
			<div class="field">
				<label>Message</label>
				<textarea name="emailContent"></textarea>
  			</div>
  			<button type="submit" class="ui button primary btn-gradient btn-block spacer">Send</button>
		</form>
	</div>
</div></div>
</section>

<%@ include file="partials/footer.jsp" %>

<%@ include file="partials/footer-end.jsp" %>
