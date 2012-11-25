<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Унос новог лета</a>
</h2>
<form:form action="${contextPath}/letovi" method="POST"
	commandName="route" cssClass="form">
	<c:if test="${not empty resultMessage}">
		<div class="${messageClass}">${resultMessage}</div>
	</c:if>
	<table>
		<tr>
			<c:choose>
				<c:when test="${not empty sourceId}">
					<td><label for="sourceAirport">Полазни аеродром:</label></td>
					<td>
						<a id="sourceAirport" href="${contextPath}/aerodromi/${sourceId}" target="_blank">${sourceName}</a>
						<input type="hidden" name="source.id" id="source.id" value="${sourceId}"/>
					</td>
					<td><form:errors path="source.id" cssClass="error-inline" /></td>
				</c:when>
				<c:otherwise>
					<td><label for="sourceAirportAutocomplete">Полазни аеродром:</label></td>
					<td>
						<input id="sourceAirportAutocomplete" class="autocompleteAirport"/>
						<form:hidden path="source.id" />
					</td>
					<td><form:errors path="source.id" cssClass="error-inline" /></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${not empty destId}">
					<td><label for="destAirport">Одредишни аеродром:</label></td>
					<td>
						<a id="destAirport" href="${contextPath}/aerodromi/${destId}" target="_blank">${destName}</a>
						<input type="hidden" name="dest.id" id="dest.id" value="${destId}"/>
					</td>
					<td><form:errors path="dest.id" cssClass="error-inline" /></td>
				</c:when>
				<c:otherwise>
					<td><label for="destAirportAutocomplete">Одредишни аеродром:</label></td>
					<td>
						<input id="destAirportAutocomplete" class="autocompleteAirport"/>
						<form:hidden path="dest.id"/>
					</td>
					<td><form:errors path="dest.id" cssClass="error-inline" /></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${not empty airlineId}">
					<td><label for="airlineName">Авиопревозник:</label></td>
					<td>
						<a id="airlineName" href="${contextPath}/avioprevoznici/${airlineId}" target="_blank">${airlineName}</a>
						<input type="hidden" id="alid" name="alid" value="${airlineId}"/>
					</td>
					<td><form:errors path="alid" cssClass="error-inline" /></td>
				</c:when>
				<c:otherwise>
					<td><label for="airlineAutocomplete">Авиопревозник:</label></td>
					<td>
						<input id="airlineAutocomplete"/>
						<form:hidden path="alid"/>
					</td>
					<td><form:errors path="alid" cssClass="error-inline" /></td>	
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td><form:label path="equipment" for="equipment">Кôд опреме:</form:label></td>
			<td><form:input path="equipment" id="equipment" class="k-textbox" /></td>
			<td><form:errors path="equipment" cssClass="error-inline" /></td>		
		</tr>
		<tr>
			<td><form:label path="price" for="price">Цена карте (<span title="новчана јединица">н.ј.</span>):</form:label></td>
			<td><form:input path="price" id="price" class="k-textbox" /></td>
			<td><form:errors path="price" cssClass="error-inline" /></td>
		</tr>
		<tr>
			<td><form:label path="distance" for="distance">Раздаљина (км):</form:label></td>
			<td><form:input path="distance" id="distance" class="k-textbox" /></td>
			<td><form:errors path="distance" cssClass="error-inline" /></td>
		</tr>
		<tr>
			<td colspan="2"><br /> 
				<input type="submit" class="k-button" value="Сачувај" />
				<a href="${contextPath}/letovi/dodaj" class="k-button">Ресетуј</a>
			</td>
		</tr>
	</table>
</form:form>