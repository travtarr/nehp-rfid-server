App.IndexRoute = App.AuthenticatedRoute.extend({
  model: function() {
    return this.store.find('notification');
  }
});