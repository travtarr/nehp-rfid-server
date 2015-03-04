App.SessionsController = Ember.Controller.extend({
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
		} else if (sessionStorage.token !== null) {
			Ember.$.ajaxSetup({
				headers : {
					'Authorization' : 'Bearer ' + sessionStorage.token
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
		} else if (this.get('rawToken') === null) {
			return sessionStorage.getItem('token');
		} else 
			return this.get('rawToken');
	}).property('rawToken'),
	
	currentUser : (function() { 
		if(Ember.$.cookie('auth_user')){
			if(typeof Ember.$.cookie('auth_user') == 'string')
				return JSON.parse(Ember.$.cookie('auth_user'));
			else
				return Ember.$.cookie('auth_user');
		} else if (sessionStorage.getItem('user') !== null){
			return JSON.parse(sessionStorage.user);
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
		} else if (this.get('user.admin') === null) {
			return this.get('currentUser.admin');
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
								var userToAdd = {
										id: response.api_key[0].user_id.string,
										setting: user.get('setting'),
										name: user.get('name'),
										email: user.get('email'),
										username: user.get('username'),
										admin: user.get('admin')
								};								
								// set this controller token & current user properties
								// based on the data from the user and access_token
								if ( typeof(Storage) !== "undefined" ) {
									sessionStorage.token = response.api_key[0].access_token.string;
									sessionStorage.user = JSON.stringify(userToAdd);
								}
								_this.setProperties({
									rawToken: response.api_key[0].access_token.string,
									token: response.api_key[0].access_token.string,
									currentUser : userToAdd,
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
});