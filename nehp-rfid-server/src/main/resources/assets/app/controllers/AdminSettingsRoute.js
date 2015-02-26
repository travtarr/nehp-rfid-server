App.AdminSettingsController = Ember.ObjectController.extend({
	needs: ['application'],
	actions: {
		edit: function() {	
			var _this = this;

			this.store.find( 'setting', { user : this.get('controllers.application.currentUser').id } ).then( function(settings) {
				// Update the model
				console.log(settings);
				settings.forEach(function(setting) {
					var stage = setting.get('stage');
					console.log(stage);
					var total = (Math.floor(parseInt(_this.get('stage' + stage + 'day'), 10)) * 24) + 
						Math.floor(parseInt(_this.get('stage' + stage + 'hour'), 10));
					setting.set('duration', total);
				});
				
				var onSuccess = function(){
					_this.get('controllers.application').send('setNotification', 'success', 'Success', 
					'Setting updated successfully.');
					_this.transitionToRoute('settings');
				};
				
				var onFail = function(error){
					settings.rollback();
					_this.get('controllers.application').send('setNotification', 'failure', 'Failed', 
					'Unable to edit this setting.');
					_this.transitionToRoute('settings');
				};
				
				settings.save().then(onSuccess, onFail);
			});
		}
	}
});