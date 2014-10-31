App.UsersController = Ember.ArrayController.extend({
	needs: ['application'],
	actions : {
		resetPW : function(id) {
			// TODO: add resource method to reset PW

		},
		remove : function(record) {
			var _this = this;
			record.deleteRecord();
			record.save().then(onSuccess, onFail);

			var onSuccess = function() {
				_this.get('controllers.application').send('setNotification',
						'success', 'Success', 'User deleted.');
			};

			var onFail = function(error) {
				_this.get('controllers.application').send('setNotification',
						'failure', 'Failed',
						'Unable to delete this user.');
				record.rollback();
			};
		}
	}
});