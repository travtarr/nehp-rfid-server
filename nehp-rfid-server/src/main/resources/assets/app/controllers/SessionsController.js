App.SessionsController = Ember.Controller.extend({
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
});