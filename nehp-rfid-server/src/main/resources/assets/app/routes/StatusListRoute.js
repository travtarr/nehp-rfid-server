App.StatusListRoute = App.AuthenticatedRoute.extend({
	model: function(params) {
      return Ember.$.getJSON('/status/list/' + params.list);
    }
});