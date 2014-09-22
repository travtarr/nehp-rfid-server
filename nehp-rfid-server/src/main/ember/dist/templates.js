Ember.TEMPLATES["app/templates/admin"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});

Ember.TEMPLATES["app/templates/index"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<h1>Welcome!</h1>");
  
});

Ember.TEMPLATES["app/templates/login"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<div class=\"centered-box\">\r\n	<form name=\"form-login\">\r\n		<input type=\"text\" name=\"userID\"> <input type=\"password\"\r\n			name=password\"> <input type=\"submit\">\r\n	</form>\r\n</div>\r\n");
  
});

Ember.TEMPLATES["app/templates/status"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n			<tr>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "currentStage", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "rfid", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "itemId", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "description", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "lastStatusChange", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "currentRevision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n		");
  return buffer;
  }

  data.buffer.push("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\"\r\n	data-toggle=\"dropdown\">Status<b class=\"caret\"></b></a>\r\n	<ul class=\"dropdown-menu\">\r\n		<li class=\"nav-header\">In-Process</li>\r\n		<li><a href=\"#\">Modeling</a></li>\r\n		<li><a href=\"#\">Material Kitting</a></li>\r\n		<li><a href=\"#\">Manufacturing</a></li>\r\n		<li><a href=\"#\">Manufacturing Completed</a></li>\r\n		<li><a href=\"#\">Shipped</a></li>\r\n		<li><a href=\"#\">Arrival</a></li>\r\n		<li><a href=\"#\">Installed</a></li>\r\n		<li class=\"divider\"></li>\r\n		<li class=\"nav-header\">Stopped</li>\r\n		<li><a href=\"#\">All</a></li>\r\n	</ul>\r\n</li>\r\n<table>\r\n	<thead>\r\n		<th>Current Stage</th>\r\n		<th>RFID</th>\r\n		<th>Item ID</th>\r\n		<th>Description</th>\r\n		<th>Last Status Change</th>\r\n		<th>Revision</th>\r\n	</thead>\r\n	<tbody>\r\n		");
  stack1 = helpers.each.call(depth0, "item", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n	</tbody>\r\n</table>");
  return buffer;
  
});

Ember.TEMPLATES["app/templates/summary"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<div class=\"notifications\">\r\n	<h2>Notifications</h2>\r\n</div>\r\n<div class=\"summary\">\r\n	<h2>Summary</h2>\r\n</div>");
  
});

Ember.TEMPLATES["app/templates/user"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});