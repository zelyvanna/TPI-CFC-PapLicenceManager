package com.proactive.plugin.paplicencemanager
import org.springframework.web.multipart.commons.CommonsMultipartFile


class LicenceController {

	def licenceService

	def index() {
		return render(view: 'index', model: [activeLicenceInfo: licenceService?.showLicenceInfo()])
	}

	def select() {
		return render(view: 'index', model: [id: params.id, activeLicenceInfo: licenceService?.showLicenceInfo()])
	}

	def edit() {
		return render(view: 'index', model: [edit: true, id: params.id, activeLicenceInfo: licenceService?.showLicenceInfo()])
	}

	def addUser() {
		User user = new User(login: params.login, password: params.password, enable: params.isEnabled)
		user.save(flush: true, failOnError: true)
		redirect(action: 'index')
	}

	def enableUser() {
		if (params?.id) {
			if (User.get(params.id).enable) {
				User.get(params.id).setEnable(false)
			} else {
				User.get(params.id).setEnable(true)
			}
		}
		redirect(action: 'index')
	}

	def saveLicence() {
		//Récupération des paramètres et de la licence et changement de ses champs
		Licence licence = Licence.get(params.id)
		licence.setAcquired(Integer.parseInt(params.acquired))
		licence.setBeginValidityDate(params.beginValidityDate)
		licence.setExpiracyDate(params.expiracyDate)
		licence.setHistorized(Boolean.parseBoolean(params.isHistorized))

		//Sauvegarde de la licence
		if (!licenceService.updateLicence(licence)) {
			//Message d'erreur si la sauvegarde échoue
			log.error("Erreur lors de la sauvegarde de la licence, opération annulée")
		}
		redirect(action: 'index')
	}

	def uploadLicenceFile() {
		//Récupération du fichier multipart uploadé et appel de la méthode upload du licenceService0
		CommonsMultipartFile licenceFile = request.getFile('licenceFile')
		licenceService.uploadLicenceFile(licenceFile)
		redirect(action: 'index')
	}

	def createLicenceFile() {
		//Récupération des paramètres
		String acquired = params.acquired
		Date beginValidityDate = params.beginValidityDate
		Date expiracyDate = params.expiracyDate

		//Création du contenu de licence en xml et encodé blowfish
		String xmlEncoded = licenceService.createLicenceXmlEncoded(acquired, beginValidityDate, expiracyDate)

		//Lancement download
		//Envoi à la page des informations du fichier et download
		response.setHeader("Content-Disposition", "attachment; filename=licenceFile.xml")
		response.setContentType("text/xml")
		OutputStream outputStream = response.getOutputStream()
		outputStream.write(xmlEncoded.getBytes())
		outputStream.flush()
		outputStream.close()
	}

}

