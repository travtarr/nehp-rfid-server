App.LoginController = Ember.Controller.extend({
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
});