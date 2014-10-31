App.NotificationsEditController = Ember.ObjectController.extend({
	needs : ['application'],
	actions : {
		edit: function(id) {
			var _this = this;

			this.store.find('notification', id).then( function(notification) {
				notification.set('title',  _this.get('title'));
				notification.set('message', _this.get('message'));
				notification.set('created_by', _this.get('controllers.application.currentUser').username);
				notification.set('date', moment($.now()).format('MMM Do YY, h:mm:ss a'));
				
				notification.save().then(onSuccess, onFail);
				
				var onSuccess = function(){
					_this.controllerFor('application').send('setNotification', 'success', 'Success', 
					'Notification updated successfully.');
					_this.transitionToRoute('notifications');
				};
				
				var onFail = function(error){
					notification.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failre', 
					'Unable to edit notificatoin.');
					_this.transitionToRoute('notifications');
				};
			});
		},
		
		cancel: function() {
			this.transitionToRoute('notifications');
		}
	}
});