App.IndexRoute = App.AuthenticatedRoute.extend({
  model: function() {
    return this.store.find('notification');
  },
  actions: {
	  // recover from any error that may happen during the transition to this
	  // route
	  error: function(reason, transition) {
		  // if the HTTP status is 401 (unauthorized), redirect to the login
		  // page
	  if (reason.status === 401) {
		  this.controllerFor('sessions').reset();
		  return this.transitionTo('sessions');
	  } else {
		  console.log(reason.status);
	  }
  }
  }
});