<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#" id="countryName">Аеродроми у држави </a>
	<div id="displayRadio">
			<input type="radio" name="radio" id="listBtn"/><label for="listBtn" title="Листа"></label>
			<input type="radio" name="radio" id="mapBtn" /><label for="mapBtn" title="Мапа"></label>
	</div>
</h2>
<c:if test="${not empty resultMessage}">
		<div class="${messageClass}">${resultMessage}</div>
</c:if>
<div id="listContent" class="hidden">
	<div style="height: 40px">
		<p id="count" style="display:inline"></p>
		<a class="k-button k-button-icontext k-grid-add" style="float:right" 
			href="${contextPath}/aerodromi/dodaj" title="Додај аеродром у базу"><span
			class="k-icon k-add"></span>Додај</a>
	</div>
	<div style="padding:5px 0px 10px 0">
		<input type="text" id="arirportFilterTxt" class="k-textbox" style="width:200px" placeholder="Пронађи аеродром..."/>
	</div>
	<div id="listView"></div>
	<div id="pager" class="k-pager-wrap"></div>	
</div>
<div id="mapContent">
	<div id="map"></div>
</div>
<input type="hidden" id="country_id" value="${countryId}"/>
<script id="infoWindowAirportTemplate" type="text/x-jquery-tmpl">
<div id="infoWindow">
	<p>Аеродром <span class="bold">{{= name}}, {{= city}}</span></p>
	<p>Држава: {{= country}}</p>
	<br/>
	<a href="{{= ctx}}/aerodromi/{{= id}}" style="color:#A82A15;text-decoration:underline">Детаљан опис</a>
</div>
</script>
<script id="airportsListViewTemplate" type="text/x-jquery-tmpl">
	<div class="product-view">
		<a href="{{= ctx}}/aerodromi/{{= id}}">
			{{= city}} : {{= name}}
		</a>
	</div>
</script>