<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Авиопревозници</a> 
</h2>
<c:if test="${not empty resultMessage}">
	<div class="${messageClass}">${resultMessage}</div>
</c:if>

<div id="pageContent" class="hidden">
	<div style="height:40px">
		<p id="count" style="display: inline;"></p>
		<a class="k-button k-button-icontext k-grid-add"
			style="float: right"
			href="${contextPath}/avioprevoznici/dodaj"
			title="Додај превозника у базу"><span class="k-icon k-add"></span>Додај</a>
	</div>
	<div id="listView"></div>
	<div id="pager" class="k-pager-wrap"></div>
</div>
<script id="airlinesListViewTemplate" type="text/x-jquery-tmpl">
	<div class="product-view">
		<a href="{{= ctx}}/avioprevoznici/{{= id}}">
			{{= name}} ({{if iata}}{{= iata}}{{/if}}{{if icao}} {{= icao}}{{/if}})
		</a>
	</div>
</script>