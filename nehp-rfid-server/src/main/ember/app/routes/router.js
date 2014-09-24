App.Router.map(function() {
  this.route("summary", { path:"/summary" }); 
  this.resource("status", { path:"/status" }, function() {
    this.resource("list", { path:"/list/:list" });
    this.resource("item", { path:"/item/:item"});
  });
  this.resource("user", { path:"/user" });
  this.route("admin", { path:"/admin" });
  this.route("login", { path:"/login" });
});