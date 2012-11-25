<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Унос новог аеродрома</a>
</h2>
<form:form action="${contextPath}/aerodromi" method="POST" commandName="airport" cssClass="form">
	<c:if test="${not empty resultMessage}">
		<div class="${messageClass}">${resultMessage}</div>
	</c:if>
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
			<td><form:label path="timezone" for="timezone">Временска зона (ГМТ):</form:label></td>
			<td><form:input path="timezone" id="timezone" class="k-textbox" /></td>
			<td><form:errors path="timezone" cssClass="error-inline" /></td>
		</tr>
		<tr>
			<td><form:label path="latitude" for="latitude">Географска дужина:</form:label></td>
			<td><form:input path="latitude" id="latitude" class="k-textbox" /></td>
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
			<td><form:input path="altitude" id="altitude" class="k-textbox" /></td>
			<td><form:errors path="altitude" cssClass="error-inline" /></td>
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