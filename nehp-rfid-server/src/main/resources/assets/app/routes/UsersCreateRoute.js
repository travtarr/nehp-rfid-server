App.UsersCreateRoute = App.AuthenticatedRoute.extend({
	setupController: function(controller, model) {
        controller.set('newUser', model);
    }
});