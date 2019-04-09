package com.proactive.plugin.paplicencemanager

import grails.transaction.Transactional

/**
 * Gère les utilisateurs qui vont consommer les licences
 * @implements LicenceInterface
 * @see LicenceInterface
 */
@Transactional
class UserLicenceService implements LicenceInterface {

	/**
	 * Retourne le nombre d'utilisateurs utilisant la licence (actifs)
	 * @return User.countByIsEnabled ( true ) le nombre d'utilisateurs actifs
	 */
	@Override
	int usedLicence() {
		return User.countByEnable(true)
	}
}
