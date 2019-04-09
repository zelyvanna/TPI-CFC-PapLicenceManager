class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?(.$format)?" {
			constraints {
				// apply constraints here
			}
		}

		//"/"(view:"/index") à commenter pour empêcher la vue par défaut de s’ouvrir
		"500"(view: '/error')

		"/"(controller: 'licence') //Liens vers le contrôleur licence
	}
}
