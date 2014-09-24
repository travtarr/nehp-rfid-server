App.ListRoute = Ember.Route.extend({
  //authRedirectable: true,
  model: function(params){
	return this.store.findAll('item');
  }
});