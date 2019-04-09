package com.proactive.plugin.paplicencemanager

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LicenceFile)
class LicenceFileSpec extends Specification {
	LicenceFile licenceFileBlank
	LicenceFile licenceFileNull
	LicenceFile licenceFile

	def setup() {
		licenceFileBlank = new LicenceFile(fileContent: "")
		licenceFileNull = new LicenceFile(null)
		licenceFile = new LicenceFile(fileContent: "uzfktfdgvligilghuigkuzfgkjvfjkfgvfuj")
	}

	def cleanup() {
		licenceFileBlank?.delete()
		licenceFileNull?.delete()
		licenceFile?.delete()
	}

	void "Creation LicenceFile"() {
		expect:
		assertNull(licenceFileBlank.save())
		assertNull(licenceFileNull.save())
		assertNotNull(licenceFile.save())
	}
}
