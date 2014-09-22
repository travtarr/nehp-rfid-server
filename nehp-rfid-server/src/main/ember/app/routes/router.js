App.Router.map(function() {
  this.route("summary", { path:"/summary" }); 
  this.resource("status", { path:"/status" }, function() {
    this.route("item", { path:"/:item" });
  });
  this.route("user", { path:"/user" });
  this.route("admin", { path:"/admin" });
  this.route("login", { path:"/login" });
});
App.IndexRoute = Ember.Route.extend({
  redirect: function(){
	this.transitionTo('summary');
  }
});