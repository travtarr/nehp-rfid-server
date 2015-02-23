App.ItemRoute = App.AuthenticatedRoute.extend({
	model: function(params) {
		return this.store.find('item', params.item_id);
	},
	afterModel: function(){
		this.transitionTo('stages');
	},
	actions: {
		showImage: function(stage) {
			var imageData = "data:image/gif;base64,"  + $.getJSON("/service/signature/" + this.currentModel.get('id') + "/" + stage);
			
			this.set('image', imageData);
			this.render('modal', { into: 'application', outlet: 'modal' });
		},
		closeImage: function() {
		    this.render('nothing', { into: 'application', outlet: 'modal' });
		}
	}
});