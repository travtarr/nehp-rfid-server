App.UserSettingsRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find('setting', this.controllerFor('application').get('currentUser.setting') ).then(function(setting){
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

			this.store.find( 'setting', this.controllerFor('application').get('currentUser.setting') ).then( function(setting) {

				// Update the model
				for (var i = 0; i < 8; i++) {
					var total = (parseInt(_this.get('stage' + i + 'day'), 10) * 24) + parseInt(_this.get('stage' + i + 'hour'), 10);
					setting.set('stage' + i, total);
				}
				
				var onSuccess = function(){
					_this.controllerFor('application').send('setNotification', 'success', 'Success', 
					'Setting updated successfully.');
					_this.transitionToRoute('settings');
				};
				
				var onFail = function(error){
					setting.rollback();
					_this.controllerFor('application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this setting.');
					_this.transitionToRoute('settings');
				};
				
				setting.save().then(onSuccess, onFail);
			});
		}
	}
});