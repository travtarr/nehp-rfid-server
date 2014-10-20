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
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(5, program5, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "items", options) : helperMissing.call(depth0, "link-to", "items", options));
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

Ember.TEMPLATES["item"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1;


  data.buffer.push("<div class=\"details\">\r\n	<table>\r\n		<tbody>\r\n			<tr>\r\n				<td class=\"header\">RFID</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "rfid", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td class=\"header\">Item ID</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n			<tr>\r\n				<td class=\"header\">Created By</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "created_by", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td class=\"header\">Created Date</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "created_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n			<tr>\r\n				<td class=\"header\">Last Change Date</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td class=\"header\">Last Change User</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n			<tr>\r\n				<td class=\"header\">Current Revision</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td class=\"header\">Current Revision Date</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "current_revision_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n			<tr>\r\n				<td class=\"header\" >Current Stage</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "current_stage", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n			<tr>\r\n				<td class=\"header\">Description</td>\r\n				<td colspan=\"3\">");
  stack1 = helpers._triageMustache.call(depth0, "description", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n		</tbody>\r\n	</table>\r\n</div>\r\n<div class=\"details\">\r\n	<table>\r\n		<tbody>\r\n			<tr class=\"header\">\r\n				<td></td>\r\n				<td>Modeling</td>\r\n				<td>Kitting</td>\r\n				<td>Manufacturing</td>\r\n				<td>QA/QC</td>\r\n				<td>Shipped</td>\r\n				<td>Arrival</td>\r\n				<td>Installed</td>\r\n				<td>Stopped</td>\r\n			</tr>\r\n			<tr>\r\n				<td>User</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage1_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage2_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage3_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage4_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage5_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage6_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage7_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage0_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n			<tr>\r\n				<td>Date</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage1_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage2_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage3_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage4_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage5_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage6_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage7_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage0_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n			</tr>\r\n		</tbody>\r\n	</table>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["items"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, self=this, helperMissing=helpers.helperMissing, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push("\r\n					<tr>\r\n						<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_stage", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n						<td>");
  stack1 = helpers._triageMustache.call(depth0, "rfid", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n						<td>");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(2, program2, data),contexts:[depth0,depth0],types:["STRING","ID"],data:data},helper ? helper.call(depth0, "item", "", options) : helperMissing.call(depth0, "link-to", "item", "", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n						<td>");
  stack1 = helpers._triageMustache.call(depth0, "description", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n						<td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n						<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					</tr>\r\n					");
  return buffer;
  }
function program2(depth0,data) {
  
  var stack1;
  stack1 = helpers._triageMustache.call(depth0, "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  else { data.buffer.push(''); }
  }

  data.buffer.push("<div class=\"container-fluid\">\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span2\">\r\n			<ul class=\"nav nav-inverse dark-list\">\r\n				<li class=\"nav-header\">In-Process</li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "ALL", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">All</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "MODELING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Modeling</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "KITTING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Material Kitting</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "MANUFACTURING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Manufacturing</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "QAQC", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">QualityCheck</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "SHIPPED", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Shipped</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "ARRIVAL", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Arrival</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "INSTALLED", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Installed</a></li>\r\n				<li class=\"divider\"></li>\r\n				<li class=\"nav-header\">Stopped</li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "STOPPED", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">All</a></li>\r\n			</ul>\r\n		</div>\r\n		<div class=\"span10\">\r\n			<table class=\"tables-items\">\r\n				<thead>\r\n					<th>Current Stage</th>\r\n					<th>RFID</th>\r\n					<th>Item ID</th>\r\n					<th>Description</th>\r\n					<th>Last Change</th>\r\n					<th>Revision</th>\r\n				</thead>\r\n				<tbody>\r\n					");
  stack1 = helpers.each.call(depth0, "filteredItems", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n				</tbody>\r\n			</table>\r\n		</div>\r\n	</div>\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span12\">\r\n			<div class=\"content-body\">\r\n				");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n			</div>\r\n		</div>\r\n	</div>\r\n	<br>\r\n</div>");
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

Ember.TEMPLATES["summary"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<div class=\"summary\">\r\n	<div class=\"title\"><span>Summary</span></div>\r\n	<div>\r\n	\r\n	</div>\r\n</div>");
  
});

Ember.TEMPLATES["user"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});