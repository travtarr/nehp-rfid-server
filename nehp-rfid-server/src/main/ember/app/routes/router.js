App.Router.map(function() {
  this.route('index', { path: '/' });
  
  this.resource("main", { path:"/main" }, function () {  

    this.route("summary", { path:"/summary" }); 
    this.route("list", { path:"/list" });
    this.route("user", { path:"/user" });
    this.route("admin", { path:"/admin" });
    this.route("login", { path:"/login" });
  });
});