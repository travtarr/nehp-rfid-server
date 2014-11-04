App.UsersCreateController = Ember.Controller.extend({
	needs: ['application'],
	
	actions: {
		create: function() {
			var _this = this;
			
			var user = this.store.createRecord('user', {
				username: _this.get('newUser.username'),
				name: _this.get('newUser.name'),
				email: _this.get('newUser.email'),
				admin: _this.get('newUser.admin'),
				scanner: _this.get('newUser.scanner'),
				user_created_date: moment($.now()).format('YYYY-MM-DD HH:mm:ss z')
			});
			
			var onSuccess = function(){
				_this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'User created successfully.');
				_this.transitionToRoute('users');
			};
			
			var onFail = function(user){
				_this.store.deleteRecord(user);
				_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
						'Unable to create this user');
				_this.transitionToRoute('users');
			};
			
			user.save().then(onSuccess, onFail);
		},
		
		cancel: function() {
			this.setProperties({
				username: null,
				name: null,
				email: null,
				admin: null,
				scanner: null
			});
			this.transitionToRoute('users');
		}
	}
});