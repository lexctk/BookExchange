<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp" %>

<div class="row h-md-100">
    <div class="col-sm-12 col-md-6 book-background">
        <div class="ui right aligned basic segment m-lg-5">
            <div class="column my-5">
                <h2 class="ui right aligned header"><i class="fas fa-search"></i> Search for books</h2>
                <h2 class="ui right aligned header"><i class="fas fa-users"></i> Exchange</h2>
                <h2 class="ui right aligned header"><i class="fas fa-book-reader"></i> Find events near you</h2>
            </div>
        </div>        
    </div>
    <div class="col-sm-12 col-md-6">
          <div class="ui center aligned basic segment m-lg-5">
            <div class="column my-5">
              <h2 class="ui center aligned header">BookExchange</h2>
              <hr>
              <form class="form-signin" action="register" method="post">
                <div class="form-group">
                  <input type="email" name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email"> 
                </div>
                <div class="form-group">
                  <input type="password" name="password" class="form-control" id="password" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Register</button>
              </form>
            </div>
            <div class="divider-column my-5"><div class="ui horizontal divider">
                Or
            </div></div>   
            <div class="column my-5">
              <button class="btn btn-block btn-login btn-google">Register with Google</button>
              <button class="btn btn-block btn-login btn-twitter">Register with Twitter</button>
              <button class="btn btn-block btn-login btn-facebook">Register with Facebook</button>
            </div>
        </div>               
    </div>        
</div>


<%@ include file="partials/footer.jsp" %>