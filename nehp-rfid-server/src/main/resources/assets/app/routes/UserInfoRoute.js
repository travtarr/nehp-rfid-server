App.UserInfoRoute = App.AuthenticatedRoute.extend({
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
});