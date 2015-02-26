App.UsersEditController = Ember.ObjectController.extend({
	needs: ['application'],
	
	actions : {
		edit: function(obj) {
			var _this = this;

			this.store.find('user', obj.id).then( function(user) {
				user.set('name', _this.get('name'));
				user.set('email', _this.get('email'));
				user.set('admin', _this.get('admin'));
				user.set('scanner', _this.get('scanner'));
				
				
				var onSuccess = function(){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'User updated successfully.');
					_this.transitionToRoute('users');
				};
				
				var onFail = function(error){
					user.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this user.');
					_this.transitionToRoute('users');
				};
				
				user.save().then(onSuccess, onFail);
			});
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
		}
	}
});