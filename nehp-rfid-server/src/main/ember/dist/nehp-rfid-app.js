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
});;/**
 * Configuration file
 */

var config  = {
	baseURL: "localhost"
};;App.Controller = Ember.ArrayController.extend({
	user: function() {
		return App.Auth.get('user');
	}
});;App.Item = DS.Model.extend({
    rfid: DS.attr('string'),
    itemId: DS.attr('string'),
    description: DS.attr('string'),
    lastStatusChangeDate: DS.attr('string'),
    currentStage: DS.attr('string'),
    currentRevision: DS.attr('string')
});;App.Session = DS.Model.extend({
  authToken: DS.attr('string'),
  account:   DS.belongsTo('App.Account')
});;App.User = DS.Model.extend({
  name: DS.attr('string'),
  admin: DS.attr('int')
});;/**
 * 
 */;/**
 * 
 */
App.MainListRoute = Ember.Route.extend({
  model: function() {
    var listObjects = [];
    Ember.$.getJSON(config.baseURL + '/list', function(lists) {
      lists.forEach(function(data) {
        listObjects.pushObject(App.Item.createRecord(data));
      });
    });
    return listObjects;
  }
});;/**
 * 
 */;/**
 * 
 */;/**
 * 
 */;;App.Router.map(function() {
  this.route('index', { path: '/' });
  this.resource("main", { path:"/main" }, function () {  
    this.route("summary", { path:"/summary" }); 
    this.resource("list", { path:"/list" }, function() {
      this.route("item", { path:"/:item" });
    });
    this.route("user", { path:"/user" });
    this.route("admin", { path:"/admin" });
    this.route("login", { path:"/login" });
  });
});