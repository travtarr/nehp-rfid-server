App.Router.map(function() {
  this.route("summary", { path:"/summary" }); 
  this.resource("status", { path:"/status" }, function() {
    this.resource("list", { path:"/list/:list" });
    this.resource("item", { path:"/item/:item"});
  });
  this.resource("user", { path:"/user/:user_id" });
  this.route("admin", { path:"/admin" });
  this.resource("sessions", function(){
	this.route("login"};
	this.route("logout");
  });
  
});