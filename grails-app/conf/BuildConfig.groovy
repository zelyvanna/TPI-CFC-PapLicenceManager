grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.fork = [
	// configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
	//  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

	// configure settings for the test-app JVM, uses the daemon by default
	test   : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
	// configure settings for the run-app JVM
	run    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
	// configure settings for the run-war JVM
	war    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
	// configure settings for the Console UI JVM
	console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	repositories {
		grailsCentral()

		// our and 3rd party plugins
		mavenRepo "http://svn.sierre.proactive-partners.ch:8081/artifactory/plugins-release-local"
		// our code releases only
		mavenRepo "http://svn.sierre.proactive-partners.ch:8081/artifactory/libs-release-local"
		// 3rd party releases only
		mavenRepo "http://svn.sierre.proactive-partners.ch:8081/artifactory/ext-release-local"

		mavenLocal()
		mavenCentral()
		// uncomment the below to enable remote dependency resolution
		// from public Maven repositories
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
	}
	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		// runtime 'mysql:mysql-connector-java:5.1.27'
	}

	plugins {
		build(":release:3.0.1",
			":rest-client-builder:1.0.3") {
			export = false
		}

		// plugins for the build system only
		build ":tomcat:7.0.54"

		// plugins needed at runtime but not for compilation
		runtime(":hibernate:3.6.10.17") {
			excludes "ehcache-core"
		}

		// plugins for the compile step
		compile ":scaffolding:2.0.2"
		compile ':cache:1.1.1'

		// required plugin for cached-resources
		runtime ":cache-headers:1.1.6"

		// plugin for caching data
		compile ":cache-ehcache:1.0.1"
		compile ":scoped-proxy:0.2"

		//Plugins Proactive
		compile ":sql-maintenance:16.6.0"
		compile ":pap-crypto:15.11.1"

	}

	grails.project.dependency.distribution = {
		remoteRepository(id: "pap-plugins", url: "http://svn.sierre.proactive-partners.ch:8081/artifactory/plugins-release-local") {
			authentication username: "grails", password: 'Pr0C1v$'
		}
	}
}
