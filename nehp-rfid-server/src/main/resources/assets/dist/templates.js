Ember.TEMPLATES["admin"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});

Ember.TEMPLATES["application"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, self=this, helperMissing=helpers.helperMissing, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  
  data.buffer.push("NEHP Worldwide Tracker");
  }

function program3(depth0,data) {
  
  
  data.buffer.push("Summary");
  }

function program5(depth0,data) {
  
  
  data.buffer.push("Status");
  }

function program7(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push(" \r\n							");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(8, program8, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "user", options) : helperMissing.call(depth0, "link-to", "user", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n							");
  stack1 = helpers['if'].call(depth0, "isAdmin", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(10, program10, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n							<button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "logout", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(" class=\"btn-logout\">Logout</button>\r\n						");
  return buffer;
  }
function program8(depth0,data) {
  
  var stack1;
  stack1 = helpers._triageMustache.call(depth0, "user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  else { data.buffer.push(''); }
  }

function program10(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push("\r\n								");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(11, program11, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "admin", options) : helperMissing.call(depth0, "link-to", "admin", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n							");
  return buffer;
  }
function program11(depth0,data) {
  
  
  data.buffer.push("Admin");
  }

function program13(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push(" \r\n							");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(14, program14, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "sessions", options) : helperMissing.call(depth0, "link-to", "sessions", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n						");
  return buffer;
  }
function program14(depth0,data) {
  
  
  data.buffer.push("Login");
  }

  data.buffer.push("<div id=\"wrap\">\r\n		<section id=\"rfid\">\r\n		\r\n			<!--  Navigation bar -->\r\n			<div class=\"navbar navbar-inverse navbar-fixed-top\">\r\n				<div class=\"navbar-inner\">\r\n        <div class=\"container\">\r\n					");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'classNames': ("pull-left pad-lg link title-link")
  },hashTypes:{'classNames': "STRING"},hashContexts:{'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "index", options) : helperMissing.call(depth0, "link-to", "index", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n					<div class=\"nav-collapse collapse\">\r\n					<ul class=\"nav\">\r\n						");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("pad-sm link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(3, program3, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "summary", options) : helperMissing.call(depth0, "link-to", "summary", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("pad-sm link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(5, program5, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "status", options) : helperMissing.call(depth0, "link-to", "status", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n					</ul>\r\n					<ul class=\"nav pull-right\">\r\n						");
  stack1 = helpers['if'].call(depth0, "isAuthenticated", {hash:{},hashTypes:{},hashContexts:{},inverse:self.program(13, program13, data),fn:self.program(7, program7, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n					</ul>\r\n          </div>\r\n        </div>\r\n				</div>\r\n			</div>\r\n			<!-- /Navigation Bar -->\r\n\r\n			<!-- Content -->\r\n			<div class=\"container\">\r\n				<section id=\"content\">\r\n					<!--  Page Contents from Ember -->\r\n					");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				</section>\r\n			</div>\r\n			<!-- /Content -->\r\n\r\n		</section>\r\n	</div>\r\n	<footer class=\"container\">\r\n		<span>NEHP Worldwide, LLC. Copyright 2014.</span>\r\n	</div>");
  return buffer;
  
});

Ember.TEMPLATES["index"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n			<li class=\"header\">Date:");
  stack1 = helpers._triageMustache.call(depth0, "date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" &nbsp By:");
  stack1 = helpers._triageMustache.call(depth0, "created_by", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n			<li class=\"title\">");
  stack1 = helpers._triageMustache.call(depth0, "title", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n			<li class=\"message\">");
  stack1 = helpers._triageMustache.call(depth0, "message", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n		");
  return buffer;
  }

  data.buffer.push("<div class=\"notifications\">\r\n	<div class=\"title\">\r\n		<span>Notifications</span>\r\n	</div>\r\n	<div class=\"content\">\r\n		<ul>\r\n		");
  stack1 = helpers.each.call(depth0, {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[],types:[],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n		</ul>\r\n	</div>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["list"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n			<tr>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_stage", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "rfid", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "description", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n		");
  return buffer;
  }

  data.buffer.push("<table class=\"tables-items\">\r\n	<thead>\r\n		<th>Current Stage</th>\r\n		<th>RFID</th>\r\n		<th>Item ID</th>\r\n		<th>Description</th>\r\n		<th>Last Status Change</th>\r\n		<th>Revision</th>\r\n	</thead>\r\n	<tbody>\r\n		");
  stack1 = helpers.each.call(depth0, "filteredItems", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n	</tbody>\r\n</table>");
  return buffer;
  
});

Ember.TEMPLATES["sessions"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "login", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Sign In</legend>\r\n			<label>User ID:<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'valueBinding': ("username")
  },hashTypes:{'type': "STRING",'valueBinding': "STRING"},hashContexts:{'type': depth0,'valueBinding': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Password:<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("password"),
    'valueBinding': ("password")
  },hashTypes:{'type': "STRING",'valueBinding': "STRING"},hashContexts:{'type': depth0,'valueBinding': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Remember Me?");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'valueBinding': ("remember")
  },hashTypes:{'type': "STRING",'valueBinding': "STRING"},hashContexts:{'type': depth0,'valueBinding': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Log In")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>\r\n");
  return buffer;
  
});

Ember.TEMPLATES["status"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, helperMissing=helpers.helperMissing, self=this;

function program1(depth0,data) {
  
  
  data.buffer.push("All");
  }

function program3(depth0,data) {
  
  
  data.buffer.push("Modeling");
  }

function program5(depth0,data) {
  
  
  data.buffer.push("Material Kitting");
  }

function program7(depth0,data) {
  
  
  data.buffer.push("Manufacturing");
  }

function program9(depth0,data) {
  
  
  data.buffer.push("Quality Check");
  }

function program11(depth0,data) {
  
  
  data.buffer.push("Shipped");
  }

function program13(depth0,data) {
  
  
  data.buffer.push("Arrival");
  }

function program15(depth0,data) {
  
  
  data.buffer.push("Installed");
  }

  data.buffer.push("<div class=\"container-fluid\">\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span2\">\r\n			<ul class=\"nav nav-inverse dark-list\">\r\n				<li class=\"nav-header\">In-Process</li>\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("ALL")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("MODELING")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(3, program3, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("KITTING")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(5, program5, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("MANUFACTURING")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(7, program7, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("QAQC")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(9, program9, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("SHIPPED")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(11, program11, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("ARRIVAL")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(13, program13, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("INSTALLED")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(15, program15, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				<li class=\"divider\"></li>\r\n				<li class=\"nav-header\">Stopped</li>\r\n				");
  stack1 = (helper = helpers['query-params'] || (depth0 && depth0['query-params']),options={hash:{
    'stage': ("STOPPED")
  },hashTypes:{'stage': "STRING"},hashContexts:{'stage': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "query-params", options));
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0,depth0],types:["STRING","sexpr"],data:data},helper ? helper.call(depth0, "list", stack1, options) : helperMissing.call(depth0, "link-to", "list", stack1, options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n			</ul>\r\n		</div>\r\n		<div class=\"span10\">\r\n			");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n		</div>\r\n	</div>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["status/index"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<span class=\"light-text\">Please choose a stage.</span>");
  
});

Ember.TEMPLATES["summary"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<div class=\"summary\">\r\n	<h3>Summary</h3>\r\n	<div>\r\n	\r\n	</div>\r\n</div>");
  
});

Ember.TEMPLATES["user"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});