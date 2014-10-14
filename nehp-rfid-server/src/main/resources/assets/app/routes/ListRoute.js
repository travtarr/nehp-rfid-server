App.ListRoute = App.AuthenticatedRoute.extend({
	queryParams: {
	  category: {
	    refreshModel: true
	  }
	},
	model: function(params) {
      return this.store.find("item", params);
    }
});