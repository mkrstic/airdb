<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#" id="routeTitle">Информације о лету Ф</a>
</h2>
<div class="k-content">
	<c:if test="${not empty resultMessage}">
		<div class="${messageClass}">${resultMessage}</div>
	</c:if>
	<div id="tabstrip" class="hidden">
		<ul>
			<li ${mainTabActive}>Основни подаци</li>
			<li id="mapTab">Мапа</li>
			<li ${editTabActive} id="editTab" title="Уреди податке"><span class="k-icon k-custom" /></li>
		</ul>
		<div></div>
		<div>
			<div id="map"></div>
		</div>
		<div>
			<form:form method="PUT" action="${contextPath}/letovi/${routeId}"
				commandName="route" cssClass="form">
				<form:hidden path="id"/>
				<form:hidden path="source.id"/>
				<form:hidden path="dest.id"/>
				<table>
					<tr>
						<td><label>Од:</label></td>
						<td><a id="sourceAirportLink"></a></td>
					</tr>
					<tr>
						<td><label>До:</label></td>
						<td><a id="destAirportLink"></a></td>
					</tr>		
					<tr>
						<td><form:label path="alid" for="airlineAutocomplete">Авиопревозник:</form:label></td>
						<td>
							<form:hidden path="alid" id="alid"/>
							<div id="airlineInfoWrapper">
								<a id="airlineLink" href="javascript:void(0)"></a> 
								<a href="javascript:void(0);" id="airlineEditBtn" class="k-button"
									title="Промени авиопревозника"><span
									class="k-icon k-edit"></span>
								</a>
							</div>
							<div id="airlineEditWrapper">
								<input id="airlineAutocomplete" style="width:170px"/>
								<a href="javascript:void(0);" id="airlineCancelBtn" class="k-button"
									title="Одустани"><span
									class="k-icon k-cancel"></span>
								</a>
							</div>
						</td>
						<td><form:errors path="alid" cssClass="error-inline" /></td>
					</tr>
					<tr>
						<td><form:label path="equipment" for="equipment">Кôд опреме:</form:label></td>
						<td><form:input path="equipment" id="equipment"
								class="k-textbox" /></td>
						<td><form:errors path="equipment" cssClass="error-inline" /></td>
					</tr>
					<tr>
						<td><form:label path="price" for="price">Цена карте (<span title="новчана јединица">н.ј.</span>):</form:label></td>
						<td><form:input path="price" id="price" class="k-textbox" /></td>
						<td><form:errors path="price" cssClass="error-inline" /></td>
					</tr>
					<tr>
						<td><form:label path="distance" for="distance">Раздаљина (км):</form:label></td>
						<td><form:input path="distance" id="distance"
								class="k-textbox" /></td>
						<td><form:errors path="distance" cssClass="error-inline" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<br />
							<input type="submit" class="k-button" value="Сачувај измене"/>
							<a id="removeRouteBtn" class="k-button k-button-icontext k-grid-delete" href="javascript:void(0)"> <span
								class="k-icon k-delete"></span> Уклони лет из базе
							</a>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</div>
<input type="hidden" id="route_id" value="${routeId}">
<input type="hidden" id="selectedAlid" value=""/>
<form:form id="removeRouteForm" method="DELETE" action="${contextPath}/letovi/${routeId}" cssClass="hidden"></form:form>
<script id="routeInfoTemplate" type="text/x-jquery-tmpl">
	<dl class="specification">
		<dt>Број лета:</dt>
		<dd>Ф{{= route.id}}</dd>
		<dt>Од:</dt>
		<dd>
			<a href="{{= ctx}}/aerodromi/{{= route.source.id}}">{{= route.source.city}}{{if route.source.iata}} ({{= route.source.iata}}){{else route.source.icao}} ({{= route.source.icao}}{{/if}}, {{= route.source.country}}</a>
		</dd>
		<dt>До:</dt>
		<dd>
			<a href="{{= ctx}}/aerodromi/{{= route.dest.id}}">{{= route.dest.city}}{{if route.dest.iata}} ({{= route.dest.iata}}){{else route.dest.icao}} ({{= route.dest.icao}}{{/if}}, {{= route.dest.country}}</a>
		</dd>
		<dt>Авиопревозник:</dt>
		<dd>{{if airline.id}} <a href="{{= ctx}}/avioprevoznici/{{= airline.id}}">{{= airline.name}}{{if airline.country}}, {{= airline.country}}{{/if}}</a>{{else}} непознато {{/if}}</dd>
		<dt>Седиште авиопревозника:</dt>
		<dd>{{if airline.country}} {{= airline.country}} {{else}} непознато {{/if}}</dd>
		<dt>Кôд опреме:</dt>
		<dd>{{if route.equipment}}{{= route.equipment}}{{else}}непознато{{/if}}</dd>
		<dt>Цена карте:</dt>
		<dd>{{= route.price}} <span title="новчана јединица">н.ј.</span></dd>
		<dt>Раздаљина:</dt>
		<dd>{{= route.distance}} км</dd>
	</dl>
</script>
<script id="infoWindowAirportTemplate" type="text/x-jquery-tmpl">
<div id="infoWindow">
	<p>Аеродром <span class="bold">{{= name}}, {{= city}}</span></p>
	<p>Држава: {{= country}}</p>
	<br/>
	<a href="{{= ctx}}/aerodromi/{{= id}}" style="color:#A82A15;text-decoration:none">Детаљан опис</a>
</div>
</script>
<script id="airlineAutocompleteTemplate" type="text/x-jquery-tmpl">
	<div class="airportAutocompleteDropdown"><span class="ddown-first-row">{{= name}} {{if iata}} ({{= iata}}) {{else icao}} ({{= icao}}){{/if}}</span><span class="ddown-second-row">{{= country}}</span></div>
</script>

