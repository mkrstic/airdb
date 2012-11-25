<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="jsPath" value="${contextPath}/resources/js"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name="author" content="Mladen Krstic (br. dosijea 52/O8-11)" />
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<link href="${contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/resources/js/lib/kendoui/styles/kendo.common.min.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/js/lib/kendoui/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/js/lib/apycom-menu/menu.css" rel="stylesheet" type="text/css"/>
	<tiles:useAttribute id="cssList" name="css" classname="java.util.List" />
	<c:forEach var="cssUrl" items="${cssList}">
		<link href="${cssUrl}" rel="stylesheet" type="text/css"/>
	</c:forEach>
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
	<script>var ctx = "${contextPath}"</script>
	<script>var jsPath = "${jsPath}"</script>
	<script src="${jsPath}/global/init.js"></script>
</head>
<body>
	<div id="bodywrap">
		<section id="pagetop">
			<tiles:insertAttribute name="top" />	
		</section>
		<header id="pageheader">
			<tiles:insertAttribute name="header" />	
		</header>
		<div id="contents">
			<tiles:insertAttribute name="featured-content"/>
			<div id="leftcontainer">
				<tiles:insertAttribute name="main-content"/>
			</div>
			<tiles:insertAttribute name="sidebar"/>
			<div class="clear"></div>
		</div>
	</div>
	<footer id="pagefooter">
		<div id="footerwrap">
			<tiles:insertAttribute name="footer" />
		</div>
	</footer>
	<script src="${contextPath}/resources/js/lib/kendoui/js/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/lib/kendoui/js/kendo.web.min.js"></script>
	<script src="${contextPath}/resources/js/lib/kendoui/cultures/kendo.culture.sr-Cyrl-RS.min.js"></script>
	<script src="${contextPath}/resources/js/lib/jquery.template/jquery.tmpl.js"></script>
	<script src="${jsPath}/global/layout.js"></script>
	<script src="${contextPath}/resources/js/lib/apycom-menu/menu.js"></script>
	<tiles:useAttribute id="scriptList" name="script" classname="java.util.List" />
	<c:forEach var="scriptUrl" items="${scriptList}">
  		<script type="text/javascript" src="${scriptUrl}"></script>
	</c:forEach>
	<script id="airportAutocompleteTemplate" type="text/x-jquery-tmpl">
		<div class="airportAutocompleteDropdown"><span class="ddown-first-row">{{= city}} ({{= name}})</span><span class="ddown-second-row">{{if iata}}{{= iata}}, {{else icao}}{{= icao}}, {{/if}}{{= country}}</span></div>
	</script>
</body>
</html>