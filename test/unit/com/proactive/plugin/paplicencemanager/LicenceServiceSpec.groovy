package com.proactive.plugin.paplicencemanager

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.commons.fileupload.FileItem
import org.springframework.web.multipart.commons.CommonsMultipartFile
import spock.lang.Specification

//import org.springframework.transaction.annotation.Transactional

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
//@Transactional
@TestFor(LicenceService)
@Mock([Licence, LicenceFile, User])
class LicenceServiceSpec extends Specification {
	LicenceService licenceService
	Licence licenceValidWithFile
	LicenceFile licenceFile
	CommonsMultipartFile file
	def userLicenceService

	def setup() {
		licenceService = new LicenceService()
		licenceService.licenceInterface = userLicenceService

		licenceFile = new LicenceFile(fileContent: "Contenu du fichier de licence").save()
		licenceValidWithFile = new Licence(acquired: 10, beginValidityDate: new GregorianCalendar(2014, Calendar.FEBRUARY, 24).time, expiracyDate: new GregorianCalendar(2020, Calendar.DECEMBER, 11).time, historized: false, licenceFile: licenceFile).save()
	}

	def cleanup() {
		licenceValidWithFile?.delete()
	}

	void "historizeAllTest"() {
		//Historisation donc aucune licence non historisée restantes
		expect:
		assertTrue(licenceService.historizeAll())
	}

	void "showLicenceInfoVideTest"() {
		//Historisation de toutes licences donc aucune infos
		when:
		licenceService.historizeAll()
		then:
		assertNull(licenceService.showLicenceInfo())
	}

	void "uploadLicenceFile Invalid"() {

		//Plus de licence en cours
		when:
		file = new CommonsMultipartFile(Mock(FileItem))
		licenceService.uploadLicenceFile(file)
		then:
		assertNull(licenceService.showLicenceInfo())
	}
}
