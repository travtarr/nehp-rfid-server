window.App = Ember.Application.create();
App.Auth = Em.Auth.extend({
	request: 'jquery',
	response: 'json',
	strategy: 'token',
	tokenKEy: 'auth_token',
	tokenLocation: 'param',
	session: 'cookie',
	signInEndPoint: '/sign-in',
	signOutEndPoint: '/sign-out',
	modules: ['emberData'],
	emberData: { userModel: 'member' }		
});