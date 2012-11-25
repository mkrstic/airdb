<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#" id="airlineName"></a>
</h2>
<c:if test="${not empty resultMessage}">
	<div class="${messageClass}">${resultMessage}</div>
</c:if>
<div id="tabstrip" class="hidden">
	<ul>
		<li ${mainTabActive}>Основни подаци</li>
		<li>Летови</li>
		<li id="editTab" title="Уреди податке" ${editTabActive}><span
			class="k-icon k-custom" /></li>
	</ul>
	<div></div>
	<div>
		<ul id="routesListView"></ul>
		<div id="routesListViewPager" class="k-pager-wrap"></div>
	</div>
	<div>
		<ul id="panelbar">
			<li class="k-state-active">Уреди основне податке
				<div class="panelContent">
					<form:form method="PUT"
						action="${contextPath}/avioprevoznici/${airlineId}"
						commandName="airline" cssClass="form">
						<form:hidden path="id"/>							
						<table id="infoTable">
							<tr>
								<td><form:label path="name" for="name">Назив авиопревозника:</form:label></td>
								<td><form:input path="name" id="name" class="k-textbox" /></td>
								<td><form:errors path="name" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="country" for="country">Држава оснивач:</form:label></td>
								<td><form:input path="country" id="country"
										class="k-textbox" /></td>
								<td><form:errors path="country" cssClass="error-inline" /></td>
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
								<td><form:label path="callsign" for="callsign">Позивна ознака:</form:label></td>
								<td><form:input path="callsign" id="callsign"
										class="k-textbox" /></td>
								<td><form:errors path="callsign" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td><form:label path="active" for="active">Оперативно стање:</form:label></td>
								<td id="activeDropdown"><form:select path="active" itemValue="active">
										<form:option value="true" label="Активан"></form:option>
										<form:option value="false" label="Неактиван"></form:option>
									</form:select></td>
								<td><form:errors path="active" cssClass="error-inline" /></td>
							</tr>
							<tr>
								<td colspan="2"><br /> 
									<input type="submit" class="k-button" value="Сачувај измене" />
									<button id="removeAirlineBtn" type="button" class="k-button">Уклони авиопревозника из базе</button> 
							</tr>
						</table>
					</form:form>
				</div>
			</li>
			<li>Уреди летове
				<div class="panelContent">
					<form:form id="editRoutesForm" method="DELETE"
						action="${contextPath}/avioprevoznici/${airportId}/letovi">
						<div class="k-toolbar k-grid-toolbar">
							<a class="k-button k-button-icontext k-grid-add"
								href="${contextPath}/letovi/dodaj?airlineId=${airlineId}"> <span
								class="k-icon k-add"></span> Додај нови лет
							</a> <a id="removeSelectedRoutesBtn"
								class="k-button k-button-icontext k-grid-delete" href="javascript:void(0)"> <span
								class="k-icon k-delete"></span> Уклони означене летове
							</a>
						</div>
						<table id="airlineRoutesGrid">
							<thead>
								<tr>
									<th><input id="checkAll" type="checkbox"
										class="checkbox-column" /></th>
									<th>Полазиштe</th>
									<th>Oдредиштe</th>
									<th>Цена карте</th>
									<th>Опције</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="4"></td>
								</tr>
							</tbody>
						</table>
					</form:form>
				</div>
			</li>
		</ul>
	</div>
</div>
<input type="hidden" id="airline_id" value="${airlineId}">
<form:form id="removeAirlineForm" method="DELETE"
	action="${contextPath}/avioprevoznici/${airlineId}" cssClass="hidden"></form:form>
<script id="routesBlankTemplate" type="text/x-jquery-tmpl">
	<li class="li-blank">Нема података о летовима које организује овај превозник. <a href="{{= ctx}}/letovi/dodaj">Додајте лет</a></li>
</script>
<script id="airlineInfoTemplate" type="text/x-jquery-tmpl">
	<dl class="specification">
	<dt>Назив превозника:</dt>
	<dd>{{if name}} {{= name}} {{else}} непознато {{/if}}</dd>
	<dt>Држава:</dt>
	<dd>{{if country}} {{= country}} {{else}} непознато {{/if}}</dd>
	<dt>ИАТА кôд:</dt>
	<dd>{{if iata}} {{= iata}} {{else}} непознато {{/if}}</dd>
	<dt>ИКАО кôд:</dt>
	<dd>{{if icao}} {{= icao}} {{else}} непознато {{/if}}</dd>
	<dt>Позивна ознака:</dt>
	<dd>{{if callsign}} {{= callsign}} {{else}} непознато {{/if}}</dd>
	<dt>Оперативно стање:</dt>
	<dd>{{if active}} активан {{else}} неактиван/непознато {{/if}}</dd>
</dl>
</script>
<script id="routesListViewTemplate" type="text/x-jquery-tmpl">
	<li class="li-block"><a href="{{= ctx }}/letovi/{{= id }}" title="Детаљи о лету">{{= source.city }} {{if source.iata}} ({{= source.iata}}) {{else source.icao}} ({{= source.icao}}) {{/if}}&nbsp;→&nbsp;{{= dest.city }} {{if dest.iata}} ({{= dest.iata}}) {{else dest.icao}} ({{= dest.icao}}) {{/if}}</a></li>
</script>
<script id="airlineRoutesGridTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td style="width:10px"><input type="checkbox" data-routeid="{{= id}}" class="k-checkbox route-checkbox" name="routeIds[]" value=""></td>
		<td style="width:150px"><a title="Детаљи о аеродрому" href="{{= ctx}}/aerodromi/{{= source.id}}" target="_blank">{{= source.name }}, {{= source.city}}</a></td>
		<td style="width:150px"><a title="Детаљи о аеродрому" href="{{= ctx}}/aerodromi/{{= dest.id}}" target="_blank">{{= dest.name }}, {{= dest.city}}</a></td>
		<td style="width:10px">{{= price}} <span title="новчана јединица">н.ј.</span></td>
		<td style="width:120px"><a class="k-button" href="{{= ctx}}/letovi/{{= id }}">Детаљи о лету</a></td>
	</tr>
</script>