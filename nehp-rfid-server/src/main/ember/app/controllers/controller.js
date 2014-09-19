App.Controller = Ember.ArrayController.extend({
	user: function() {
		return App.Auth.get('user');
	}
});