<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
 	<img id="airportCountryImg" aлт="држава" class="hidden"/> 
	<a href="#" id="airportName"></a>
</h2>
<c:if test="${not empty resultMessage}">
	<div class="${messageClass}">${resultMessage}</div>
</c:if>
<div id="tabstrip" class="hidden">
	<ul>
		<li ${mainTabActive}>Основни подаци</li>
		<li>Одлазни летови</li>
		<li>Долазни летови</li>
		<li>Аеродроми у близини</li>
		<li id="mapTab">Мапа</li>
		<li id="editTab" title="Уреди податке" ${editTabActive}><span
			class="k-icon k-custom" /></li>
	</ul>
	<div></div>
	<div>
		<ul id="airportsOutListView"></ul>
		<div id="airportsOutPager" class="k-pager-wrap"></div>
	</div>
	<div>
		<ul id="airportsInListView"></ul>
		<div id="airportsInPager" class="k-pager-wrap"></div>
	</div>
	<div>
		<ul id="airportsNearbyListView"></ul>
		<div id="airportsNearbyPager" class="k-pager-wrap"></div>
	</div>
	<div>
		<table class="smaller-text">
			<tr>
				<td><img alt=""
					src="${contextPath}/resources/images/markers/marker_blue_24x20.png"> <label>Повезан
						у оба смера</label></td>
				<td><img alt=""
					src="${contextPath}/resources/images/markers/marker_green_24x20.png"> <label>Повезан
						одлазном путањом</label></td>
				<td><img alt=""
					src="${contextPath}/resources/images/markers/marker_yellow_24x20.png"> <label>Повезан
						долазном путањом</label></td>
			</tr>
		</table>
		<div id="map"></div>
	</div>
	<div style="display: none">
		<ul id="panelbar">
			<li class="k-state-active">Уреди основне податке
				<div class="panelContent">
					<form:form method="PUT"
						action="${contextPath}/aerodromi/${airportId}"
						commandName="airport" cssClass="form">
						<form:hidden path="id"/>
						<table>
							<tr>
								<td><form:label path="name" for="name">Назив аеродромa:</form:label></td>
								<td><form:input path="name" id="name" class="k-textbox" /></td>
								<td><form:errors path="name" cssClass="error-inline" /></td>  
							</tr>
							<tr>
								<td><form:label path="city" for="city">Место:</form:label></td>
								<td><form:input path="city" id="city" class="k-textbox" /></td>
								<td><form:errors path="city" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="country" for="country">Држава:</form:label></td>
								<td><form:input path="country" id="country" class="k-textbox"></form:input></td>
								<td><form:errors path="country" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="country" for="countryCode">Ознака државе:</form:label></td>
								<td><form:input path="countryCode" id="countryCode" class="k-textbox"></form:input></td>
								<td><form:errors path="countryCode" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="iata" for="iata">ИАТА кôд:</form:label></td>
								<td><form:input path="iata" id="iata" class="k-textbox" /></td>
								<td><form:errors path="iata" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="icao" for="icao">ИКАО кôд:</form:label></td>
								<td><form:input path="icao" id="icao" class="k-textbox" /></td>
								<td><form:errors path="icao" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="timezone" for="timezone">Временска зона (ГМТ):</form:label></td>
								<td><form:input path="timezone" id="timezone"
										class="k-textbox" /></td>
								<td><form:errors path="timezone" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="latitude" for="latitude">Географска дужина:</form:label></td>
								<td><form:input path="latitude" id="latitude"
										class="k-textbox" /></td>
								<td><form:errors path="latitude" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="longitude" for="longitude">Географска ширина:</form:label></td>
								<td><form:input path="longitude" id="longitude"
										class="k-textbox" /></td>
								<td><form:errors path="longitude" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="altitude" for="altitude">Надморска висина (м):</form:label></td>
								<td><form:input path="altitude" id="altitude"
										class="k-textbox" /></td>
								<td><form:errors path="altitude" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<br /> 
									<input type="submit" class="k-button" value="Сачувај измене" />
									<button id="removeAirportBtn" type="button" class="k-button">Уклони аеродром из базе</button>
									<!--  <a id="removeAirportBtn" class="k-button k-button-icontext k-grid-delete" href="javascript:void(0)"> <span
										class="k-icon k-delete"></span> Уклони аеродром из базе
									</a>-->
								</td>
							</tr>
						</table>
					</form:form>
				</div>
			</li>
			<li>Уреди аеродромске везе
				<div class="panelContent">
					<form:form id="editRoutesForm" method="DELETE"
						action="${contextPath}/aerodromi/${airportId}/letovi">
						<div class="k-toolbar k-grid-toolbar">
							<a class="k-button k-button-icontext k-grid-add" href="${contextPath}/letovi/dodaj?sourceId=${airportId}"> <span
								class="k-icon k-add"></span> Додај нови лет
							</a> <a id="removeSelectedRoutesBtn"
								class="k-button k-button-icontext k-grid-delete"
								href="javascript:void(0)"> <span class="k-icon k-delete"></span> Уклони
								означене летове
							</a>
						</div>
						<table id="airportRoutesGrid">
							<thead>
								<tr>
									<th><input id="checkAll" type="checkbox" class="checkbox-column"/></th>
									<th>Полазиштe</th>
									<th>Oдредиштe</th>
									<th>Цена карте</th>
									<th>Опције</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="5"></td>
								</tr>
							</tbody>
						</table>
					</form:form>
				</div>
			</li>
		</ul>
	</div>
</div>
<input type="hidden" id="airport_id" value="${airportId}">
<form:form id="removeAirportForm" method="DELETE" action="${contextPath}/aerodromi/${airportId}" cssClass="hidden"></form:form>
<script id="airportInfoTemplate" type="text/x-jquery-tmpl">
	<dl class="specification">
	<dt>Назив аеродрома:</dt>
	<dd>{{if name}} {{= name}} {{else}} непознато {{/if}}</dd>
	<dt>Место:</dt>
	<dd>{{if city}} {{= city}} {{else}} непознато {{/if}}</dd>
	<dt>Држава:</dt>
	<dd>{{if country}} {{= country}} {{else}} непознато {{/if}}</dd>
	<dt>ИАТА кôд:</dt>
	<dd>{{if iata}} {{= iata}} {{else}} непознато {{/if}}</dd>
	<dt>ИКАО кôд:</dt>
	<dd>{{if icao}} {{= icao}} {{else}} непознато {{/if}}</dd>
	<dt>Временска зона:</dt>
	<dd>
		{{if timezone > 0}}
			ГМТ+{{= timezone}}
		{{else timezone <= 0}}
			ГМТ{{= timezone}}
		{{else}}
			непознато
		{{/if}}
	</dd>
	<dt>Географска дужина:</dt>
	<dd>{{= latitude}}</dd>
	<dt>Географска ширина:</dt>
	<dd>{{= longitude}}</dd>
	<dt>Надморска висина:</dt>
	<dd>{{if altitude}} {{= altitude}} м {{else}} непознато {{/if}}</dd>
</dl>
</script>
<script id="airportInOutTemplate" type="text/x-jquery-tmpl">
	<li class="li-block"><a href="{{= ctx }}/aerodromi/{{= id }}">{{= city}}{{if iata}} ({{= iata}}){{else icao}} ({{= icao}}){{/if}}, {{= country}}</a></li>
</script>
<script id="airportNearbyTemplate" type="text/x-jquery-tmpl">
	<li class="li-block"><a href="{{= ctx }}/aerodromi/{{= id }}">{{= city}}{{if iata}} ({{= iata}}){{else icao}} ({{= icao}}){{/if}}, {{= country}} ({{= distance}} км)</a></li>
</script>
<script id="airportTemplateBlank" type="text/x-jquery-tmpl">
	<li class="li-blank">{{= text}} <a href="{{= ctx}}/letovi/dodaj">Додајте лет</a></li>
</script>
<script id="infoWindowAirportTemplate" type="text/x-jquery-tmpl">
<div id="infoWindow">
	<p>Аеродром <span class="bold">{{= name}}, {{= city}}</span></p>
	<p>Држава: {{= country}}</p>
	<br/>
	<a href="{{= ctx}}/aerodromi/{{= id}}" style="color:#A82A15;text-decoration:none">Детаљан опис</a>
</div>
</script>
<script id="airportRoutesGridTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td><input type="checkbox" data-routeid="{{= id}}" class="k-checkbox" name="routeIds[]" value=""></td>
		<td><a title="Детаљи о аеродрому" href="{{= ctx}}/aerodromi/{{= source.id}}" target="_blank">{{= source.name }}, {{= source.city}}</a></td>
		<td><a title="Детаљи о аеродрому" href="{{= ctx}}/aerodromi/{{= dest.id}}" target="_blank">{{= dest.name }}, {{= dest.city}}</a></td>
		<td>{{= price}} <span title="новчана јединица">н.ј.</span></td>
		<td><a class="k-button" href="{{= ctx}}/letovi/{{= id }}">Детаљи о лету</a></td>
	</tr>
</script>