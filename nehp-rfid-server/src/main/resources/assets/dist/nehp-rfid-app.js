// Application
window.App = Ember.Application.create({
		LOG_TRANSITIONS:true, // debugging == true
		LOG_TRANSITIONS_INTERNAL: true,
		LOG_ACTIVE_GENERATION: true,
		LOG_RESOLVER: true,
		rootElement:'body'
		});

// Adapter
App.ApplicationAdapter = DS.RESTAdapter.extend({
	namespace: "service"
});

App.ApiKeyAdapter = DS.LSAdapter.extend({
	namespace: 'nehp-rfid'
});

// Store
App.Store = DS.Store.extend({
  revision:12,
  adapter: App.ApplicationAdapter.create()
});;/**
 * Configuration file
 */

var config  = {
	baseURL: "54.85.61.143:8080"
};;App.Controller = Ember.ArrayController.extend({
	// requires the sessions controller
	needs : [ 'sessions' ],

	// creates a computed property called currentUser that will be
	// binded on the curretUser of the sessions controller and will return its
	// value
	currentUser : (function() {
		return this.get('controllers.sessions.currentUser');
	}).property('controllers.sessions.currentUser'),

	// creates a computed property called admin that will be binded on
	// the admin of the sessions controller to verify if the user is
	// an administrator
	isAdmin : (function() {
		return this.get('controllers.sessions.admin');
	}).property('controllers.sessions.admin'),

	// creates a computed property called isAuthenticated that will be
	// binded on the curretUser of the sessions controller and will verify if
	// the object is empty
	isAuthenticated : (function() {
		return !Ember.isEmpty(this.get('controllers.sessions.currentUser'));
	}).property('controllers.sessions.currentUser')
});;App.IndexController = Ember.ArrayController.extend({
	sortProperties: ['date'],
	sortAscending: false
});;App.ListController = Ember.ArrayController.extend({
	queryParams: ['stage'],
	stage: 'ALL',
	filteredItems: Ember.computed.filterBy('model', 'current_stage', function(){
		return this.get('stage');
	})
});;App.SessionsController = Ember.Controller.extend({
	init : function() {
		this._super();
		if (Ember.$.cookie('access_token')) {
			Ember.$.ajaxSetup({
				headers : {
					'Authorization' : 'Bearer ' + Ember.$.cookie('access_token')
				}
			});
		}
	},

	// overwriting the default attemptedTransition attribute from the
	// Ember.Controller object
	attemptedTransition : null,

	// create and set the token & current user objects based on the respective
	// cookies
	token : Ember.$.cookie('access_token'),
	currentUser : Ember.$.cookie('auth_user'),

	// create cookie for administration token
	admin : Ember.$.cookie('admin_token'),

	tokenChanged : (function() {
		if (Ember.isEmpty(this.get('token'))) {
			Ember.$.removeCookie('access_token');
			Ember.$.removeCookie('auth_user');
			Ember.$.removeCookie('admin_token');
		} else {
			Ember.$.cookie('access_token', this.get('token'));
			Ember.$.cookie('auth_user', this.get('currentUser'));
			Ember.$.cookie('admin_token', this.get('admin'));
		}
	}).observes('token'),

	// reset the controller properties and the ajax header
	reset : function() {
		this.setProperties({
			username : null,
			password : null,
			token : null,
			currentUser : null
		});
		Ember.$.ajaxSetup({
			headers : {
				'Authorization' : 'Bearer none'
			}
		});
	},

	actions : {

		login : function() {
			var _this = this;
			var attemptedTrans = this.get('attemptedTransition');

			var formData = { "grant_type":"password", "username": this.get('username'), "password": this.get('password')};
			
			Ember.$.ajax({
				url: '/service/auth/token',
				data: formData,
				dataType: 'json',
				type: 'POST',
				success: function(response){
					// test response from server
					//console.log(JSON.stringify(response));

					// set the ajax header with the returned access_token object
					Ember.$.ajaxSetup({
						headers : {
							'Authorization' : 'Bearer ' + response.api_key[0].access_token.string
						}
					});
					
					_this.setProperties({
						username : null,
						password : null
					});

					var key = _this.get('store').createRecord('apiKey', {
						accessToken : response.api_key[0].access_token.string
					});
					
					// testing user_id string
					//console.log(response.api_key[0].user_id.string);
				
				
					_this.store.find('user', response.api_key[0].user_id.string).then(
							function(user) {
								
								// tests
								console.log(JSON.stringify(user));
								console.log(user);
								
								// set this controller token & current user
								// properties
								// based on the data from the user and
								// access_token
								
								_this.setProperties({
									token : response.api_key[0].access_token.string,
									currentUser : user.getProperties('username', 'name', 'email'),
									admin: user.getProperties('admin')
								});

								// set the relationship between the User and the
								// ApiKey
								// models & save the apiKey object
								key.set('user', user);
								key.save();

								user.get('apiKeys').content.push(key);

								// check if there is any attemptedTransition to
								// retry it
								// or go to the secret route
								if (attemptedTrans) {
									attemptedTrans.retry();
									_this.set('attemptedTransition', null);
								} else {
									_this.transitionToRoute('index');
								}
							}
				    );
				},
				error: function(error){
					if (error.status === 401) {
						// if there is a authentication error the user is
						// informed of it to try again
						alert("Wrong user or password, please try again");
				    }
					console.log(error);
			    }
			});
		}
	}
});;;;App.ApiKey = DS.Model.extend({
	accessToken: DS.attr('string'),
	user:		 DS.belongsTo('user')
});;App.Item = DS.Model.extend({
    rfid: DS.attr('string'),
    item_id: DS.attr('string'),
    description: DS.attr('string'),
    created_by: DS.attr('string'),
    created_date: DS.attr('date'),
    current_revision: DS.attr('string'),
    current_revision_date: DS.attr('date'),
    current_stage: DS.attr('string'),
    last_status_change_date: DS.attr('date'),
    last_status_change_user: DS.attr('string'),
    stage1_user: DS.attr('string'),
    stage1_date: DS.attr('date'),
    stage2_user: DS.attr('string'),
    stage2_date: DS.attr('date'),
    stage3_user: DS.attr('string'),
    stage3_date: DS.attr('date'),
    stage4_user: DS.attr('string'),
    stage4_date: DS.attr('date'),
    stage5_user: DS.attr('string'),
    stage5_date: DS.attr('date'),
    stage6_user: DS.attr('string'),
    stage6_date: DS.attr('date'),
    stage7_user: DS.attr('string'),
    stage7_date: DS.attr('date'),
    stage0_user: DS.attr('string'),
    stage0_date: DS.attr('date')
});;App.Notification = DS.Model.extend({
  title: DS.attr('string'),
  date: DS.attr('date'),
  created_by: DS.attr('string'),
  message: DS.attr('string')
});;App.User = DS.Model.extend({
  username:              DS.attr('string'),
  password:              DS.attr('string'),
  name:                  DS.attr('string'),
  email:                 DS.attr('string'),
  last_login_date:		 DS.attr('string'),
  user_created_date:	 DS.attr('string'),
  admin:                 DS.attr('boolean'),
  scanner:				 DS.attr('boolean'),
  apiKeys:               DS.hasMany('apiKey')
});;App.Router.map(function() {
  this.resource('summary', function(){}); 
  this.resource('status', function() {
    this.resource('list', { path: '/list' });
    this.resource('item', { path:'/item/:item' });
  });
  this.resource('user', { path:'/user/:user_id' });
  this.resource('admin', function(){});
  this.resource('sessions', function(){
	this.route('login');
	this.route('logout');
  });
});;App.AdminRoute = Ember.Route.extend({
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
});;App.ApplicationRoute = Ember.Route.extend({
	actions : {
		// create a global logout action
		logout : function() {
			// get the sessions controller instance and reset it to then
			// transition to the sessions route
			this.controllerFor('sessions').reset();
			this.transitionTo('sessions');
		},
		error: function(error, transition) {
			if (error && error.status === 401) {
				// error substate and parent routes do not handle this error 
				this.controllerFor('sessions').reset();
				return this.transitionTo('sessions');
			}
			// Return true to bubble this event to any parent route.
			return true;
		}
	}
});;// create a base object for any authentication protected route
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
	        console.log(reason.status);
	      }
	    }
	  }
});;App.IndexRoute = App.AuthenticatedRoute.extend({
  model: function() {
    return this.store.find('notification');
  }
});;App.ListRoute = App.AuthenticatedRoute.extend({
	model: function() {
      return this.store.find("item");
    }
});;App.SecretRoute = App.AuthenticatedRoute.extend({
	model : function() {
		// instantiate the model for the SecretController as a list of created
		// users
		return this.store.find('user');
	}
});;App.SessionsRoute = Ember.Route.extend({
	beforeModel : function(transition) {
		// before proceeding any further, verify if the token property is not
		// empty
		// if it is, transition to the secret route
		if (!Ember.isEmpty(this.controllerFor('sessions').get('token'))) {
			this.transitionTo('index');
		}
	}
});;App.StatusItemRoute = App.AuthenticatedRoute.extend({
	model: function(params){
		return Ember.$.getJSON('service/status/item' + params.item);
	}
});;App.StatusRoute = App.AuthenticatedRoute.extend({});;App.SummaryRoute = App.AuthenticatedRoute.extend({});;App.UserRoute = App.AuthenticatedRoute.extend({});