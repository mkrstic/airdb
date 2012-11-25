<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	
	<a href="#">Аеродроми</a><img id="mapLoadingImg" src="${contextPath}/resources/images/ajax_loading_small.gif" style="display: none" />
	<div id="displayRadio" style="float: right;">
		<input type="radio" name="radio" id="listBtn" /><label for="listBtn"
			title="Листа"></label> <input type="radio" name="radio" id="mapBtn" /><label
			for="mapBtn" title="Мапа"></label>
	</div>
</h2>
<c:if test="${not empty resultMessage}">
	<div class="${messageClass}">${resultMessage}</div>
</c:if>
<div id="listContent" class="hidden">
	<div style="height: 40px">
		<p id="count" style="display: inline"></p>
		<a class="k-button k-button-icontext k-grid-add" style="float: right"
			href="${contextPath}/aerodromi/dodaj" title="Додај аеродром у базу">
				<span class="k-icon k-add"></span>Додај</a>
	</div>
	<div style="padding: 5px 0px 10px 0">
		<input type="text" id="countryFilterTxt" class="k-textbox"
			placeholder="Пронађи државу..." style="width: 200px" />
	</div>
	<div id="listView"></div>
	<div id="pager" class="k-pager-wrap"></div>
</div>
<div id="mapContent">
	<div id="map"></div>
</div>

<script id="countriesListViewTemplate" type="text/x-jquery-tmpl">
	<div class="product-view">
		<a href="{{= ctx}}/drzave/{{= id}}/aerodromi">
			<table>
				<tr>
					<td><img class="lazy" src="{{= ctx}}/resources/images/countries/32/blank.jpg" data-original="{{= flagUrl(code)}}" width="32" height="32"/></td>
					<td>&nbsp;{{= name}}</td>
				</tr>
			</table>
		</a>
	</div>
</script>