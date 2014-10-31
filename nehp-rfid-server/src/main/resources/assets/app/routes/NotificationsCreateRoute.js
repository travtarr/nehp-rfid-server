App.NotificationsCreateRoute = App.AuthenticatedRoute.extend({	
	setupController: function(controller, model) {
        controller.set('newNotification', model);
    }
});