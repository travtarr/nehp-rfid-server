App.UsersEditController = Ember.ObjectController.extend({
	needs: ['application'],
	
	actions : {
		edit: function(id) {
			var _this = this;

			this.store.find('user', id).then( function(user) {
				user.set('name', _this.get('name'));
				user.set('email', _this.get('email'));
				user.set('admin', _this.get('admin'));
				user.set('scanner', _this.get('scanner'));
				
				user.save().then(onSuccess, onFail);
				
				var onSuccess = function(){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'User updated successfully.');
					_this.transitionToRoute('users');
				};
				
				var onFail = function(error){
					notification.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this user.');
					_this.transitionToRoute('users');
				};
			});
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
		}
	}
});