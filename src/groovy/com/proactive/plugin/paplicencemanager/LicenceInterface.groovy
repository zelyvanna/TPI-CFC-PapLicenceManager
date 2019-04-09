package com.proactive.plugin.paplicencemanager

/**
 * Created by vincent.praz on 02.03.2017.
 */

/**
 * Cette interface sert à fournir les méthodes nécessaires
 * au LicenceService ainsi qu'au Service de l'application
 * gérant les objets qui consommeront la licence
 * @see LicenceService
 */
interface LicenceInterface {
	/**
	 * Méthode retournant le nombre de licences utilisées après
	 * calcul de cette valeur
	 * @return Retourne le nombre de licences utilisées
	 */
	int usedLicence()   //Affiche le nombre de licences utilisées
}