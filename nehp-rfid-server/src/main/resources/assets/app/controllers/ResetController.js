App.ResetController = Ember.Controller.extend({
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
});