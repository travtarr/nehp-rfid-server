// Application
window.App = Ember.Application.create({
		LOG_TRANSITIONS:true,
		rootElement:'#rfid'
		});

// Authentication
App.Auth = Em.Auth.extend({
	request: 'jquery',
	response: 'json',
	strategy: 'token',
	tokenKey: 'auth_token',
	tokenLocation: 'param',
	session: 'local-stroage',
	signInEndPoint: '/sign-in',
	signOutEndPoint: '/sign-out',
	modules: ['emberData'],
	emberData: { userModel: 'user' }		
});

// Store
App.Adapter = DS.RESTAdapter.extend({
  namespace:'api'
});
App.Store = DS.Store.extend({
  revision:12,
  adapter:App.Adapter.create({})
});