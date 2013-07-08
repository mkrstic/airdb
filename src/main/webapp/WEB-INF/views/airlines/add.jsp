<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Унос новог авиопревозника</a>
</h2>
<form:form action="${contextPath}/avioprevoznici" method="POST" commandName="airline" cssClass="form">
	<c:if test="${not empty resultMessage}">
		<div class="${messageClass}">${resultMessage}</div>
	</c:if>
	<table>
		<tr>
			<td><form:label path="name" for="name">Назив авиопревозника:</form:label></td>
			<td><form:input path="name" id="name" class="k-textbox" /></td>
			<td><form:errors path="name" cssClass="error-inline" /></td>
		</tr>
		<tr>
			<td><form:label path="country" for="country">Држава оснивач:</form:label></td>
			<td><form:select path="country" id="country" class="k-textbox" /></td>
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
			<td><form:select path="active">
				<form:option value="true">Активан</form:option>
				<form:option value="false">Неактиван</form:option>
			</form:select></td>
			<td><form:errors path="active" cssClass="error-inline" /></td>
		</tr>
		<tr>
			<td colspan="2"><br /> <input type="submit" class="k-button"
				value="Сачувај" /> 
			</td>
		</tr>
	</table>
</form:form>
<script id="countryDropdownTemplate" type="text/x-jquery-tmpl">
	<div>
		<img src="{{= ctx}}/resources/images/countries/16/{{= code.toLowerCase()}}.png"/>
		{{= name}}
	</div>
</script>