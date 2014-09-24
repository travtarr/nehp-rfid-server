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
	session: 'local-storage',
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

// Adapter
App.ApplicationAdapter = DS.RESTAdapter.extend({});

// Store
App.Store = DS.Store.extend({
  revision:12,
  adapter: App.ApplicationAdapter.create()
});