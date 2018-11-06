<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<div class="col-xs-12 col-md-4 col-lg-3 spacer">
	<h2>Filter</h2>
	<div class="d-flex flex-column" id="book-list-filter">	
		<h3>Categories</h3>
		<c:forEach var="category" items="${categories}">
			<div class="inline field">
				<div class="ui toggle checkbox">
					<input data-multifilter="<c:out value='${category}'/>" type="checkbox" name="<c:out value='${category}'/>" value="<c:out value='${category}'/>">
					<label><c:out value='${category}'/></label>
				</div>
			</div>
		</c:forEach>
		<h3>Languages</h3>
		<c:forEach var="language" items="${languages}">
			<div class="inline field">
				<div class="ui toggle checkbox">
					<input data-multifilter="<c:out value='${language}'/>" type="checkbox" name="<c:out value='${language}'/>" value="<c:out value='${language}'/>">
					<label><c:out value='${language}'/></label>
				</div>
			</div>
		</c:forEach>		
	</div>
</div>
<div class="col-xs-12 col-md-8 col-lg-9 spacer">
	<h2>Books</h2>
	<div class="row filtr-container" id="book-list-books">
	${booklist}
	</div>
</div>