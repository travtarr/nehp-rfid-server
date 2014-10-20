App.ItemsRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('item');
	}
});