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
		
	// from server
	deserialize : function(serialized) {
		var tz = jstz.determine();
		if (serialized) {
			return moment.tz(serialized, tz.name()).format('YYYY-MM-DD HH:mm:ss z');
		}
		return serialized;
	},
	
	// send to server
	serialize : function(deserialized) {
		if (deserialized) {
			return moment.utc(deserialized).toDate();
		}
		return deserialized;
	}
});;App.NotifyAlertComponent = Ember.Component.extend({
	
	classNameBindings: [':notify',
	                    'isOpaque'],
	
	/*
	 * @property {boolean} Should the view be opaque now?
	 */
	isOpaque: false,
	/*
	 * @property {number} Will be set by 'didInsertElement'.
	 * Used for clearing the auto-hide timeout
	 */
	timeoutId: null,
	
	/*
	 * @property {number} Will be hidden after this many ms.
	 */
	hideAfterMs: 5000,
	
	/*
	 * Lifecycle hook - called when the view enters the DOM
	 */
	didInsertElement: function(){
		// Set-up the auto-hide
		this.set('closed', false);
		this.set('timeoutId', setTimeout(function(){
			this.send('close');
		}.bind(this), this.get('hideAfterMs')));
		
		// Fade in the view.
		Ember.run.later(this, function() {
			this.set('isOpaque', true);
		}, 1);
	},
	
	actions: {
		close: function() {
			this.set('isOpaque, false');
			setTimeout(function() {
				this.set('notification.closed', true);
				clearTimeout(this.get('timeoutId'));
			}.bind(this), 300);
		}
	}
});;/**
 * Configuration file
 */

var config  = {
	baseURL: "54.85.61.143:8080"
};;App.ApplicationController = Ember.Controller.extend({
	// requires the sessions controller
	needs : [ 'sessions' ],

	notification: {
		type: null,
		title: null,
		message: null,
		closed: true
	},		
		
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
	}).property('controllers.sessions.currentUser'),

	actions : {
		/**
		 * Action handler for creating a new notification. Could be called from
		 * elsewhere throughout the application.
		 * 
		 * @param type
		 *            {String} classification; used for which icon to show
		 * @param title
		 *            {String} leading text
		 * @param message
		 *            {String} supporting text
		 */
		setNotification : function(type, title, message) {
			var notification = {
				type : type,
				title : title,
				message : message,
				closed : false
			};
			this.set('notification', notification);
		},
		testNotification : function() {
			this.send('setNotification', 'success', 'Success', 'Yay you did it.');
		}
	}
});;App.ChangeController = Ember.Controller.extend({
	needs : ['application'],
	actions : {
		change : function() {
			
			if( this.get('password') == this.get('verifyPassword') ){
			
				_this = this;
				
				var formData = { "username": this.get('controllers.application.currentUser').username, "password": this.get('password') };	
			
				Ember.$.ajax({
					url: '/service/users/pwchange',
					data: formData,
					type: 'POST',
					success: function(response){
						_this.get('controllers.application').send('setNotification', 'success', 'Success', 
						'Thank you for updating your password.');
						_this.transitionToRoute('index');
					},
					error: function(error) {
						_this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
						'Failed to update your password.');
					}
				});
			}
			_this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
			'Passwords are not equal.');
			
		}
	}
});;App.IndexController = Ember.ArrayController.extend({
	sortProperties: ['date'],
	sortAscending: false
});;App.ItemController = Ember.ObjectController.extend({});;App.ItemsController = Ember.ArrayController.extend({
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
	
	actions: {
		setStage: function(stage) {
			this.set('stage', [stage]);
		},
		
		toggleSort: function(column) {
		    if(this.get('sortedColumn') == column) {
		      this.toggleProperty('sortAscending');
		    } else {
		      this.set('sortProperties', [column]);
		      this.set('sortAscending', true);
		    }
		}
	}
});;App.NotificationsController = Ember.ArrayController.extend({
	needs: ['application'],
	actions: {
		remove: function(record) {
			var _this = this;
			record.deleteRecord();
			
			var onSuccess = function() {
				console.log("successfully deleted notification");
				this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'Notificaiton deleted.');
			};
			
			var onFail = function(error) {
				this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
				'Unable to delete this notification.');
				record.rollback();
			};
			
			record.save().then(onSuccess, onFail);
		}
	}
});;App.NotificationsCreateController = Ember.Controller.extend({
	needs: ['application', 'sessions'],
	
	currentUsername : (function() {
		return this.get('controllers.sessions.currentUser').username;
	}).property('controllers.sessions.currentUser'),
		
	actions: {
		create: function() {
			var _this = this;

			var notification = this.store.createRecord('notification', {
				title: _this.get('newNotification.title'),
				message: _this.get('newNotification.message'),
				created_by: _this.get('currentUsername'),
				date: moment($.now()).format('YYYY-MM-DD HH:mm:ss z')
			});
			
			var onSuccess = function(){
				_this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'Notification created successfully.');
				_this.transitionToRoute('notifications');
			};
			
			var onFail = function(notification){
				_this.store.deleteRecord(notification);
				_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
				'Unable to create notificatoin.');
				_this.transitionToRoute('notifications');
			};
			
			notification.save().then(onSuccess, onFail);
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
		}
	}
});;App.NotificationsEditController = Ember.ObjectController.extend({
	needs : ['application'],
	actions : {
		edit: function(obj) {
			var _this = this;

			this.store.find('notification', obj.id).then( function(notification) {
				notification.set('title',  _this.get('title'));
				notification.set('message', _this.get('message'));
				notification.set('created_by', _this.get('controllers.application.currentUser').username);
				notification.set('date', moment($.now()).format('YYYY-MM-DD HH:mm:ss z'));
				
				var onSuccess = function(){
					_this.controllerFor('application').send('setNotification', 'success', 'Success', 
					'Notification updated successfully.');
					_this.transitionToRoute('notifications');
				};
				
				var onFail = function(error){
					notification.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
					'Unable to edit notificatoin.');
					_this.transitionToRoute('notifications');
				};
				
				notification.save().then(onSuccess, onFail);
			});
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
		}
	}
});;App.ResetController = Ember.Controller.extend({
	needs : ['application'],
	actions : {
		reset : function() {
			_this = this;
			
			var formData = { "email": this.get('email') };	
		
			Ember.$.ajax({
				url: '/service/users/pwreset-email',
				data: formData,
				type: 'POST',
				success: function(response){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'An email will be sent to you shortly to reset your password.');
					_this.transitionToRoute('sessions');
				},
				error: function(error) {
					_this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
					'Password reset failed, please verify your email address.');
				}
			});
			
		},
		
		cancel : function(){
			this.transitionToRoute('sessions');
		}
	}
});;App.SessionsController = Ember.Controller.extend({
	init : function() {
		this._super();
		Ember.$.cookie.json = true;
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
	currentUser : (function() { 
		if(typeof Ember.$.cookie('auth_user') == 'string')
			return JSON.parse(Ember.$.cookie('auth_user'));
		else
			return Ember.$.cookie('auth_user');
	}).property(),
	lastRequest : Ember.$.cookie('last_request'),

	// create cookie for administration token
	admin : (function() {
		if(Ember.$.cookie('admin_token') === true)
			return true;
		else
			return false;
	}).property(),

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
		
		forgotPW : function(){
			this.transitionToRoute('reset');
		},

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
																
								// set this controller token & current user
								// properties
								// based on the data from the user and
								// access_token
								
								_this.setProperties({
									token : response.api_key[0].access_token.string,
									currentUser : {
										id: response.api_key[0].user_id.string,
										name: user.get('name'),
										email: user.get('email'),
										username: user.get('username')
									},
									admin: user.get('admin'),
									lastRequest: $.now()
								});

								// set the relationship between the User and the
								// ApiKey
								// models & save the apiKey object
								key.set('user', user);
								key.save();

								user.get('apiKeys').content.push(key);
								
								// force user to update password
								if( user.get('password_reset') ){
									_this.transitionToRoute('change');
								} else {
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
});;;App.SummaryController = Ember.ArrayController.extend({
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

	actions: {
		setStage: function(stage) {
			this.set('stage', [stage]);
		},
		
		toggleSort: function(column) {
		    if(this.get('sortedColumn') == column) {
		      this.toggleProperty('sortAscending');
		    } else {
		      this.set('sortProperties', [column]);
		      this.set('sortAscending', true);
		    }
		}
	}
});;App.UserController = Ember.Controller.extend({});;App.UsersController = Ember.ArrayController.extend({
	needs: ['application'],
	actions : {
		resetPW : function(id) {
			// TODO: add resource method to reset PW

		},
		remove : function(record) {
			var _this = this;
			record.deleteRecord();
			
			var onSuccess = function() {
				_this.get('controllers.application').send('setNotification',
						'success', 'Success', 'User deleted.');
			};

			var onFail = function(error) {
				_this.get('controllers.application').send('setNotification',
						'failure', 'Failed',
						'Unable to delete this user.');
				record.rollback();
			};
			
			record.save().then(onSuccess, onFail);
		}
	}
});;App.UsersCreateController = Ember.Controller.extend({
	needs: ['application'],
	
	actions: {
		create: function() {
			var _this = this;
			
			var user = this.store.createRecord('user', {
				username: _this.get('newUser.username'),
				name: _this.get('newUser.name'),
				email: _this.get('newUser.email'),
				admin: _this.get('newUser.admin'),
				scanner: _this.get('newUser.scanner'),
				user_created_date: moment($.now()).format('YYYY-MM-DD HH:mm:ss z')
			});
			
			var onSuccess = function(){
				_this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'User created successfully.');
				_this.transitionToRoute('users');
			};
			
			var onFail = function(user){
				_this.store.deleteRecord(user);
				_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
						'Unable to create this user');
				_this.transitionToRoute('users');
			};
			
			user.save().then(onSuccess, onFail);
		},
		
		cancel: function() {
			this.setProperties({
				username: null,
				name: null,
				email: null,
				admin: null,
				scanner: null
			});
			this.transitionToRoute('users');
		}
	}
});;App.UsersEditController = Ember.ObjectController.extend({
	needs: ['application'],
	
	actions : {
		edit: function(obj) {
			var _this = this;

			this.store.find('user', obj.id).then( function(user) {
				user.set('name', _this.get('name'));
				user.set('email', _this.get('email'));
				user.set('admin', _this.get('admin'));
				user.set('scanner', _this.get('scanner'));
				
				
				var onSuccess = function(){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'User updated successfully.');
					_this.transitionToRoute('users');
				};
				
				var onFail = function(error){
					notification.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this user.');
					_this.transitionToRoute('users');
				};
				
				user.save().then(onSuccess, onFail);
			});
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
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
});;App.Setting = DS.Model.extend({
  user:                 DS.attr('number'),
  user_changed:         DS.attr('boolean'),
  stage1:				DS.attr('string'),
  stage2:				DS.attr('string'),
  stage3:				DS.attr('string'),
  stage4:				DS.attr('string'),
  stage5:				DS.attr('string'),
  stage6:				DS.attr('string'),
  stage7:				DS.attr('string'),
  stage0:				DS.attr('string')
});;App.User = DS.Model.extend({
  username:              DS.attr('string'),
  password:              DS.attr('string'),
  password_reset:        DS.attr('boolean'),
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
    this.resource('item', { path:'/:item_id' });
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
  this.route('reset');
  this.route('change');
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
	//console.log("current time: [" + currenttime + "] last time: [" + lasttime + "]");	
	if(((currenttime - lasttime) > 600000)){
		this.controllerFor('sessions').reset();
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
			  this.controllerFor('sessions').reset();
			  this.redirectToLogin(transition);
		  } else {
			  console.log(reason.status);
		  }
	  }
  }
});;App.AdminRoute = App.AuthenticatedRoute.extend({});;App.ApplicationRoute = Ember.Route.extend({
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
		}
	}
});;App.IndexRoute = App.AuthenticatedRoute.extend({
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
});;App.ItemRoute = App.AuthenticatedRoute.extend({
	model: function(params) {
		return this.store.find('item', params.item_id);
	}
});;App.ItemsRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('item');
	}
});;App.ListRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find("item");
    }
});;App.NotificationsCreateRoute = App.AuthenticatedRoute.extend({	
	setupController: function(controller, model) {
        controller.set('newNotification', model);
    }
});;App.NotificationsEditRoute = App.AuthenticatedRoute.extend({
	
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
});;App.SettingsRoute = App.AuthenticatedRoute.extend({});;App.SummaryRoute = App.AuthenticatedRoute.extend({
	model: function() {
		// grab settings based upon current user
		var _this = this;
		
		return Ember.$.getJSON('service/settings/' + this.controllerFor('application').get('currentUser.id')).then(function(data){
			var setting = data.setting;
			
			// visible items = (now - stage_date) > stage_date_setting
			var filtered = _this.store.filter('item', function(item){
				var curStage = item.get('current_stage');
				var stage = item.get(curStage + '_date');
				if( stage !== null){
					var a = moment($.now());
					var diff = a.diff(moment(stage), 'days');
					if( diff > setting[curStage] ){
						return item;
					}
				}
			});
			return filtered;
		});	
	}
});;App.UserRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('user');
	},
	
	actions: {
		edit: function(obj) {
			var _this = this;
			
			if( this.get('password') == this.get('verify-password') ){
				this.store.find('user', obj.id).then( function(user) {
					user.set('name',  _this.get('name'));
					user.set('email', _this.get('email'));
					user.set('password', _this.get('password'));
					
					var onSuccess = function(){
						_this.controllerFor('application').send('setNotification', 'success', 'Success', 
						'User updated successfully.');
						_this.transitionToRoute('index');
					};
					
					var onFail = function(error){
						notification.rollback();
						_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
						'Unable to edit user.');
						_this.transitionToRoute('user');
					};
					
					user.save().then(onSuccess, onFail);
				});
			}
			_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
			'Unable to edit user. Please verify passwords match.');
		},
		cancel: function() {
			this.transitionToRoute('index');
		}
	}
});;App.UsersCreateRoute = App.AuthenticatedRoute.extend({
	setupController: function(controller, model) {
        controller.set('newUser', model);
    }
});;App.UsersEditRoute = App.AuthenticatedRoute.extend({

});;App.UsersRoute = App.AuthenticatedRoute.extend({
	model : function() {
		return this.store.find('user');
	}
});