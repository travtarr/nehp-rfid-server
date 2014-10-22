// Application
window.App = Ember.Application.create({
	LOG_TRANSITIONS : true, // debugging == true
	LOG_TRANSITIONS_INTERNAL : true,
	LOG_ACTIVE_GENERATION : true,
	LOG_RESOLVER : true,
	rootElement : 'body'
});

// Adapter
App.ApplicationAdapter = DS.RESTAdapter.extend({
	namespace : "service"
});

App.ApiKeyAdapter = DS.LSAdapter.extend({
	namespace : 'nehp-rfid'
});

// Store
App.Store = DS.Store.extend({
	revision : 12,
	adapter : App.ApplicationAdapter.create()
});

App.SimpledateTransform = DS.Transform.extend({
	deserialize : function(serialized) {
		if (serialized) {
			return moment(serialized).format('MMM Do YY, h:mm:ss a');
		}
		return serialized;
	},

	serialize : function(deserialized) {
		if (deserialized) {
			return moment(deserialized).toDate();
		}
		return deserialized;
	}
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
});;App.ItemsController = Ember.ArrayController.extend({
	sortProperties: ['rfid'],
	stage: 'ALL',
	sortedColumn: (function() {
	    var properties = this.get('sortProperties');
	    if(!properties) return undefined;
	    return properties.get('firstObject');
	  }).property('sortProperties.[]'),
	filteredItems: function(){
		var stage = this.get('stage');
		var items = this.get('arrangedContent');
		
		if(stage == 'ALL')
			return items;
		else {
			return items.filter(function(item) {
				return item.get('current_stage') == stage;
			});
		}
	}.property('stage', 'arrangedContent'),
	toggleSort: function(column) {
	    if(this.get('sortedColumn') == column) {
	      this.toggleProperty('sortAscending');
	    } else {
	      this.set('sortProperties', [column]);
	      this.set('sortAscending', true);
	    }
	},
	actions: {
		setStage: function(stage) {
			this.set('stage', [stage]);
		}
	}
});;App.NotificationsController = Ember.ArrayController.extend({
	actions: {
		create: function() {
			var _this = this;

			this.store.createRecord('notification', {
				title: _this.get('title'),
				message: _this.get('message'),
				created_by: _this.controllerFor('sessions').get('currentUser'),
				date: $.now()
			});
		},
		edit: function(id) {
			var _this = this;

			this.store.find('notification', id).then( function(notification) {
				notification.set('title',  _this.get('title'));
				notification.set('message', _this.get('message'));
				notification.set('created_by', _this.controllerFor('sessions').get('currentUser'));
				notification.set('date', $.now());
				
				notification.save();
			});
		},
		remove: function(id) {
			this.store.find('notification', id).then(function (notification) {
				  notification.destroyRecord();
			});
		}
	}
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
	lastRequest : Ember.$.cookie('last_request'),

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
									currentUser : JSON.stringify(user.getProperties('username', 'name', 'email')),
									admin: JSON.stringify(user.getProperties('admin')),
									lastRequest: $.now()
								});
								console.log(user.getProperties('admin'));
								console.log(JSON.stringify(user.getProperties('admin')));

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
});;;App.UsersController = Ember.ArrayController.extend({
	actions: {
		create: function() {
			var _this = this;

			this.store.createRecord('user', {
				username: _this.get('username'),
				name: _this.get('name'),
				email: _this.get('email'),
				admin: _this.get('admin'),
				scanner: _this.get('scanner'),
				user_created_date: $.now()
			});
		},
		edit: function(id) {
			var _this = this;

			this.store.find('user', id).then( function(user) {
				user.set('name', _this.get('name'));
				user.set('email', _this.get('email'));
				user.set('admin', _this.get('admin'));
				user.set('scanner', _this.get('scanner'));
				
				user.save();
			});
		},
		resetPW: function(id) {
			// TODO: add resource method to reset PW
			
		},
		remove: function(id) {
			store.find('user', id).then(function (user) {
				  user.destroyRecord();
			});
		}
	}
});;App.ApiKey = DS.Model.extend({
	accessToken: DS.attr('string'),
	user:		 DS.belongsTo('user')
});;App.Item = DS.Model.extend({
    rfid: DS.attr('string'),
    item_id: DS.attr('string'),
    description: DS.attr('string'),
    created_by: DS.attr('string'),
    created_date: DS.attr('simpledate'),
    current_revision: DS.attr('string'),
    current_revision_date: DS.attr('simpledate'),
    current_stage: DS.attr('string'),
    last_status_change_date: DS.attr('simpledate'),
    last_status_change_user: DS.attr('string'),
    stage1_user: DS.attr('string'),
    stage1_date: DS.attr('simpledate'),
    stage2_user: DS.attr('string'),
    stage2_date: DS.attr('simpledate'),
    stage3_user: DS.attr('string'),
    stage3_date: DS.attr('simpledate'),
    stage4_user: DS.attr('string'),
    stage4_date: DS.attr('simpledate'),
    stage5_user: DS.attr('string'),
    stage5_date: DS.attr('simpledate'),
    stage6_user: DS.attr('string'),
    stage6_date: DS.attr('simpledate'),
    stage7_user: DS.attr('string'),
    stage7_date: DS.attr('simpledate'),
    stage0_user: DS.attr('string'),
    stage0_date: DS.attr('simpledate')
});;App.Notification = DS.Model.extend({
  title: DS.attr('string'),
  date: DS.attr('simpledate'),
  created_by: DS.attr('string'),
  message: DS.attr('string')
});;App.User = DS.Model.extend({
  username:              DS.attr('string'),
  password:              DS.attr('string'),
  name:                  DS.attr('string'),
  email:                 DS.attr('string'),
  last_login_date:		 DS.attr('simpledate'),
  user_created_date:	 DS.attr('simpledate'),
  admin:                 DS.attr('boolean'),
  scanner:				 DS.attr('boolean'),
  apiKeys:               DS.hasMany('apiKey')
});;App.Router.map(function() {
  this.resource('summary'); 
  this.resource('items', function() {
    this.resource('item', { path:'/:item' });
  });
  this.resource('user', { path:'/user/:user_id' });
  this.resource('admin', function(){
	  this.resource('notifications', function(){
		  this.route('create');
		  this.route('edit', { path: '/edit/:id'});
	  });
	  this.resource('users', function(){
		  this.route('create');
		  this.route('edit', { path: '/edit/:id'});
	  });
	  this.resource('settings');
  });
  this.resource('sessions');
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
	// check if time since last request is greater than 10mins (10m x 60s x 1000ms = 600,000)
	if(Ember.isEmpty(this.controllerFor('sessions').get('token'))) {
		return this.redirectToLogin(transition);
	} 
	
	var currenttime = $.now();
	var lasttime = this.controllerFor('sessions').get('lastRequest');
		
	if(((currenttime - lasttime) > 600000)){
		return this.redirectToLogin(transition);	
	} else {
		this.controllerFor('sessions').set('lastRequest', $.now());
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
  },
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
});;App.ItemsRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('item');
	}
});;App.ListRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find("item");
    }
});;App.NotificationsRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('notification');
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
});;App.SettingsRoute = App.AuthenticatedRoute.extend({});;App.SummaryRoute = App.AuthenticatedRoute.extend({});;App.UserRoute = App.AuthenticatedRoute.extend({});;App.UsersRoute = App.AuthenticatedRoute.extend({
	model : function() {
		return this.store.find('user');
	}
});