App.NotificationsController = Ember.ArrayController.extend({
	actions: {
		create: function() {
			var _this = this;

			this.store.createRecord('notification', {
				title: _this.get('title'),
				message: _this.get('message'),
				created_by: _this.controllerFor('sessions').get('currentUser'),
				date: $.now()
			});
		},
		edit: function(id) {
			var _this = this;

			this.store.find('notification', id).then( function(notification) {
				notification.set('title',  _this.get('title'));
				notification.set('message', _this.get('message'));
				notification.set('created_by', _this.controllerFor('sessions').get('currentUser'));
				notification.set('date', $.now());
				
				notification.save();
			});
		},
		remove: function(id) {
			this.store.find('notification', id).then(function (notification) {
				  notification.destroyRecord();
			});
		}
	}
});