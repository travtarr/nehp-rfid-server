App.AdminRoute = Ember.Route.extend({
	// verify if the token property of the sessions controller is set before
	// continuing with the request
	// if it is not, redirect to the home page
	beforeModel : function(transition) {
		if (Ember.isEmpty(this.controllerFor('sessions').get('admin'))) {
			return this.redirectToHome(transition);
		}
		if (Ember.isEmpty(this.controllerFor('sessions').get('token'))) {
			return this.redirectToLogin(transition);
		}
	},
	// Redirect to the home page
	redirectToHome : function(transition) {
		return this.transitionTo('index');
	},
	redirectToLogin : function(transition) {
		this.controllerFor('sessions').set('attemptedTransition', transition);
		return this.transitionTo('sessions');
	},
	actions : {
		// recover from any error that may happen during the transition to this
		// route
		error : function(reason, transition) {
			// if the HTTP status is 401 (unauthorized), redirect to the home
			// page
			if (reason.status === 401) {
				this.redirectToHome(transition);
			} else {
				console.log('unknown problem');
			}
		}
	}
});