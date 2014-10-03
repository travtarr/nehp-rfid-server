Auth.SecretRoute = Auth.AuthenticatedRoute.extend({
	model : function() {
		// instantiate the model for the SecretController as a list of created
		// users
		return this.store.find('user');
	}
});