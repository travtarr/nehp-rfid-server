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
			var stage = this.modelFor('stages').findBy('id', stageid);
			var imageData = "data:image/gif;base64,"  + 
			$.getJSON("/service/signature/" + stage.get('item') + "/" + stage.get('stage'));
			
			this.set('image', imageData);
			this.render('modal', { into: 'application', outlet: 'modal' });
		},
		closeImage: function() {
		    this.render('nothing', { into: 'application', outlet: 'modal' });
		}
	}
});