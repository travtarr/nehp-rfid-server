App.NotificationsController = Ember.ArrayController.extend({
	needs: ['application'],
	actions: {
		remove: function(record) {
			var _this = this;
			record.deleteRecord();
			record.save().then(onSuccess, onFail);

			var onSuccess = function() {
				_this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'Notificaiton deleted.');
			};
			
			var onFail = function(error) {
				_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
				'Unable to delete this notificatoin.');
				record.rollback();
			};
		}
	}
});