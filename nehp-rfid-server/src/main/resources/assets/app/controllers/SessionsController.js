App.LoginController = Ember.Controller.extend({
	data: null,
	
	init: function(){
			this._super();
			if (Ember.$.cookie('access_token')) {
			    Ember.$.ajaxSetup({
			      headers: {
			        'Authorization': 'Bearer ' + Ember.$.cookie('access_token')
			      }
			    });
			}	
		},
		
	// overwriting the default attemptedTransition attribute from the Ember.Controller object
  	attemptedTransition: null,

  	// create and set the token & current user objects based on the respective cookies
  	token:               Ember.$.cookie('access_token'),
  	currentUser:         Ember.$.cookie('auth_user'),
	
	tokenChanged: (function() {
		  if (Ember.isEmpty(this.get('token'))) {
		    Ember.$.removeCookie('access_token');
		    Ember.$.removeCookie('auth_user');
		  } else {
		    Ember.$.cookie('access_token', this.get('token'));
		    Ember.$.cookie('auth_user', this.get('currentUser'));
		  }
	}).observes('token'),
	
	// reset the controller properties and the ajax header
	reset: function() {
	  this.setProperties({
	    username:          null,
	    password:          null,
	    token:             null,
	    currentUser:       null
	  });
	  Ember.$.ajaxSetup({
	    headers: {
	      'Authorization': 'Bearer none'
	    }
	  });
	},
	
	actions: {
		 		
		login: function() {
			var _this = this;
			var attemptedTrans = this.get('attemptedTransition');
			  		  
			data = new FormData();
			data.append('grantType', 'password');
			data.append('username', this.get('username'));
			data.append('password', this.get('password'));
			  
			Ember.$.post('/service/auth/token', data).then(function(response) {
				// set the ajax header with the returned access_token object
				Ember.$.ajaxSetup({
					headers: {
						'Authorization': 'Bearer ' + response
				    }
				});
			
				}, function(error) {
					if (error.status === 401) {
						// if there is a authentication error the user is
						// informed of it to try again
						alert("wrong user or password, please try again");
				}
			});
			
			this.setProperties({
				username: null,
				password: null
			})
			
			var key = _this.get('store').createRecord('apiKey', {
				accessToken: response.api_key.access_token
			});
			
			_this.store.find('user', response)
		}
	}
});