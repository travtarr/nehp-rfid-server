App.AdminSettingsRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find('setting', { admin: true }).then(function(settings){
			var setting = settings.get('content')[0];
			var obj = {};
			for (var i = 0; i < 8; i++) {
				var total = setting.get('stage' + i);
				// need to round down since hours gets the remainder
				var days = Math.floor(total / 24);
				var hours = total % 24;
				obj["stage" + i + "day"] = days;
				obj["stage" + i + "hour"] = hours;
			}
			return obj;
		});
	},
	actions: {
		edit: function() {
			var _this = this;

			this.store.find('setting', { admin: true }).then( function(settings) {
				var setting = settings.get('content')[0];
				
				// Update the model
				for (var i = 0; i < 8; i++) {
					var total = (_this.get('stage' + i + 'day') * 24) + _this.get('stage' + i + 'hour');
					setting.set('stage' + i, total);
				}
								
				Ember.$.ajax({
					url: '/service/settings/admin',
					data: setting,
					dataType: 'json',
					type: 'PUT',
					success: function (response) {
						_this.controllerFor('application').send('setNotification', 'success', 'Success', 
						'Setting updated successfully.');
						_this.transitionToRoute('settings');
					},
					error: function (error) {
						_this.controllerFor('application').send('setNotification', 'failure', 'Failed', 
						'Unable to edit this setting.');
						_this.transitionToRoute('settings');
					}
				});
			});
		}
	}
});