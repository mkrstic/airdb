<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h1>
<a href="${contextPath}">
	<img src="${contextPath}/resources/images/logo.png"/>
</a>
</h1>
<div id="search">
	<input type="text" class="autocompleteAirport hidden" id="menu_autocompleteAirport">
</div>