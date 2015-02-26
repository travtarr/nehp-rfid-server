App.UserSettingsRoute = App.AuthenticatedRoute.extend({
	model: function() {
		return this.store.find('setting', { user : this.controllerFor('application').get('currentUser.id')}).then(function(settings){
			var obj = {};
			settings.forEach(function(setting){
				var stage = setting.get('stage');
				var total = setting.get('duration');
				var days = Math.floor(total / 24);
				var hours = total % 24;
				obj["stage" + stage + "day"] = days;
				obj["stage" + stage + "hour"] = hours;
			});

			return obj;
		});
	}
});