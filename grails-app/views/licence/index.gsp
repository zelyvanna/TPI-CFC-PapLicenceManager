<%@ page import="com.proactive.plugin.paplicencemanager.Licence" %>
<%@ page import="com.proactive.plugin.paplicencemanager.User" %>


<link rel="stylesheet" href="${resource(dir: 'css', file: 'licence.css')}" type="text/css" />
<!DOCTYPE html>
<html>
	<g:if test="${id}">
		<g:set var="selectedLicenceId" value="${id}" />
	</g:if><g:else>
		<g:set var="selectedLicenceId" value="1" />
	</g:else>
	<g:set var="selectedLicence" value="${Licence.get(selectedLicenceId)}" />


	<head>
		<title>LicenceManager</title>
	</head>

	<body>

		<div class="title">
			<h1>LicenceManager</h1>
		</div>

		<div class="block">
			<div class="block-title">
				<h1>Liste</h1>
			</div>
			<table>
				<thead>
					<tr>
						<th><g:message code="licence.acquired.label" default="Acquired" /></th>

						<th><g:message code="licence.beginValidityDate.label" default="Begin Validity Date" /></th>

						<th><g:message code="licence.expiracyDate.label" default="Expiracy Date" /></th>

						<th><g:message code="licence.isHistorized.label" default="Is Historized" /></th>

						<th><g:message code="licence.licenceFile.label" default="Licence File" /></th>

					</tr>
				</thead>

				<tbody>

					<g:set var="licenceList" value="${Licence.getAll()}" />

					<g:each in="${licenceList}" status="i" var="licence">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

							<td><g:link action="select" id="${licence.id}">${fieldValue(bean: licence, field: "acquired")}</g:link></td>

							<td><g:formatDate format="dd-MM-yyyy" date="${licence.beginValidityDate}" /></td>

							<td><g:formatDate format="dd-MM-yyyy" date="${licence.expiracyDate}" /></td>

							<td><g:formatBoolean boolean="${licence.historized}" /></td>

							<td>${fieldValue(bean: licence, field: "licenceFile.id")}</td>

						</tr>
					</g:each>
				</tbody>
			</table>

		</div>

		<div class="block">
			<div class="block-title">
				<h1>Modifications de la licence :  ${selectedLicenceId}</h1>
			</div>
			<table>
				<thead>
					<tr>

						<th><g:message code="licence.acquired.label" default="Acquired" /></th>

						<th><g:message code="licence.beginValidityDate.label" default="Begin Validity Date" /></th>


						<th><g:message code="licence.expiracyDate.label" default="Expiracy Date" /></th>

						<th><g:message code="licence.isHistorized.label" default="Is Historized" /></th>

					</tr>
				</thead>

				<tbody>
				<tr>

					<g:if test="${edit}">
						<g:form action="saveLicence">
							<input type="hidden" name="id" value="${selectedLicence.id}" />
							<td><input required="required" type="text" name="acquired" value="${selectedLicence.acquired}" /></td>

							<td><g:datePicker name="beginValidityDate" value="${selectedLicence.beginValidityDate}" precision="day" /></td>

							<td><g:datePicker name="expiracyDate" value="${selectedLicence.expiracyDate}" precision="day" /></td>

							<td>
								<select name="isHistorized">
									<option selected="selected" value="${selectedLicence.historized}">${selectedLicence.historized}</option>
									<option value="${!selectedLicence.historized}">${!selectedLicence.historized}</option>
								</select>
							</td>
							<tr><td><input type="submit" /><g:actionSubmit value="Annuler" action="index" /></td></tr>
						</g:form>
					</g:if><g:else>
					<td>${selectedLicence.acquired}</td>

					<td><g:formatDate format="dd-MM-yyyy" date="${selectedLicence.beginValidityDate}" /></td>

					<td><g:formatDate format="dd-MM-yyyy" date="${selectedLicence.expiracyDate}" /></td>

					<td><g:formatBoolean boolean="${selectedLicence.historized}" /></td>

					<td><g:link action="edit" id="${selectedLicence.id}" name="edit">Edit</g:link></td>
				</g:else>

				</tr>
				</tbody>
			</table>
		</div>


		%{--<br />--}%

		<div class="block">
			<div class="block-title">
				<h1>Informations sur la licence en cours</h1>
			</div>
			<g:if test="${activeLicenceInfo}">
				<table>
					<tr>
						<td>Id</td>
						<td>: ${activeLicenceInfo.activeLicence.id}</td>
					</tr>
					<tr>
						<td>Valid</td>
						<td>: ${activeLicenceInfo.isLicenceValid}</td>
					</tr>
					<tr>
						<td>Acquired</td>
						<td>: ${activeLicenceInfo.activeLicence.acquired}</td>
					</tr>
					<tr>
						<td>Used</td>
						<td>: ${activeLicenceInfo.usedLicence}</td>
					</tr>

					<tr>
						<td>Remaining</td>
						<td>: ${activeLicenceInfo.remainingLicence}</td>
					</tr>

					<tr>
						<td>Begin Validity Date</td>
						<td>: <g:formatDate format="dd-MM-yyyy" date="${activeLicenceInfo.activeLicence.beginValidityDate}" /></td>
					</tr>
					<tr>
						<td>Expiracy</td>
						<td>: <g:formatDate format="dd-MM-yyyy" date="${activeLicenceInfo.activeLicence.expiracyDate}" /></td>
					</tr>

				</table>
			</g:if><g:else>
			<table><tr><td>Aucune Licence Active</td></tr></table>
		</g:else>
		</div>

		<div class="block">
			<div class="block-title">
				<h1>Utilisateurs</h1>
			</div>

			<table>
				<thead>
					<tr>

						<th><g:message code="user.login.label" default="Login" /></th>

						<th><g:message code="user.password.label" default="Password" /></th>

						<th><g:message code="user.isEnabled.label" default="isEnabled" /></th>

					</tr>
				</thead>

				<tbody>

					<g:set var="userList" value="${User.getAll()}" />

					<g:each in="${userList}" status="i" var="user">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

							<td>${user.login}</td>

							<td>${user.password}</td>

							<td><g:formatBoolean boolean="${user.enable}" /></td>
							<td>
								<g:link action="enableUser" id="${user.id}">
									<g:if test="${user.enable}">
										Disable
									</g:if><g:else>
									Enable
								</g:else>
								</g:link>
							</td>

						</tr>
					</g:each>

					<tr>
						<g:form action="addUser">
							<td><input required="required" type="text" name="login" /></td>
							<td><input required="required" type="text" name="password" /></td>
							<td>
								<select name="isEnabled">
									<option selected="selected" value="true">True</option>
									<option value="false">false</option>
								</select>
							</td>
							<td><input type="submit" value="Ajouter" /></td>
						</g:form>
					</tr>
				</tbody>
			</table>

		</div>

		<br />

		<div class="block">
			<div class="block-title">
				<h1>Upload</h1>
			</div>
			<g:uploadForm action="uploadLicenceFile" name="licenceFileForm">
				<p><input style="margin-left: 5px; padding-right: 5px" type="file" name="licenceFile" /></p>

				<p><input style="margin-left: 5px;" type="submit" value="Upload" /></p>
			</g:uploadForm>
		</div>

		<div class="block">
			<div class="block-title">
				<h1>Création de fichier de licence encodé</h1>
			</div>

			<g:form action="createLicenceFile">
				<table>
					<tr>
						<td>Acquired :</td><td><input type="text" required name="acquired" /></td>
					</tr>
					<tr><td>Begin validity :</td><td><g:datePicker name="beginValidityDate" value="${selectedLicence.beginValidityDate}" precision="day" /></td></tr>
					<tr>
						<td>Expiracy :</td><td><g:datePicker name="expiracyDate" value="${selectedLicence.expiracyDate}" precision="day" /></td>
					</tr>

				</table>
				<p id="buttonCenter">
				<g:submitButton style="text-align: center" name="submit" value="Encoder" />
				<br />
			</g:form>
		</div>

		<div id="footer">
			<p>DEMO</p>
		</div>

	</body>
</html>
