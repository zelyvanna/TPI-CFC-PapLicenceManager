
<%@ page import="com.proactive.plugin.paplicencemanager.Licence" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'licence.label', default: 'Licence')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-licence" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-licence" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list licence">
			
				<g:if test="${licence?.acquired}">
				<li class="fieldcontain">
					<span id="acquired-label" class="property-label"><g:message code="licence.acquired.label" default="Acquired" /></span>
					
						<span class="property-value" aria-labelledby="acquired-label"><g:fieldValue bean="${licence}" field="acquired"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${licence?.beginValidityDate}">
				<li class="fieldcontain">
					<span id="beginValidityDate-label" class="property-label"><g:message code="licence.beginValidityDate.label" default="Begin Validity Date" /></span>
					
						<span class="property-value" aria-labelledby="beginValidityDate-label"><g:formatDate date="${licence?.beginValidityDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${licence?.expiracyDate}">
				<li class="fieldcontain">
					<span id="expiracyDate-label" class="property-label"><g:message code="licence.expiracyDate.label" default="Expiracy Date" /></span>
					
						<span class="property-value" aria-labelledby="expiracyDate-label"><g:formatDate date="${licence?.expiracyDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${licence?.isHistorized}">
				<li class="fieldcontain">
					<span id="isHistorized-label" class="property-label"><g:message code="licence.isHistorized.label" default="Is Historized" /></span>
					
						<span class="property-value" aria-labelledby="isHistorized-label"><g:formatBoolean boolean="${licence?.isHistorized}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${licence?.licenceFile}">
				<li class="fieldcontain">
					<span id="licenceFile-label" class="property-label"><g:message code="licence.licenceFile.label" default="Licence File" /></span>
					
						<span class="property-value" aria-labelledby="licenceFile-label"><g:link controller="licenceFile" action="show" id="${licence?.licenceFile?.id}">${licence?.licenceFile?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:licence, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${licence}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
