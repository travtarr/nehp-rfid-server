App.UsersController = Ember.ArrayController.extend({
	actions: {
		create: function() {
			var _this = this;

			this.store.createRecord('user', {
				username: _this.get('username'),
				name: _this.get('name'),
				email: _this.get('email'),
				admin: _this.get('admin'),
				scanner: _this.get('scanner'),
				user_created_date: $.now()
			});
		},
		edit: function(id) {
			var _this = this;

			this.store.find('user', id).then( function(user) {
				user.set('name', _this.get('name'));
				user.set('email', _this.get('email'));
				user.set('admin', _this.get('admin'));
				user.set('scanner', _this.get('scanner'));
				
				user.save();
			});
		},
		resetPW: function(id) {
			// TODO: add resource method to reset PW
			
		},
		remove: function(id) {
			store.find('user', id).then(function (user) {
				  user.destroyRecord();
			});
		}
	}
});