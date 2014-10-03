# create a base object for any authentication protected route with the required verifications
// create a base object for any authentication protected route with the required
// verifications
App.AuthenticatedRoute = Ember.Route.extend({
  // verify if the token property of the sessions controller is set before
	// continuing with the request
  // if it is not, redirect to the login route (sessions)
  beforeModel: function(transition) {
    if (Ember.isEmpty(this.controllerFor('sessions').get('token'))) {
      return this.redirectToLogin(transition);
    }
  },
  // Redirect to the login page and store the current transition so we can
  // run it again after login
  redirectToLogin: function(transition) {
    this.controllerFor('sessions').set('attemptedTransition', transition);
    return this.transitionTo('sessions');
  },
  actions: {
	    // recover from any error that may happen during the transition to this
		// route
	    error: function(reason, transition) {
	      // if the HTTP status is 401 (unauthorised), redirect to the login
			// page
	      if (reason.status === 401) {
	        this.redirectToLogin(transition);
	      } else {
	        console.log('unknown problem');
	      }
	    }
	  }
});