package com.proactive.plugin.paplicencemanager

/**
 * Created by vincent.praz on 03.03.2017.
 */

/**
 * Défini un objet de type LicenceInfo contenant
 * les champs nécessaires à l'affichage des
 * information principales d'une licence.
 */
class LicenceInfo {
	Licence activeLicence //Objet Licence contenant la licence active
	boolean isLicenceValid //boolean contenant la validité de la licence (valide/invalide)
	int usedLicence //Le nombre de licences utilisées
	int remainingLicence //Le Nombre de licences restantes
}
