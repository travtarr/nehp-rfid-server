App.StagesRoute = App.AuthenticatedRoute.extend({
	setupController: function(controller) {
        var _this = this;
        var item = this.controllerFor('item').get('itemid');
        return this.get('store').find('stagelog').then(function() {
            controller.set('model', _this.get('store').filter('stagelog', function(stage){
                return stage.get('item') == item;
            }));
        });
    },
	actions: {
		showImage: function(stageid) {
			var _this = this;
			Ember.$.get("/service/signature?stage=" + stageid).done( function(result) {
				_this.send('openModal', 'modal', Em.Object.create({'image': result}));
			});
		}
	}
});