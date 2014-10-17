App.Router.map(function() {
  this.resource('summary', function(){}); 
  this.resource('status', function() {
    this.resource('list', { path: 'list:/:stage'});
    this.resource('item', { path:'/item/:item'});
  });
  this.resource('user', { path:'/user/:user_id' });
  this.resource('admin', function(){});
  this.resource('sessions', function(){
	this.route('login');
	this.route('logout');
  });
});