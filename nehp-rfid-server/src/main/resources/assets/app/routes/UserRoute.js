App.UserRoute = App.AuthenticatedRoute.extend({
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
});