App.SummaryController = Ember.ArrayController.extend({
	needs: ['application'],
	
	actions: {
		testNotify: function() {
			this.get('controllers.application').send('setNotification', 'success', 'Success', 
			'Notificaiton deleted.');
		}
	}
});