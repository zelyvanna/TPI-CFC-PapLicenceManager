// configuration for plugin testing - will not be included in the plugin zip

log4j = {
	// Example of changing the log pattern for the default console
	// appender:

	def gbPatternString = '%d{dd.MM.yy HH:mm:ss,SSS} [%5p] %-40.40c{2} %m%n'

	appenders {
		console name: 'stdout', layout: pattern(conversionPattern: gbPatternString)
	}

	root {
		info('stdout')
		additivity = false
	}

	trace 'com.proactive'

	info "SqlMaintenanceGrailsPlugin",
		"com.proactive.mnt"

	debug "grails.app.conf"

	error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
		'org.codehaus.groovy.grails.web.pages', //  GSP
		'org.codehaus.groovy.grails.web.sitemesh', //  layouts
		'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
		'org.codehaus.groovy.grails.web.mapping', // URL mapping
		'org.codehaus.groovy.grails.commons', // core / classloading
		'org.codehaus.groovy.grails.plugins', // plugins
		'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
		'org.springframework',
		'org.hibernate',
		'net.sf.ehcache.hibernate'
}
//Disable MNT when run in standalone mode
mnt.deactivateAutoUpdate = true

papLicence.customLicenceService='userLicenceService'