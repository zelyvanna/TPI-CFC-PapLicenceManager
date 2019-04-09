package com.proactive.plugin.paplicencemanager

class User {

	String login      //login de l'utilisateur
	String password   //Mot de passe de l'utilisateur
	boolean enable //variable déterminant si une licence est active sur cet utilisateur

	static constraints = {
		login maxSize: 45, blank: false, nullable: false //Le login ne peut pas être vide, nul, dépasser 45 caractères
		password maxSize: 45, blank: false, nullable: false //Le mot de passe ne peut pas être vide, nul, dépasser 45 caractères
	}
}
