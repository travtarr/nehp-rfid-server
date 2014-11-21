App.ResetRoute = Ember.Route.extend({
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
});