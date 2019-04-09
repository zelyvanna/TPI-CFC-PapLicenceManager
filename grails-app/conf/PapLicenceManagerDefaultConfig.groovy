

/* Target version to go to */
mnt.component.PapLicenceManager.componentTargetVersion = '0.0.0'

/* List all the metadata required by this component in the form 'metadataName' : 'metadataValue' */
mnt.component.PapLicenceManager.metadata = [:]

/* Initial version number for this component.
Can be used when extracting a component into a plugin (ex: PapLocalization).

This parameter can be override in the external application config file (ie GestStar-config.groovy or
Pronova-prod-config.groovy) so that you can personnalize the initialization of the component for each installations. */
mnt.component.PapLicenceManager.initialComponentVersion = '0.0.0'

/* By default the components are automaticaly updated in the 'doWithApplicationContext' of the MNT plugin

If you need more controls on the update process you can do it 'manually' in your BootStrap.
In this case you have to set the following parameter to true.
 */
mnt.component.PapLicenceManager.manualUpdateInBootStrap = false

/* List of the component to load before this one */
mnt.component.PapLicenceManager.loadAfter = ['SqlMaintenance']