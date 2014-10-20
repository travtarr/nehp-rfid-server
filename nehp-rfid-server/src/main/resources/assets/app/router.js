App.Router.map(function() {
  this.resource('summary', function(){}); 
  this.resource('items', function() {
    this.resource('item', { path:'/:item' });
  });
  this.resource('user', { path:'/user/:user_id' });
  this.resource('admin', function(){});
  this.resource('sessions', function(){
	this.route('login');
	this.route('logout');
  });
});