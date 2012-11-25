<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="highlight" value="${param.highlight}" />
<p id="siteinfo"></p>
<div id="menu" data-highlight="${highlight}">
	<ul class="menu">
		<li id="menu_home"><spring:url value="/" var="homepage" /> <a
			href="${homepage}"><span>Насловна</span></a></li>
		<li id="menu_airports"><spring:url value="/aerodromi" var="airports" />
			<a href="${airports}"><span>Аеродроми</span></a></li>
		<li id="menu_airlines"><spring:url value="/avioprevoznici"
				var="airlines" /> <a href="${airlines}"><span>Авиопревозници</span></a></li>
		<li id="menu_routes"><spring:url value="/letovi" var="routes" /> <spring:url
				value="/putanje" var="paths" /> <a href="${routes}"
			class="parent"><span>Летови</span></a>
			<div>
				<ul>
					<li><a href="${routes}"><span>Сви летови</span></a></li>
					<li><a href="${paths}"><span>Налажење пута</span></a></li>
				</ul>
			</div></li>
		<li id="menu_info"><a href="javascript:void(0)" class="last parent"><span>Инфо</span></a>
			<div>
				<ul>
					<li><spring:url value="/o-radu" var="about" /> <a
						href="${about}"><span>О раду</span></a></li>
					<li><spring:url value="/kontakt" var="contact" /> <a
						href="${contact}"><span>Контакт</span></a></li>
				</ul>
			</div></li>
	</ul>
</div>

