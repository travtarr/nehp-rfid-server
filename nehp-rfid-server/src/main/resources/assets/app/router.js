App.Router.map(function() {
  this.resource('summary'); 
  this.resource('items', function() {
    this.resource('item', { path:'/:item_id' }, function(){
       this.resource('stages');
    });
  });
  this.resource('user', { path:'/user/:user_id' }, function(){
	  this.route('info');
	  this.route('settings');
  });
  this.resource('admin', function(){
	  this.resource('notifications', function(){
		  this.route('create');
		  this.route('edit', { path: '/edit/:id'});
	  });
	  this.resource('users', function(){
		  this.route('create');
		  this.route('edit', { path: '/edit/:id'});
	  });
	  this.route('settings');
  });
  this.resource('sessions');
  this.route('reset');
  this.route('change');
});