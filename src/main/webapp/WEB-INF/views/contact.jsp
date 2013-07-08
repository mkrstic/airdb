<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Контакт</a>
</h2>
<form:form action="${contextPath}/kontakt" method="POST" cssClass="form" commandName="contact">
	<p>Попуњавањем формулара испод можете поставити питање аутору, упутити критику или сугестију у вези са овим радом.</p>
	<c:if test="${not empty resultMessage}">
		<p class="${messageClass}">${resultMessage}</p>
	</c:if>
	<table id="contactTable">
		<tr class="textfield">
			<td><form:label path="name" for="name">Ваше име:</form:label></td>
			<td><form:input path="name" id="name" class="k-textbox" /></td>
			<td><form:errors path="name" cssClass="error-inline" /></td>
		</tr>
		<tr class="textfield">
			<td><form:label path="email" for="email">Контакт имејл:</form:label></td>
			<td><form:input path="email" id="email" class="k-textbox" /></td>
			<td><form:errors path="email" cssClass="error-inline" /></td>
		</tr>
		<tr class="textfield">
			<td><form:label path="message" for="message">Порука:</form:label></td>
			<td><form:textarea path="message" id="message" class="k-textbox"/></td>
			<td><form:errors path="message" cssClass="error-inline" /></td>
		</tr>
		<tr>
			<td colspan="2"><br /> 
				<input type="submit" class="k-button" value="Пошаљи" />
			</td>
		</tr>
	</table>
</form:form>