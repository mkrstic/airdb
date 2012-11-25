<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<h2>
	<a href="#">Летови</a>
</h2>
<c:if test="${not empty resultMessage}">
	<div class="${messageClass}">${resultMessage}</div>
</c:if>
<div style="height:40px">
	<p id="count" style="display: inline;"></p>
	<a class="k-button k-button-icontext k-grid-add" style="float:right"
			href="${contextPath}/letovi/dodaj" title="Додај нови лет у базу" id="addRouteBtn"><span
			class="k-icon k-add"></span>Додај</a>
</div>


<div id="listView"></div>
<div id="pager" class="k-pager-wrap"></div>
<script id="routesListViewTemplate" type="text/x-jquery-tmpl">
	<div class="product-view">
		<dl>
			<dt>Полеће из</dt>
			<dd><a href="{{= ctx}}/aerodromi/{{= source.id}}">{{= source.city}} {{if source.iata}} ({{= source.iata}}) {{else source.icao}} ({{= source.icao}}) {{/if}}</a></dd>
			<dt>Слеће у</dt>
			<dd><a href="{{= ctx}}/aerodromi/{{= dest.id}}">{{= dest.city}} {{if dest.iata}} ({{= dest.iata}}) {{else dest.icao}} ({{= dest.icao}}) {{/if}}</a></dd>
			<dt>Цена карте</dt>
			<dd>{{= price}} <span title="новчана јединица">н.ј.</span></dd>
		</dl>
		<div class="edit-buttons">
			<a class="k-button" href="{{= ctx}}/letovi/{{= id }}">Детаљан приказ</a> 
		</div>
	</div>
</script>
