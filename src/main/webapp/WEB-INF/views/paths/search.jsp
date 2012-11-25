<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2>
	<a href="#">Претраживач летова</a>
	<img id="loadingImg"
		src="${contextPath}/resources/images/ajax_loading_small.gif"
		alt="Учитава се..." width="35" height="35" style="float: right"
		class="hidden" /> 
</h2>

<div class="hidden" id="pageContent">
	<table id="pathSearchTable">
		<tr>
			<td><input id="autocompleteAirportStart"
				class="autocompleteAirport" /></td>
			<td><input id="autocompleteAirportEnd"
				class="autocompleteAirport" /></td>
			<td id="typeField"><label for="pathType">Тражи:</label> <input id="pathType"/></td>
			<td id="stopsField"><label for="maxLength">Преседања:&nbsp;&nbsp; </label> <input
				id="maxLength"/></td>
		</tr>
	</table>
	<div id="pathTemplateContainer"></div>
	<div id="map"></div>
</div>
<script id="pathTemplate" type="text/x-jquery-tmpl">
	<h3>Пут пронађен</h3>
	<p style="font-size:smaller;color: gray">{{if total > 1}}{{= total}} путева {{/if}}({{= elapsed}} милисекунди)</p>
	<table style="width: 100%">
		<tr>
			<td style="width: 85%">
				<table>
					{{each($i, $route) path.routes}}
					{{if ($i == 0) && (first.id != path.start.id)}}
					<tr>
						<td colspan="3" style="padding-left:2px;">
							<span style="padding-right:10px">0. Другим превозним средством {{= Maps.findDistance(first.latitude, first.longitude, $route.source.latitude, $route.source.longitude)}} км од </span>
							<img style="padding: 0 5px 0 5px; width:16px" src="{{= ctx}}/resources/images/countries/16/{{= first.countryCode.toLowerCase()}}.png" alt="{{= first.country}}" title="{{= first.country}}"/>
							<a href="{{= ctx}}/aerodromi/{{= first.id}}" title="Полазни аеродром">{{= first.city}}{{if
														first.iata}} ({{= first.iata}}){{else
														first.icao}} ({{= first.icao}}){{/if}}</a>
							<span style="padding: 0 10px 0 10px"> до </span>
							<img style="padding: 0 5px 0 5px; width:16px" src="{{= ctx}}/resources/images/countries/16/{{= $route.source.countryCode.toLowerCase()}}.png" alt="{{= $route.source.country}}" title="{{= $route.source.country}}"/>
							<a href="{{= ctx}}/aerodromi/{{= $route.source.id}}" title="Долазни аеродром">{{= $route.source.city}}{{if
														$route.source.iata}} ({{= $route.source.iata}}){{else
														$route.source.icao}} ({{= $route.source.icao}}){{/if}}</a>
						</td>
					</tr>
					{{/if}}
					<tr>
						<td style="width: 10%">
							<table>
								<tr>
									<td>{{= $i+1}}.</td>
									<td><img
										src="{{= ctx}}/resources/images/airplane_icon_16x16.png" /></td>
									<td><a href="{{= ctx}}/letovi/{{= $route.id }}"
										title="Број лета">Ф{{= $route.id}}</td>
								</tr>
							</table>
						</td>
						<td style="width:85%">
							<table style="width:100%;text-align:center">
								<tr>
									<td style="width:33%">
										<table style="width:100%;margin-left:5px;">
											<tr>
												<td style="text-align:left;padding-left:5px">
													<img style="padding: 0 5px 0 5px; width:16px" src="{{= ctx}}/resources/images/countries/16/{{= $route.source.countryCode.toLowerCase()}}.png" alt="{{= $route.source.country}}" title="{{= $route.source.country}}"/>
													<a href="{{= ctx}}/aerodromi/{{= $route.source.id}}"
														title="Полазни аеродром">{{= $route.source.city}}{{if
														$route.source.iata}} ({{= $route.source.iata}}){{else
														$route.source.icao}} ({{= $route.source.icao}}){{/if}}</a>
												</td>
											</tr>
										</table>
									</td>
									<td style="width:1%;padding: 0 5px 0 5px;">→</td>
									<td style="width:33%">
										<table style="width:100%;margin-left:5px;">
											<tr><td style="text-align:left;padding-left:5px">
													<img style="padding: 0 5px 0 5px;width:16px" src="{{= ctx}}/resources/images/countries/16/{{= $route.dest.countryCode.toLowerCase()}}.png" alt="{{= $route.dest.country}}" title="{{= $route.dest.country}}"/>
													<a href="{{= ctx}}/aerodromi/{{= $route.dest.id}}"
														title="Долазни аеродром">{{= $route.dest.city}}{{if
														$route.dest.iata}} ({{= $route.dest.iata}}){{else
														$route.dest.icao}} ({{= $route.dest.icao}}){{/if}}</a>
												</td>
											</tr>
										</table>
									</td>
									<td style="width:33%;text-align:center"><a href="{{= ctx}}/avioprevoznici/{{= $route.alid.id}}" title="Авиопревозник">{{= $route.alid.name}}</a></td>
								</tr>
							</table>
						</td>
						<td style="width: 10%;padding-right:20px">
							<table>
								<tr>
									<td style="float:left;">{{= $route.price}}</td>
									<td style=""><span title="новчана јединица">н.ј.</span></td>
								</tr>
							</table>
						</td>
					</tr>
					{{if ($i == path.routes.length-1) && (last.id != path.end.id)}}
					<tr>
						<td colspan="3" style="padding-left:2px;">
							<span style="padding-right:10px">{{= $i+2}}. Другим превозним средством {{= Maps.findDistance($route.dest.latitude, $route.dest.longitude, last.latitude, last.longitude)}} км од </span>
							<img style="padding: 0 5px 0 5px; width:16px" src="{{= ctx}}/resources/images/countries/16/{{= $route.dest.countryCode.toLowerCase()}}.png" alt="{{= $route.dest.country}}" title="{{= $route.dest.country}}"/>
							<a href="{{= ctx}}/aerodromi/{{= $route.dest.id}}" title="Полазни аеродром">{{= $route.dest.city}}{{if
														$route.dest.iata}} ({{= $route.dest.iata}}){{else
														$route.dest.icao}} ({{= $route.dest.icao}}){{/if}}</a>
							<span style="padding: 0 10px 0 10px"> до </span>
							<img style="padding: 0 5px 0 5px; width:16px" src="{{= ctx}}/resources/images/countries/16/{{= last.countryCode.toLowerCase()}}.png" alt="{{= last.country}}" title="{{= last.country}}"/>
							<a href="{{= ctx}}/aerodromi/{{= last.id}}" title="Долазни аеродром">{{= last.city}}{{if
														last.iata}} ({{= last.iata}}){{else
														last.icao}} ({{= last.icao}}){{/if}}</a>
						</td>
					</tr>
					{{/if}}
					{{/each}}
				</table>
			</td>
			<td style="text-align: right;">
				<ul style="list-style:none;line-height:1.5">
				<li>укупно {{= path.price}} <span title="новчана јединица">н.ј.</span></li> 
				<li>{{if path.length == 2}} <span>једно преседањe</span> {{else path.length > 2}} <span>{{= path.length-1}} преседања</span>
				{{else}} <span>директна линија</span> {{/if}}</li>
				<li><span>растојање {{= path.distance}} км</span></li>
				</ul>
			</td>
		</tr>
	</table>
</script>
<script id="noPathTemplate" type="text/x-jquery-tmpl">
	<h3>Пут није пронађен</h3>
	<p style="padding-bottom: 10px">Промените критеријум и поновите претрагу.</p>
</script>
<script id="altPathTemplate" type="text/x-jquery-tmpl">
	<h3>Пут није пронађен, претражујем аеродроме у близини...</h3>
</script>
<script id="infoWindowAirportTemplate" type="text/x-jquery-tmpl">
<div id="infoWindow">
	<p>Аеродром <span class="bold">{{= name}}, {{= city}}</span></p>
	<p>Држава: {{= country}}</p>
	<br/>
	<a href="{{= ctx}}/aerodromi/{{= id}}" style="color:#A82A15;text-decoration:none">Детаљан опис</a>
</div>
</script>