App.StatusItemRoute = App.AuthenticatedRoute.extend({
	model: function(params){
		return Ember.$.getJSON('service/status/item' + params.item);
	}
});