App.ChangeController = Ember.Controller.extend({
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
});