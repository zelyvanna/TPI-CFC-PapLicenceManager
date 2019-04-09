package com.proactive.plugin.paplicencemanager

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Licence)
@Mock(LicenceFile)
class LicenceSpec extends Specification {
	LicenceFile licenceFile
	Licence licenceInvalid
	Licence licenceValidWithFile
	Licence licenceValidWithoutFile

	def setup() {
		//Fichier de licence
		licenceFile = new LicenceFile(fileContent: "Contenu du fichier de licence")

		//Licences
		//Licence incorrecte
		licenceInvalid = new Licence(acquired: null, beginValidityDate: null, expiracyDate: null, historized: null)

		//Licence correcte avec fichier lié
		licenceValidWithFile = new Licence(acquired: 10, beginValidityDate: new GregorianCalendar(2014, Calendar.FEBRUARY, 24).time, expiracyDate: null, historized: false, licenceFile: licenceFile)

		//Licence correcte avec fichier lié
		licenceValidWithoutFile = new Licence(acquired: 25, beginValidityDate: new GregorianCalendar(2015, Calendar.JUNE, 01).time, expiracyDate: new GregorianCalendar(2015, Calendar.FEBRUARY, 04).time, historized: true)
	}

	def cleanup() {
		licenceFile?.delete()
		licenceInvalid?.delete()
		licenceValidWithFile?.delete()
		licenceValidWithoutFile?.delete()
	}

	void "Creation Licence"() {
		expect:
		assertNull(licenceInvalid.save())
		assertNotNull(licenceValidWithFile.save())
		assertNotNull(licenceValidWithoutFile.save())
	}
}
