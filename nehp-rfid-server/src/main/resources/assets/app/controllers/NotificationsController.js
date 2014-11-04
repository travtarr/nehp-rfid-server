App.NotificationsController = Ember.ArrayController.extend({
	needs: ['application'],
	actions: {
		remove: function(record) {
			var _this = this;
			record.deleteRecord();
			
			var onSuccess = function() {
				console.log("successfully deleted notification");
				this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'Notificaiton deleted.');
			};
			
			var onFail = function(error) {
				this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
				'Unable to delete this notification.');
				record.rollback();
			};
			
			record.save().then(onSuccess, onFail);
		}
	}
});