package com.proactive.plugin.paplicencemanager

import grails.util.Environment

class Licence {

	int acquired            //Nombre de licences acquise
	Date beginValidityDate      //Date de début de validité
	Date expiracyDate           //Date d'expiration
	boolean historized        //Historisé t/f
	static hasOne = [licenceFile: LicenceFile] //Fichier de licence lié

	static constraints = {
		beginValidityDate nullable: false //La date de début de validité de la licence ne peut pas être nulle
		expiracyDate nullable: true       //La date d’expiration de la licence peut être nulle
		historized nullable: false        //L’historisation ne peut pas être nulle
		licenceFile nullable: true        //Le fichier de licence peut être nul
	}

	static mapping = {
		table 'LICENCE'
		version false
		//id generator: 'native', params: [sequence: 'LICENCE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		//id generator: 'sequence', params: [sequence: 'LICENCE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		if (Environment.isDevelopmentMode()) {
			id generator: 'sequence', params: [sequence: 'LICENCE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		} else {
			id generator: 'native', params: [sequence: 'LICENCE_ID_SEQ', max_lo: 100] //Pour oracle utilisation d'un séquence pour incrémenter les ids
		}
	}
}

