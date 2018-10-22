<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<%@ include file="partials/navbar.jsp" %>


<section class='hero fullscreen'>
	<div class='parallax' style='background-image: url("../resources/images/parallax.jpg");'></div>
	<div class='hero-content'>
		<h1 class='hero-item item1'>HEY, THIS IS <span class='orange'>BOOK EXCHANGE</span></h1>
		<p class='hero-item item2'>A really <strong>cool</strong> app. With some awesome <strong>stuff</strong>.</p>
		<div class="hero-item item3 hero-search ui large icon input">
		  <input type="text" placeholder="Search...">
		  <i class="search icon"></i>
		</div>
	</div>
</section>


<section id='selection' class='fullscreen'>
	<div class="container pt-4 " >
	    <div class="row">
	        <div class="col-xs-6 col-sm-4">
	            <div class="card">
	                <a class="img-card">
	                    <img src="../resources/images/parallax.jpg" />
	                </a>
	                <br />
	                <div class="card-content">
	                    <h4 class="card-title">Book 1 </h4>
	                    <div class="">Book details</div>
	                </div>
	             
	            </div>
	        </div>
	    </div>
	</div>
</section>

<%@ include file="partials/footer.jsp" %>