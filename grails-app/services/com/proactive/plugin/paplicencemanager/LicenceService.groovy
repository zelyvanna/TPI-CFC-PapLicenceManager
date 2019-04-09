package com.proactive.plugin.paplicencemanager

import grails.converters.XML

//import grails.transaction.Transactional
//import org.springframework.transaction.annotation.Transactional
import org.apache.commons.io.IOUtils
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.text.SimpleDateFormat

/**
 * Ce service utilise l'interface LicenceInterface afin de pouvoir fournir à l'application
 * les données concernant les licences.
 * @see LicenceInterface
 */
//@Transactional
class LicenceService {

	static transactional = true

	LicenceInterface licenceInterface

	/**
	 * Permet de vérifier si la licence actuelle (Licence non historisée) est valide à l'heure actuelle
	 * selon différent critères
	 * @return <code>true</code si la licence actuelle est valide et <code>false</code> si elle est invalide
	 */
	boolean isLicenceValid() {
		Licence licence
		try {
			//Récupération de la licence actuelle
			licence = Licence.findWhere(historized: false)
		} catch (Exception e) {
			log.error(e)
			return false
		}

		//S'il n'y a aucune licence
		if (!licence) {
			return false
		}

		// On vérifie si le nombre de licences restantes n'est pas négatif
		// Que le nombre de licences utilisées est plus petit ou égal au nombre de licences acquises
		// Que la date de début de validité est antérieure ou égale à la date du jour
		// Que la date d'expiration est postérieur ou égale à la date du jour
		Date now = new Date()
		if (remainingLicence() >= 0
			&& usedLicence() <= licence.acquired
			&& licence.beginValidityDate <= now
			&& licence.expiracyDate >= now) {
			//La licence est valide
			return true
		}
		return false
	}

	/**
	 * Calcul le nombre de licences restantes à l'aide de la méthode usedLicence()
	 * @return Le nombre de licences restantes
	 * @see LicenceService#usedLicence()
	 * @see LicenceInterface#usedLicence()
	 */
	int remainingLicence() {
		//Calcul et retourne le nombre de licences restantes
		int remainingLicences
		try {
			remainingLicences = Licence.findWhere(historized: false).acquired - usedLicence()
			return remainingLicences
		} catch (Exception e) {
			log.error(e)
		}
	}

	/**
	 * Permet de créer ou de modifier une licence en testant si ces champs
	 * correspondent aux contraintes du domaine ainsi qu'aux règles de
	 * validation logiques (date expiration postérieur à celle de début de validité, etc...)
	 * @param licence La licence à modifier/créer
	 * @return <code>true</code> si la sauvegarde a bien été effectuée et <code>false</code> si elle a échouée
	 */
	boolean updateLicence(Licence licence) {
		//Vérifie les champs de la licence et Update la licence
		//annulation des modifs si un champs n'est pas valide
		//return true : reussite save false : echec save

		if (!licence) {
			return false
		}

		//check date
		if (licence.beginValidityDate > licence.expiracyDate) {
			log.error("La date de début de validité ne peut pas être plus grande que la date d'expiration")
			licence.discard()
			return false
		}

		//check Acquired
		if (licence.acquired < 1) {
			log.error("Le nombre de licences acquises doit être supérieur à 0")
			licence.discard()
			return false
		}

		//Historized test
		if (licence.expiracyDate < new Date() && !licence.historized) {
			log.error("Une licence qui a expirée ne peut pas être active")
			licence.discard()
			return false
		}
		return licence.save(flush: true, failOnError: true)
	}

	/**
	 * Historise la ou les licences actuelle(s)
	 * @return success <code>true</code> si l'historisation a bien été effectuée et <code>false</code> le cas échéant
	 */
	boolean historizeAll() {
		boolean success = true

		//Récupération de toutes les licences actuelles
		List<Licence> lstLicence = Licence.findAllWhere(historized: false)
		lstLicence.each {
			it.setHistorized(true)
			//Si une des sauvegarde ne s'effectue pas correctement on assigne la valeur false à la variable success
			if (!it.save(flush: true, failOnError: true)) {
				success = false
			}
		}
		return success
	}

	/**
	 * Permet de créer une licence à partir d'un fichier de licence uploadé
	 * Récupère le contenu et les champs du fichier XML et les attribue à
	 * une nouvelle licence pour finalement la créer avec la méthode updateLicence()
	 * Crée également un LicenceFile avec le contenu du fichier uploadé
	 * @param uploadedFile Fichier CommonsMultipart uploadé contenant la licence
	 * @see LicenceService#updateLicence
	 */
	void uploadLicenceFile(CommonsMultipartFile uploadedFile) {
		// Permet de récupérer le contenu du fichier de licence et de créer une Licence et un fichier de licence

		//Valeurs récupérées du fichier de licence
		String licenceFileContent //Contenu du fichier de licence
		int acquired //nbrLicence acquise
		Date beginValidityDate  //Date de début de validité
		Date expiracyDate //Date d'expiration

		//Récupération du contenu du fichier de licence
		if (uploadedFile && !uploadedFile.empty) {
			ByteArrayInputStream stream = new ByteArrayInputStream(uploadedFile.getBytes())
			licenceFileContent = IOUtils.toString(stream, "UTF-8")
			log.debug(licenceFileContent)
		} else {
			log.error("Aucun fichier de licence")
		}

		//Lecture du contenu du fichier de licence
		if (licenceFileContent) {
			try {
				//Décodage
				String licenceFileContentDecoded = licenceFileContent.decodeBlowfish()
				log.debug(licenceFileContentDecoded)

				//Lecture du contenu du String
				def rootNode = XML.parse(licenceFileContentDecoded)

				//Attribution du contenu du fichier de licence aux variables
				acquired = Integer.parseInt(rootNode.acquired.text())
				beginValidityDate = new SimpleDateFormat('yyyy-MM-dd').parse(rootNode.beginValidityDate.text())
				expiracyDate = new SimpleDateFormat('yyyy-MM-dd').parse(rootNode.expiracyDate.text())

				//Affichage du contenu récupéré
				log.debug("Licences acquises : " + acquired.toString())
				log.debug("Début validité : " + beginValidityDate.toString())
				log.debug("Expiration : " + expiracyDate.toString())

				//Création du fichier de licence et assignation de son contenu
				LicenceFile licenceFile = new LicenceFile()
				licenceFile.setFileContent(licenceFileContent)
				if (!licenceFile.save(flush: true, failOnError: true)) {
					log.error("Impossible de sauvegarder le fichier de licence")
				} else {
					log.debug("Le fichier de licence a bien été créé")
					//Historisation de la ou des licence(s) active(s)
					historizeAll()

					//Création de la nouvelle licence à partir des valeurs du fichier de licence
					Licence licence = new Licence()
					licence.setAcquired(acquired)
					licence.setLicenceFile(licenceFile)
					licence.setBeginValidityDate(beginValidityDate)
					licence.setExpiracyDate(expiracyDate)
					licence.setHistorized(false)

					//Validation et sauvegarde de la licence
					if (!updateLicence(licence)) {
						log.error("Impossible de sauvegarder la nouvelle licence")
					} else {
						log.debug("Licence ajoutée à partir du fichier de licence")
					}
				}
			} catch (Exception e) {
				log.error("Une erreur est survenue lors de l'upload du fichier de licence : " + e)
				log.error("Veuillez vérifier le format et le contenu du fichier de licence")
			}
		} else {
			log.error("Le fichier de licence n'as pas pu être lu ou est vide")
		}
	}

	/**
	 * Permet d'afficher les information de la licence actuelle
	 * @return licenceInfo Objet LicenceInfo contenant les information de la licence actuelle
	 * @see LicenceInfo
	 */
	LicenceInfo showLicenceInfo() { //Retourne un objet licenceInfo contenant les information de la licence active
		LicenceInfo licenceInfo = null
		try {
			licenceInfo = new LicenceInfo(activeLicence: Licence.findWhere(historized: false), isLicenceValid: isLicenceValid(), usedLicence: usedLicence(), remainingLicence: remainingLicence())
		} catch (Exception e) {
			log.debug("Aucune licence active")
		}
		return licenceInfo
	}

	/**
	 * Retourne le nombre de licence utilisées à l'aide de la méthode fournie par l'interface
	 * @return licenceInterface.usedLicence ( ) le nombre de licences utilisées
	 * @see LicenceInterface#usedLicence()
	 */
	int usedLicence() { //Affiche le nombre de licences utilisées
		return licenceInterface.usedLicence()
	}

	/**
	 * Créer un fichier de licence en xml encodé à partir des champs de créations
	 * @param acquired
	 * @param beginValidityDate
	 * @param expiracyDate
	 * @return content.encodeAsBlowfish ( ) Licence en XML encodé
	 */
	String createLicenceXmlEncoded(String acquired, Date beginValidityDate, Date expiracyDate) {
		//Création d'une licence
		Licence licence = new Licence(acquired: acquired, beginValidityDate: beginValidityDate, expiracyDate: expiracyDate)
		//Conversion de la licence au format XML
		String content = licence as XML
		log.debug(content)
		//Retourne la licence au format XML encodé
		return content.encodeAsBlowfish()
	}
}
