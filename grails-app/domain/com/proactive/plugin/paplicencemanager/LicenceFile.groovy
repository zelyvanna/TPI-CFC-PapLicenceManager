package com.proactive.plugin.paplicencemanager

import grails.util.Environment

class LicenceFile {
	String fileContent  //Contenu du fichier de licence
	static hasMany = [licences: Licence]

	static constraints = {
		fileContent maxSize: 4000, blank: false, nullable: false //Le contenu du fichier ne doit pas être nul et ne doit pas être vide
	}

	static mapping = {
		table 'LICENCE_FILE'
		version false
		//id generator: 'native', params: [sequence: 'LICENCE_FILE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		//id generator: 'sequence', params: [sequence: 'LICENCE_FILE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		if (Environment.isDevelopmentMode()) {
			id generator: 'sequence', params: [sequence: 'LICENCE_FILE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		} else {
			id generator: 'native', params: [sequence: 'LICENCE_FILE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		}
	}
}
