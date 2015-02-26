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
};;App.AdminSettingsController = Ember.ObjectController.extend({
	needs: ['application'],
	actions: {
		edit: function() {	
			var _this = this;

			this.store.find( 'setting', { user : this.get('controllers.application.currentUser').id } ).then( function(settings) {
				// Update the model
				console.log(settings);
				settings.forEach(function(setting) {
					var stage = setting.get('stage');
					console.log(stage);
					var total = (Math.floor(parseInt(_this.get('stage' + stage + 'day'), 10)) * 24) + 
						Math.floor(parseInt(_this.get('stage' + stage + 'hour'), 10));
					setting.set('duration', total);
				});
				
				var onSuccess = function(){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'Setting updated successfully.');
					_this.transitionToRoute('settings');
				};
				
				var onFail = function(error){
					settings.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this setting.');
					_this.transitionToRoute('settings');
				};
				
				settings.save().then(onSuccess, onFail);
			});
		}
	}
});;App.ApplicationController = Ember.Controller.extend({
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
		return !Ember.isEmpty(this.get('controllers.sessions.currentUser.username'));
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
		}
	}
});;App.ChangeController = Ember.Controller.extend({
	needs : ['application'],
	actions : {
		change : function() {
			
			if( this.get('password') == this.get('verify-password') ){
			
				_this = this;
				
				var formData = { "username": this.get('controllers.application.currentUser').username, "password": this.get('password') };	
			
				Ember.$.ajax({
					url: '/service/users/pwchange',
					data: formData,
					type: 'POST',
					success: function(response){
						this.set('controllers.application.currentUser.password_reset', false);
						_this.get('controllers.application').send('setNotification', 'success', 'Success', 
						'Thank you for updating your password.');
						_this.transitionToRoute('index');
					},
					error: function(error) {
						_this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
						'Failed to update your password.');
					}
				});
			} else {
				this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
				'Passwords are not equal.');
			}
		}
	}
});;App.IndexController = Ember.ArrayController.extend({
	sortProperties: ['date'],
	sortAscending: false
});;App.ItemController = Ember.ObjectController.extend({
	itemid: function() {
		return this.model.get('id');
	}.property('model')
});;App.ItemsController = Ember.ArrayController.extend({
	sortProperties: ['item_id'],
	stage: 'ALL',
	sortedColumn: (function() {
	    var properties = this.get('sortProperties');
	    if(!properties) return undefined;
	    return properties.get('firstObject');
	  }).property('sortProperties.[]'),
	filteredItems: function(){
		var stage = this.get('stage');
		var items = this.get('arrangedContent');
		items.forEach(function(item){
			var stage = item.get('last_status_change_date');
			if( stage !== null ){
				var a = moment($.now());
				var diff = a.diff(moment(stage, "YYYY-MM-DD HH:mm:ss"), 'hours');
				var days = 0;
				var hours = 0;
				var dayStr = "days";
				var hourStr = "hours";
				if (diff > 24){
					days = Math.floor(diff / 24);
					hours = (diff - (days * 24));	
					if (hours == 1)
						hourStr = "hour";
					if (days == 1)
						dayStr = "day";
					item.set('last_status_change_duration', days + " " + dayStr +
							", " + hours + " " + hourStr + " ago");
				} else {
					days = 0;
					hours = diff;
					if (hours == 1)
						hourStr = "hour";
					item.set('last_status_change_duration', hours + " " + hourStr + " ago");
				}
			} else {
				item.set('last_status_change_duration', "");
			}
		});
		if(stage == 'ALL')
			return items;
		else {
			return items.filter(function(item) {
				return item.get('current_stage_desc') == stage;
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
		},
		
		goToItem: function(id) {
			this.transitionToRoute('item', id); 
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
	needs: [ 'application' ],
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
	cookiesEnabled: false,
	rawToken: null,
	token : (function() {
		if(Ember.$.cookie('access_token')){
			return Ember.$.cookie('access_token');
		} else
			return this.get('rawToken');
	}).property('rawToken'),
	
	currentUser : (function() { 
		if(Ember.$.cookie('auth_user')){
			if(typeof Ember.$.cookie('auth_user') == 'string')
				return JSON.parse(Ember.$.cookie('auth_user'));
			else
				return Ember.$.cookie('auth_user');
		}
	}).property('rawToken'),
	
	lastRequest : (function() {
		if(Ember.$.cookie('last_request')){
			return Ember.$.cookie('last_request');
		}
	}).property(),

	// create cookie for administration token
	admin : (function() {
		if(Ember.$.cookie('admin_token')){
			if(Ember.$.cookie('admin_token') === true)
				return true;
			else
				return false;
		} else {
			return this.get('user.admin');
		}
	}).property('user.admin'),

	tokenChanged : (function() {
		if (Ember.isEmpty(this.get('token'))) {
			Ember.$.removeCookie('access_token');
			Ember.$.removeCookie('auth_user');
			Ember.$.removeCookie('admin_token');
			Ember.$.removeCookie('last_request');
		} else if(this.get('cookiesEnabled')) {
			Ember.$.cookie('access_token', this.get('rawToken'));
			Ember.$.cookie('auth_user', this.get('currentUser'));
			Ember.$.cookie('admin_token', this.get('user.admin'));
			Ember.$.cookie('last_request', $.now());
		} 
	}).observes('rawToken'),

	// reset the controller properties and the ajax header
	reset : function() {
		this.setProperties({
			username : null,
			password : null,
			rawToken : null,
			token: null,
			currentUser : null,
			cookiesEnabled: false
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
			
			// remove cookies if user had previously used them
			if(!this.get('remember')){
				if(Ember.$.cookie('access_token'))
					Ember.$.removeCookie('access_token');
				if(Ember.$.cookie('auth_user'))
					Ember.$.removeCookie('auth_user');
				if(Ember.$.cookie('admin_token'))
					Ember.$.removeCookie('admin_token');
				if(Ember.$.cookie('last_request'))
					Ember.$.removeCookie('last_request');
			}
			
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
									rawToken: response.api_key[0].access_token.string,
									token: response.api_key[0].access_token.string,
									currentUser : {
										id: response.api_key[0].user_id.string,
										setting: user.get('setting'),
										name: user.get('name'),
										email: user.get('email'),
										username: user.get('username'),
										admin: user.get('admin')
									},
									admin: user.get('admin'),
									lastRequest: $.now(),
									cookiesEnabled: _this.get('remember')
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
						this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
						'Username and/or Password incorrect. Please try again.');
				    }
					console.log(error);
			    }
			});
		}
	}
});;App.StagesController = Ember.ArrayController.extend({
	filteredItems: function(){
		var items = this.get('arrangedContent');
		var prevItem = null;
		items.forEach(function(item){
			if (prevItem !== null){
				var prevStage = prevItem.get('stage_date');
				var stage = item.get('stage_date');
				var a = moment(prevStage, "YYYY-MM-DD HH:mm:ss");
				var b = null;
				if( stage === null ){
					b = moment($.now());
				} else {
					b = moment(stage, "YYYY-MM-DD HH:mm:ss");
				}
				var diff = b.diff(a, 'hours');
				var days = 0;
				var hours = 0;
				var dayStr = "days";
				var hourStr = "hours";
				if (diff > 24){
					days = Math.floor(diff / 24);
					hours = (diff - (days * 24));	
					if (hours == 1)
						hourStr = "hour";
					if (days == 1)
						dayStr = "day";
					item.set('duration', days + " " + dayStr +
							", " + hours + " " + hourStr);
				} else {
					days = 0;
					hours = diff;
					if (hours == 1)
						hourStr = "hour";
					item.set('duration', hours + " " + hourStr);
				}
			}
			prevItem = item;
		});
		return items;
		
	}.property('arrangedContent')
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
		items.forEach(function(item){
			var stage = item.get('last_status_change_date');
			if( stage !== null ){
				var a = moment($.now());
				var diff = a.diff(moment(stage, "YYYY-MM-DD HH:mm:ss"), 'hours');
				var days = 0;
				var hours = 0;
				var dayStr = "days";
				var hourStr = "hours";
				if (diff > 24){
					days = Math.floor(diff / 24);
					hours = (diff - (days * 24));	
					if (hours == 1)
						hourStr = "hour";
					if (days == 1)
						dayStr = "day";
					item.set('last_status_change_duration', days + " " + dayStr +
							", " + hours + " " + hourStr + " ago");
				} else {
					days = 0;
					hours = diff;
					if (hours == 1)
						hourStr = "hour";
					item.set('last_status_change_duration', hours + " " + hourStr + " ago");
				}
			} else {
				item.set('last_status_change_duration', "");
			}
		});
		if(stage == 'ALL')
			return items;
		else {
			return items.filter(function(item) {
				return item.get('current_stage_desc') == stage;
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
		},
		goToItem: function(id) {
			this.transitionToRoute('item', id); 
		}
	}
});;App.UserController = Ember.Controller.extend({});;App.UserSettingsController = Ember.ObjectController.extend({
	needs: ['application'],
	actions: {
		edit: function() {	
			var _this = this;

			this.store.find( 'setting', { user : this.get('controllers.application.currentUser').id } ).then( function(settings) {
				// Update the model
				console.log(settings);
				settings.forEach(function(setting) {
					var stage = setting.get('stage');
					console.log(stage);
					var total = (Math.floor(parseInt(_this.get('stage' + stage + 'day'), 10)) * 24) + 
						Math.floor(parseInt(_this.get('stage' + stage + 'hour'), 10));
					setting.set('duration', total);
				});
				
				var onSuccess = function(){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'Setting updated successfully.');
				};
				
				var onFail = function(error){
					settings.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this setting.');
				};
				
				settings.save().then(onSuccess, onFail);
			});
		}
	}
});;App.UsersController = Ember.ArrayController.extend({
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
					user.rollback();
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
    comment: DS.attr('string'),
    printed: DS.attr('boolean'),
    reason: DS.attr('string'),
    group: DS.attr('number'),
    created_by: DS.attr('string'),
    created_date: DS.attr('simpledate'),
    revision: DS.attr('string'),
    current_stage: DS.attr('number'),
    current_stage_num: DS.attr('number'),
    current_stage_desc: DS.attr('string'),
    last_status_change_date: DS.attr('simpledate'),
    last_status_change_user: DS.attr('string')
});;App.Notification = DS.Model.extend({
  title: DS.attr('string'),
  date: DS.attr('simpledate'),
  created_by: DS.attr('string'),
  message: DS.attr('string')
});;App.Setting = DS.Model.extend({
  user:                 DS.attr('number'),
  user_changed:         DS.attr('boolean'),
  stage:				DS.attr('number'),
  duration:				DS.attr('number')
});;App.Stagelog = DS.Model.extend({
	item: DS.attr('number'),
	stage: DS.attr('number'),
	description: DS.attr('string'),
	stage_date: DS.attr('simpledate'),
	signed_by: DS.attr('string'),
	reason: DS.attr('string')
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
    this.resource('item', { path:'/:item_id' }, function(){
       this.resource('stages');
    });
  });
  this.resource('user', { path:'/user/:user_id' }, function(){
	  this.route('info');
	  this.route('settings');
  });
  this.resource('admin', function(){
	  this.resource('notifications', function(){
		  this.route('create');
		  this.route('edit', { path: '/edit/:id'});
	  });
	  this.resource('users', function(){
		  this.route('create');
		  this.route('edit', { path: '/edit/:id'});
	  });
	  this.route('settings');
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
});;App.AdminRoute = App.AuthenticatedRoute.extend({});;App.AdminSettingsRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find('setting', { admin: true }).then(function(settings){
			var obj = {};
			settings.forEach(function(setting){
				var stage = setting.get('stage');
				var total = setting.get('duration');
				var days = Math.floor(total / 24);
				var hours = total % 24;
				obj["stage" + stage + "day"] = days;
				obj["stage" + stage + "hour"] = hours;
			});

			return obj;
		});
	}
});;App.ApplicationRoute = Ember.Route.extend({
	image: null,
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
	},
	afterModel: function(){
		this.transitionTo('stages');
	}
});;App.ItemsRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('item');
	},
	
	actions: {
		/**
		 * Exports the current visible items in the table to an excel file.
		 */
		excelFilter: function(){
			var excel="<table>";
			var el = this;
			// Header
			$(el).find('thead').find('tr').each(function() {
				excel += "<tr>";
				$(this).filter(':visible').find('th').each(function(index,data) {
					if ($(this).css('display') != 'none'){					
						if(defaults.ignoreColumn.indexOf(index) == -1){
							excel += "<td>" + parseString($(this))+ "</td>";
						}
					}
				});	
				excel += '</tr>';						
				
			});					
			
			// Row Vs Column
			var rowCount=1;
			$(el).find('tbody').find('tr').each(function() {
				excel += "<tr>";
				var colCount=0;
				$(this).filter(':visible').find('td').each(function(index,data) {
					if ($(this).css('display') != 'none'){	
						if(defaults.ignoreColumn.indexOf(index) == -1){
							excel += "<td>"+parseString($(this))+"</td>";
						}
					}
					colCount++;
				});															
				rowCount++;
				excel += '</tr>';
			});					
			excel += '</table>';
			
			var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
			excelFile += "<head>";
			excelFile += "<!--[if gte mso 9]>";
			excelFile += "<xml>";
			excelFile += "<x:ExcelWorkbook>";
			excelFile += "<x:ExcelWorksheets>";
			excelFile += "<x:ExcelWorksheet>";
			excelFile += "<x:Name>";
			excelFile += "{worksheet}";
			excelFile += "</x:Name>";
			excelFile += "<x:WorksheetOptions>";
			excelFile += "<x:DisplayGridlines/>";
			excelFile += "</x:WorksheetOptions>";
			excelFile += "</x:ExcelWorksheet>";
			excelFile += "</x:ExcelWorksheets>";
			excelFile += "</x:ExcelWorkbook>";
			excelFile += "</xml>";
			excelFile += "<![endif]-->";
			excelFile += "</head>";
			excelFile += "<body>";
			excelFile += excel;
			excelFile += "</body>";
			excelFile += "</html>";

			var base64data = "base64," + $.base64.encode(excelFile);
			window.open('data:application/vnd.ms-excel;filename=exportData.xls;' + base64data);
		},
		excel: function(data){
			
		},
		/**
		 * Exports all items loaded from the server into an excel file.
		 */
		allexcel: function(){
			var excel = "<table>";
			
			// Header
			excel += "<tr>";
			App.Item.eachAttribute(function(name) {
				excel += "<td>" + name + "</td>";
			});
			excel += '</tr>';					

			this.store.find('item').then(function(items){
				// Each Row
				items.forEach(function(item){
					jsonItem = item.toJSON();
					excel += "<tr>";
					// Each Column
					$.each(jsonItem, function(attrib, value){
						// Filter out null values
						if (value === null){
							excel += "<td></td>";
						} else {
							excel += "<td>"+ value +"</td>";
						}
					});			
					excel += '</tr>';
				});
	
				excel += '</table>';
				
				var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
				excelFile += "<head>";
				excelFile += "<!--[if gte mso 9]>";
				excelFile += "<xml>";
				excelFile += "<x:ExcelWorkbook>";
				excelFile += "<x:ExcelWorksheets>";
				excelFile += "<x:ExcelWorksheet>";
				excelFile += "<x:Name>";
				excelFile += "{worksheet}";
				excelFile += "</x:Name>";
				excelFile += "<x:WorksheetOptions>";
				excelFile += "<x:DisplayGridlines/>";
				excelFile += "</x:WorksheetOptions>";
				excelFile += "</x:ExcelWorksheet>";
				excelFile += "</x:ExcelWorksheets>";
				excelFile += "</x:ExcelWorkbook>";
				excelFile += "</xml>";
				excelFile += "<![endif]-->";
				excelFile += "</head>";
				excelFile += "<body>";
				excelFile += excel;
				excelFile += "</body>";
				excelFile += "</html>";
	
				var base64data = "base64," + $.base64.encode(excelFile);
				window.open('data:application/vnd.ms-excel;filename=exportData.xls;' + base64data);
			});
		},
		excelDuration: function() {
			$.get("/service/reports/duration/hours").done(function(data) {
				window.open('data:application/vnd.ms-excel;filename=exportData.xls;' + data);
			});
			
		}
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
});;App.ResetRoute = Ember.Route.extend({
	actions: {
		reset: function() {
			var _this = this;
			var email = function(){
				var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				if (regex.test(_this.get('email'))){
					return _this.get('email');
				} else {
					return "null";
				}
			};
			if( email !== "null" ){
				var formData = {'email': email};
				Ember.$.ajax({
					url: '/service/users/pwreset-email',
					data: formData,
					type: 'POST',
					success: function(response){
						_this.get('controllers.application').send('setNotification', 'success', 'Success', 
						'An email should be arriving shortly to update your password.');
						_this.transitionToRoute('index');
					},
					error: function(error) {
						_this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
						'Email not recognized.');
					}
				});
			} else {
				_this.get('controllers.application').send('setNotification', 'failure', 'Failure', 
				'Not a valid email.');
			}
		}
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
});;App.StagesRoute = App.AuthenticatedRoute.extend({
	setupController: function(controller) {
        var _this = this;
        var item = this.controllerFor('item').get('itemid');
        return this.get('store').find('stagelog').then(function() {
            controller.set('model', _this.get('store').filter('stagelog', function(stage){
                return stage.get('item') == item;
            }));
        });
    },
	actions: {
		showImage: function(stageid) {
			var stage = this.modelFor('stages').findBy('id', stageid);
			var imageData = "data:image/gif;base64,"  + 
			$.getJSON("/service/signature/" + stage.get('item') + "/" + stage.get('stage'));
			
			this.set('image', imageData);
			this.render('modal', { into: 'application', outlet: 'modal' });
		},
		closeImage: function() {
		    this.render('nothing', { into: 'application', outlet: 'modal' });
		}
	}
});;App.SummaryRoute = App.AuthenticatedRoute.extend({
	model: function() {
		// grab settings based upon current user
		var _this = this;
		
		return this.store.find('item').then(function(items) {
			return Ember.$.getJSON('service/settings?user=' + _this.controllerFor('application').get('currentUser.id')).then(function(data){
				var settings = Ember.A(data.settings);
				// visible items = (now - stage_date) > stage_date_setting
				return _this.store.filter('item', function(item){
					var curStageNum = item.get('current_stage_num');
					
					var stage = item.get('last_status_change_date');
					if( stage !== null ){
						var a = moment($.now());
						var diff = a.diff(moment(stage, "YYYY-MM-DD HH:mm:ss"), 'hours');
						if( diff > settings.filterBy('stage', curStageNum).get('duration') ){
							return item;
						}
					}
				});
			});	
		});
	}
});;App.UserInfoRoute = App.AuthenticatedRoute.extend({
	actions: {
		edit: function(obj) {
			var _this = this;
			
			if( this.get('newpassword') == this.get('verify-password') ){
				this.store.find('user', obj.id).then( function(user) {
					user.set('name',  _this.get('name'));
					user.set('email', _this.get('email'));
					user.set('password', _this.get('newpassword'));
					
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
			} else {
				_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
				'Unable to edit user. Please verify passwords match.');
			}
		}
	}
});;App.UserRoute = App.AuthenticatedRoute.extend({});;App.UserSettingsRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find('setting', { user : this.controllerFor('application').get('currentUser.id')}).then(function(settings){
			var obj = {};
			settings.forEach(function(setting){
				var stage = setting.get('stage');
				var total = setting.get('duration');
				var days = Math.floor(total / 24);
				var hours = total % 24;
				obj["stage" + stage + "day"] = days;
				obj["stage" + stage + "hour"] = hours;
			});

			return obj;
		});
	}
});;App.UsersCreateRoute = App.AuthenticatedRoute.extend({
	setupController: function(controller, model) {
        controller.set('newUser', model);
    }
});;App.UsersEditRoute = App.AuthenticatedRoute.extend({});;App.UsersRoute = App.AuthenticatedRoute.extend({
	model : function() {
		return this.store.find('user');
	}
});;App.ModalView = Ember.View.Extend({
	didInsertElement : function() {
		this.$('.modal, .modal-backdrop').addClass('in');
	},

	layoutName : 'modal_layout',

	close : function() {
		var view = this;
		// use one of: transitionend webkitTransitionEnd oTransitionEnd
		// MSTransitionEnd
		// events so the handler is only fired once in your browser
		this.$('.modal').one("transitionend", function(ev) {
			view.controller.send('close');
		});

		this.$('.modal, .modal-backdrop').removeClass('in');
	}
});