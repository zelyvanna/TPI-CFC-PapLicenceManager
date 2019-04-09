import com.proactive.plugin.paplicencemanager.Licence
import com.proactive.plugin.paplicencemanager.User
import com.proactive.plugin.paplicencemanager.LicenceFile

class BootStrap {
	def init = { servletContext ->
		// code d'initialisation

		//Fichier de Licences
		def licenceFile = new LicenceFile(fileContent: "Contenu du fichier de licence").save(failOnError: true)

		//Licences

		//Licence invalide sans fichier lié
		def licenceInvalidWithoutFile = new Licence(acquired: 5, beginValidityDate: new GregorianCalendar(2013, Calendar.FEBRUARY, 21).time, expiracyDate: new GregorianCalendar(2014, Calendar.MARCH, 14).time, historized: true).save(failOnError: true)

		//Licence valide avec fichier lié
		def licenceValidWithFile = new Licence(acquired: 10, beginValidityDate: new GregorianCalendar(2014, Calendar.FEBRUARY, 24).time, expiracyDate: new GregorianCalendar(2020, Calendar.DECEMBER, 11).time, historized: false, licenceFile: licenceFile).save(failOnError: true)

		//Licence invalide avec fichier lié
		def licenceInvalidWithFile = new Licence(acquired: 25, beginValidityDate: new GregorianCalendar(2015, Calendar.JANUARY, 01).time, expiracyDate: new GregorianCalendar(2015, Calendar.FEBRUARY, 04).time, historized: true, licenceFile: licenceFile).save(failOnError: true)


		def userWithLicenceEnabled = new User(login: "prav",password: "123", enable: true).save(failOnError: true)

		def userWithLicenceDisabled = new User(login: "Philippe", password: "strongestPass", enable: false).save(failOnError: true)



		println("Affichage des données tests")

		println('----- LICENCES -----')

		Licence.getAll().each {
			println('-----')
			println("id : " + it.id)
			println('-----')
			println("acquired : " + it.acquired)
			println("beginValidityDate : " + it.beginValidityDate)
			println("expiracyDate : " + it.expiracyDate)
			println("historized : " + it.historized)
		}

		println('----- FICHIERS DE LICENCES -----')

		LicenceFile.getAll().each {
			it.refresh()
			println('-----')
			println("id : " + it.id)
			println('-----')
			println("fileContent : " + it.fileContent)
			println('-----')
			println('Linked licences: ' + it.licences)
		}

		println('----- Users -----')

		User.getAll().each {
			println('-----')
			println("id : " + it.id)
			println('-----')
			println("login : " + it.login)
			println("enable : " + it.enable)
		}

		log.info("Application is up and running")
	}
}