// Application
var App = Ember.Application.create({
		LOG_TRANSITIONS:true, // debugging == true
		rootElement:'body'
		});

// Authentication
App.Auth = Em.Auth.extend({
	request: 'jquery',
	response: 'json',
	strategy: 'token',
	tokenKey: 'auth_token',
	tokenLocation: 'param',
	session: 'local-stroage',
	signInEndPoint: '/login',
	signOutEndPoint: '/logout',
	modules: ['emberData', 'authRedirectable', 'rememberable'],
	emberData: { userModel: 'user' },
	authRedirectable: { route: 'login' },
	rememberable: {
		    tokenKey: 'remember_token',
		    period: 14,
		    autoRecall: false
    }

});

// Store
App.Adapter = DS.RESTAdapter.extend({
  namespace:'api'
});
App.Store = DS.Store.extend({
  revision:12,
  adapter:App.Adapter.create({})
});