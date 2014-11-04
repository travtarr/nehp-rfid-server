App.NotificationsCreateController = Ember.Controller.extend({
	needs: ['application', 'sessions'],
	
	currentUsername : (function() {
		return this.get('controllers.sessions.currentUser').username;
	}).property('controllers.sessions.currentUser'),
		
	actions: {
		create: function() {
			var _this = this;

			var notification = this.store.createRecord('notification', {
				title: _this.get('newNotification.title'),
				message: _this.get('newNotification.message'),
				created_by: _this.get('currentUsername'),
				date: moment($.now()).format('YYYY-MM-DD HH:mm:ss z')
			});
			
			var onSuccess = function(){
				_this.get('controllers.application').send('setNotification', 'success', 'Success', 
				'Notification created successfully.');
				_this.transitionToRoute('notifications');
			};
			
			var onFail = function(notification){
				_this.store.deleteRecord(notification);
				_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
				'Unable to create notificatoin.');
				_this.transitionToRoute('notifications');
			};
			
			notification.save().then(onSuccess, onFail);
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
		}
	}
});