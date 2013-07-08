<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Апликација за претрагу авионских летова</a>
</h2>
<table id="radial-menu-table">
	<tr>
		<td><a href="${contextPath}/aerodromi"> <img alt="Аеродроми"
				src="${contextPath}/resources/images/airports-launcher.png"
				width="140px" height="140px" />

		</a></td>
		<td><a href="${contextPath}/putanje"> <img alt="Претраживач"
				src="${contextPath}/resources/images/paths-launcher.png"
				width="150px" height="150px" />
		</a></td>
		<td><a href="${contextPath}/avioprevoznici"> <img
				alt="Авиопревозници"
				src="${contextPath}/resources/images/airlines-launcher.png"
				width="135px" height="135px" />

		</a></td>

	</tr>
	<tr>
		<td><p class="launcher-title">аеродроми</p></td>
		<td><p class="launcher-title">летови</p></td>
		<td><p class="launcher-title">авиопревозници</p></td>
</table>

<br />