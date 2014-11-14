App.ItemRoute = App.AuthenticatedRoute.extend({
	model: function(params) {
		return this.store.find('item', params.item_id);
	}
});