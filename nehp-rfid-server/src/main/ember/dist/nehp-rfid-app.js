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
});;/**
 * Configuration file
 */

var config  = {
	baseURL: "54.209.176.186"
};;App.Controller = Ember.ArrayController.extend({
	user: function() {
		return App.Auth.get('user');
	}
});;App.LoginController = Ember.Controller.extend({
	userID: null,
	password: null,
	remember: false,
	actions: {
	  login: function() {
	    App.Auth.signIn({
	      data: {
			userID: this.get('userID'),
			password: this.get('password'),
			remember: this.get('remember')
		  }
	    });
	  }
    }
});;;App.Item = DS.Model.extend({
    rfid: DS.attr('string'),
    itemId: DS.attr('string'),
    description: DS.attr('string'),
    lastStatusChangeDate: DS.attr('date'),
    currentStage: DS.attr('string'),
    currentRevision: DS.attr('string')
});;App.Item.FIXTURES = [
  { id: 1,
	rfid: "232452432",
	itemId: "232BS4095",
	description: "HPN2 A54 4in",
	lastStatusChangeDate: 2014/09/23,
    currentStage: "modeling",
    currentRevision: "01B"
  },
  { id: 2,
	rfid: "232452434",
	itemId: "232BS5395",
	description: "HPHe A23 0.75in",
	lastStatusChangeDate: 2014/09/27,
    currentStage: "shipped",
    currentRevision: "01"
  },
  { id: 3,
	rfid: "232452533",
	itemId: "343DFDF232",
	description: "HPO2 A51 1.5in",
	lastStatusChangeDate: 2014/08/23,
    currentStage: "kitting",
    currentRevision: "01"
  }
];

;App.Notifications = DS.Model.extend({
  date: DS.attr('date'),
  author: DS.attr('string'),
  message: DS.attr('string')
});;App.Session = DS.Model.extend({
  authToken: DS.attr('string'),
  account:   DS.belongsTo('App.Account')
});;App.User = DS.Model.extend({
  user: DS.attr('string'),
  name: DS.attr('string'),
  admin: DS.attr('boolean')
});;/**
 * 
 */
App.AdminRoute = Em.Auth.extend({
	authRedirectable: true
});;App.IndexRoute = Ember.Route.extend({
  model: function() {
    return this.store.find('notifications');
  }
});;App.LoginRoute = Ember.Route.extend({
	
});;;App.StatusListRoute = Ember.Route.extend({
	model: function() {
		//return this.store.findAll('item');	
	}
});;/**
 * 
 */
App.StatusRoute = Ember.Route.extend({
  authRedirectable: true
//  model: function() {
//    var listObjects = [];
//    Ember.$.getJSON('/status', function(status) {
//      status.forEach(function(data) {
//        statusObjects.pushObject(App.Item.createRecord(data));
//      });
//    });
//    return statusObjects;
//  }
});;App.SummaryRoute = Ember.Route.extend({
	authRedirectable: true
});;;App.Router.map(function() {
  this.route("summary", { path:"/summary" }); 
  this.resource("status", { path:"/status" }, function() {
    this.resource("list", { path:"/list/:list" });
    this.resource("item", { path:"/item/:item"});
  });
  this.resource("user", { path:"/user" });
  this.route("admin", { path:"/admin" });
  this.route("login", { path:"/login" });
});