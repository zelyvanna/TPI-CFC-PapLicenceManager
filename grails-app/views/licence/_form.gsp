<%@ page import="com.proactive.plugin.paplicencemanager.Licence" %>



<div class="fieldcontain ${hasErrors(bean: licence, field: 'acquired', 'error')} required">
	<label for="acquired">
		<g:message code="licence.acquired.label" default="Acquired" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="acquired" type="number" value="${licence.acquired}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: licence, field: 'beginValidityDate', 'error')} required">
	<label for="beginValidityDate">
		<g:message code="licence.beginValidityDate.label" default="Begin Validity Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="beginValidityDate" precision="day"  value="${licence?.beginValidityDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: licence, field: 'expiracyDate', 'error')} ">
	<label for="expiracyDate">
		<g:message code="licence.expiracyDate.label" default="Expiracy Date" />
		
	</label>
	<g:datePicker name="expiracyDate" precision="day"  value="${licence?.expiracyDate}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: licence, field: 'historized', 'error')} ">
	<label for="isHistorized">
		<g:message code="licence.isHistorized.label" default="Is Historized" />
		
	</label>
	<g:checkBox name="isHistorized" value="${licence?.isHistorized}" />

</div>

<div class="fieldcontain ${hasErrors(bean: licence, field: 'licenceFile', 'error')} ">
	<label for="licenceFile">
		<g:message code="licence.licenceFile.label" default="Licence File" />
		
	</label>
	<g:select id="licenceFile" name="licenceFile.id" from="${com.proactive.plugin.paplicencemanager.LicenceFile.list()}" optionKey="id" value="${licence?.licenceFile?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

