Ember.TEMPLATES["admin"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data) {
  
  
  data.buffer.push("Notifications");
  }

function program3(depth0,data) {
  
  
  data.buffer.push("Users");
  }

function program5(depth0,data) {
  
  
  data.buffer.push("Settings");
  }

  data.buffer.push("<div class=\"nav-admin\">\r\n	<ul>\r\n		");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("button")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "notifications", options) : helperMissing.call(depth0, "link-to", "notifications", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n		");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("button")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(3, program3, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "users", options) : helperMissing.call(depth0, "link-to", "users", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n		");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("button")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(5, program5, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "admin.settings", options) : helperMissing.call(depth0, "link-to", "admin.settings", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n	</ul>\r\n</div>\r\n<div class=\"row-fluid\">\r\n	");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["admin/settings"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"user-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "edit", "", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Summary Settings - Overdue Limits</legend>\r\n			<label>Integration<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage1day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage1hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Kitting<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage2day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage2hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Awaiting Mfg<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage3day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage3hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Manufacturing<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage4day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage4hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>QA/QC<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage5day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage5hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Shipping<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage6day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage6hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>On Hold<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage0day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage0hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "cancel", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">Cancel</button>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Submit Changes")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["application"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, self=this, helperMissing=helpers.helperMissing, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  
  data.buffer.push("NEHP Tracker");
  }

function program3(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push(" \r\n								");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("pad-sm link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(4, program4, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "summary", options) : helperMissing.call(depth0, "link-to", "summary", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n								");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("pad-sm link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(6, program6, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "items", options) : helperMissing.call(depth0, "link-to", "items", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n							");
  return buffer;
  }
function program4(depth0,data) {
  
  
  data.buffer.push("Summary");
  }

function program6(depth0,data) {
  
  
  data.buffer.push("Status");
  }

function program8(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push(" \r\n								");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(9, program9, data),contexts:[depth0,depth0],types:["STRING","ID"],data:data},helper ? helper.call(depth0, "user", "currentUser.id", options) : helperMissing.call(depth0, "link-to", "user", "currentUser.id", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n								");
  stack1 = helpers['if'].call(depth0, "isAdmin", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(11, program11, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n								<li ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "logout", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(" class=\"btn-logout\">Logout</li>\r\n							");
  return buffer;
  }
function program9(depth0,data) {
  
  var stack1;
  stack1 = helpers._triageMustache.call(depth0, "currentUser.username", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  else { data.buffer.push(''); }
  }

function program11(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push("\r\n									");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(12, program12, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "admin", options) : helperMissing.call(depth0, "link-to", "admin", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n								");
  return buffer;
  }
function program12(depth0,data) {
  
  
  data.buffer.push("Admin");
  }

function program14(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push(" \r\n								");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("link btn-logout")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(15, program15, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "sessions", options) : helperMissing.call(depth0, "link-to", "sessions", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n							");
  return buffer;
  }
function program15(depth0,data) {
  
  
  data.buffer.push("Login");
  }

function program17(depth0,data) {
  
  var buffer = '', helper, options;
  data.buffer.push("\r\n			<div class=\"notify-container\">\r\n				");
  data.buffer.push(escapeExpression((helper = helpers['notify-alert'] || (depth0 && depth0['notify-alert']),options={hash:{
    'notification': ("notification")
  },hashTypes:{'notification': "ID"},hashContexts:{'notification': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "notify-alert", options))));
  data.buffer.push("\r\n			</div>\r\n		");
  return buffer;
  }

  data.buffer.push("	<div id=\"wrap\">\r\n		<section id=\"rfid\">\r\n		\r\n			<!--  Navigation bar -->\r\n			<div class=\"navbar navbar-inverse navbar-fixed-top\">\r\n				<div class=\"navbar-inner\" style=\"padding-top: 10px;\">\r\n        <div class=\"container-fluid\">\r\n					");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'classNames': ("pull-left pad-lg link title-link")
  },hashTypes:{'classNames': "STRING"},hashContexts:{'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "index", options) : helperMissing.call(depth0, "link-to", "index", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n					<div>\r\n						<ul class=\"nav navbar-nav\">\r\n							");
  stack1 = helpers['if'].call(depth0, "isAuthenticated", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(3, program3, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						</ul>\r\n						<ul class=\"nav pull-right\">\r\n							");
  stack1 = helpers['if'].call(depth0, "isAuthenticated", {hash:{},hashTypes:{},hashContexts:{},inverse:self.program(14, program14, data),fn:self.program(8, program8, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						</ul>\r\n          </div>\r\n        </div>\r\n				</div>\r\n			</div>\r\n			<!-- /Navigation Bar -->\r\n\r\n			<!-- Content -->\r\n			<div class=\"container-fluid\">\r\n				<section id=\"content\">\r\n					<!--  Page Contents from Ember -->\r\n					");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n					");
  data.buffer.push(escapeExpression((helper = helpers.outlet || (depth0 && depth0.outlet),options={hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data},helper ? helper.call(depth0, "modal", options) : helperMissing.call(depth0, "outlet", "modal", options))));
  data.buffer.push("\r\n				</section>\r\n			</div>\r\n			<!-- /Content -->\r\n		</section>\r\n		");
  stack1 = helpers.unless.call(depth0, "notification.closed", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(17, program17, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n	</div>\r\n	<footer>\r\n		<div class=\"container\" id=\"footer\">\r\n			<span>NEHP Worldwide, LLC. &copy; 2015.</span>\r\n		</div>\r\n	</div>");
  return buffer;
  
});

Ember.TEMPLATES["change"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "change", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Please choose a new password</legend>\r\n			<label>Password<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("password"),
    'value': ("password"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Verify Password<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("password"),
    'value': ("verify-password"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Reset Password")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["components/modal-image"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, escapeExpression=this.escapeExpression;


  data.buffer.push("<div class=\"overlay\" ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "close", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push("></div>\r\n<div class=\"modal\">\r\n	");
  stack1 = helpers._triageMustache.call(depth0, "yield", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n<div>");
  return buffer;
  
});

Ember.TEMPLATES["components/notify-alert"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, escapeExpression=this.escapeExpression;


  data.buffer.push("<ul>\r\n	<li>");
  stack1 = helpers._triageMustache.call(depth0, "notification.title", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n	<li>");
  stack1 = helpers._triageMustache.call(depth0, "notification.message", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n</ul>\r\n<button class=\"icon\" ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "close", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push("><img src=\"assets/images/exit-icon.png\" alt=\"Exit\"></button>\r\n\r\n");
  return buffer;
  
});

Ember.TEMPLATES["index"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n			<div class=\"notification\">\r\n				<ul>\r\n					<li class=\"header\"><b>Date:</b> ");
  stack1 = helpers._triageMustache.call(depth0, "date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" &nbsp <b>By:</b> ");
  stack1 = helpers._triageMustache.call(depth0, "created_by", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n					<li class=\"title\">");
  stack1 = helpers._triageMustache.call(depth0, "title", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n					<li class=\"message\">");
  stack1 = helpers._triageMustache.call(depth0, "message", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</li>\r\n				</ul>\r\n			</div>\r\n		");
  return buffer;
  }

  data.buffer.push("<div class=\"notifications\">\r\n	<div class=\"title\">\r\n		<span class=\"light-title\">Notifications</span>\r\n	</div>\r\n	<div class=\"content\">\r\n		");
  stack1 = helpers.each.call(depth0, {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[],types:[],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n	</div>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["item"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1;


  data.buffer.push("<div class=\"row\">\r\n	<div class=\"tables-items no-hover\">\r\n		<table>\r\n				<tr>\r\n					<th>Item</th><th>Revision</th><th>Group</th><th>Stage</th><th>Created By</th><th>Created Date</th>\r\n				</tr>\r\n				<tr>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "group", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "current_stage_desc", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "created_by", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "created_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				</tr>\r\n				<tr>\r\n					<th>Last Status Change User</th><th>Last Status Change Date</th><th>Hold Reason</th><th colspan=\"3\">Comment</th>\r\n				</tr>\r\n				<tr>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change_user", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td>");
  stack1 = helpers._triageMustache.call(depth0, "reason", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td><td colspan=\"3\">");
  stack1 = helpers._triageMustache.call(depth0, "comment", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				</tr>\r\n		</table>\r\n	</div>\r\n</div>\r\n<div class=\"row\">\r\n");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["items"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n							<tr ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "goToItem", "id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_stage_desc", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "group", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change_duration", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n							</tr>\r\n							");
  return buffer;
  }

  data.buffer.push("<div class=\"container-fluid\">\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span12\">\r\n			<div class=\"header-summary\">\r\n				<span class=\"light-title\">Current Status of All Drawings / Items</span>\r\n			</div>\r\n		</div>\r\n	</div>\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span2\">\r\n			<ul class=\"nav nav-inverse dark-list nav-pointer\">\r\n				<li class=\"nav-header\">In-Process</li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "ALL", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">All</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "INTEGRATING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Integrating</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "KITTING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Kitting</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "AWAITING MFG", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Awaiting Mfg</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "MANUFACTURING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Manufacturing</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "QA/QC", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">QA/QC</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "SHIPPING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Shipping</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "COMPLETE", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Completed</a></li>\r\n				<li class=\"divider\"></li>\r\n				<li class=\"nav-header\">On Hold</li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "ON HOLD", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">All</a></li>\r\n			</ul>\r\n			<button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "allexcel", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(" class=\"dark-blue\">Export All Data</button>\r\n			<button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "excelDuration", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(" class=\"dark-blue\">Export Durations</button>\r\n		</div>\r\n		<div class=\"span10\">\r\n			<div class=\"tables-items\">\r\n				<table>\r\n					<thead>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "current_stage_desc", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Current Stage</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Drawing / Item</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Revision</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "group", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Group</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "last_status_change_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Last Change</button></th>\r\n					</thead>\r\n				</table>\r\n				<div class=\"inner-table\">\r\n					<table class=\"table-hover\">\r\n						<tbody>\r\n							");
  stack1 = helpers.each.call(depth0, "filteredItems", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						</tbody>\r\n					</table>\r\n				</div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span12\">\r\n			<div class=\"content-body\">\r\n				");
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

Ember.TEMPLATES["loading"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  


  data.buffer.push("<div class=\"spinner\">\r\n  <div class=\"spinner-container container1\">\r\n    <div class=\"circle1\"></div>\r\n    <div class=\"circle2\"></div>\r\n    <div class=\"circle3\"></div>\r\n    <div class=\"circle4\"></div>\r\n  </div>\r\n  <div class=\"spinner-container container2\">\r\n    <div class=\"circle1\"></div>\r\n    <div class=\"circle2\"></div>\r\n    <div class=\"circle3\"></div>\r\n    <div class=\"circle4\"></div>\r\n  </div>\r\n  <div class=\"spinner-container container3\">\r\n    <div class=\"circle1\"></div>\r\n    <div class=\"circle2\"></div>\r\n    <div class=\"circle3\"></div>\r\n    <div class=\"circle4\"></div>\r\n  </div>\r\n</div>");
  
});

Ember.TEMPLATES["modal"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var stack1, helper, options, escapeExpression=this.escapeExpression, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data) {
  
  var buffer = '';
  data.buffer.push("\r\n      <p><img ");
  data.buffer.push(escapeExpression(helpers['bind-attr'].call(depth0, {hash:{
    'src': ("image")
  },hashTypes:{'src': "ID"},hashContexts:{'src': depth0},contexts:[],types:[],data:data})));
  data.buffer.push(" alt=\"signature image\"></p>\r\n      <button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "close", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">Done</button>\r\n");
  return buffer;
  }

  stack1 = (helper = helpers['modal-image'] || (depth0 && depth0['modal-image']),options={hash:{
    'action': ("close")
  },hashTypes:{'action': "STRING"},hashContexts:{'action': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "modal-image", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  else { data.buffer.push(''); }
  
});

Ember.TEMPLATES["notifications"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, self=this, helperMissing=helpers.helperMissing, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  
  data.buffer.push("<span>Create New Notification</span>");
  }

function program3(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push("\r\n								<tr>\r\n									<td>");
  stack1 = helpers._triageMustache.call(depth0, "title", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n									<td>");
  stack1 = helpers._triageMustache.call(depth0, "date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n									<td>");
  stack1 = helpers._triageMustache.call(depth0, "created_by", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n									<td>");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(4, program4, data),contexts:[depth0,depth0],types:["STRING","ID"],data:data},helper ? helper.call(depth0, "notifications.edit", "", options) : helperMissing.call(depth0, "link-to", "notifications.edit", "", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n									<td><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "remove", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push("><img src=\"assets/images/delete-icon.png\" alt=\"delete icon\"></button></td>\r\n								</tr>\r\n							");
  return buffer;
  }
function program4(depth0,data) {
  
  
  data.buffer.push("<img src=\"assets/images/edit-icon.png\" alt=\"edit icon\">");
  }

  data.buffer.push("<div class=\"span6\">\r\n	<div class=\"left-col\">\r\n		<div class=\"row\">\r\n			<div class=\"header\">\r\n				");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'classNames': ("button")
  },hashTypes:{'classNames': "STRING"},hashContexts:{'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "notifications.create", options) : helperMissing.call(depth0, "link-to", "notifications.create", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n			</div>\r\n		</div>\r\n		<div class=\"row\" style=\"margin-top: 10px\">\r\n			<div class=\"tables-items\">\r\n				<table>\r\n					<thead>\r\n						<tr>\r\n							<th>Title</th>\r\n							<th>Created</th>\r\n							<th>Author</th>\r\n							<th>Edit</th>\r\n							<th>Delete</th>\r\n						</tr>\r\n					</thead>\r\n				</table>\r\n				<div class=\"inner-table\">\r\n					<table>\r\n						<tbody>\r\n							");
  stack1 = helpers.each.call(depth0, {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(3, program3, data),contexts:[],types:[],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						</tbody>\r\n					</table>\r\n				</div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</div>\r\n<div class=\"span5\">\r\n	");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["notifications/create"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "create", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Create Notification</legend>\r\n			<label>Title<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("newNotification.title"),
    'placeholder': ("Enter the title here..."),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'placeholder': "STRING",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'placeholder': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Message<br>");
  data.buffer.push(escapeExpression((helper = helpers.textarea || (depth0 && depth0.textarea),options={hash:{
    'value': ("newNotification.message"),
    'cols': ("70"),
    'rows': ("5"),
    'placeholder': ("Enter the message here..."),
    'required': ("required")
  },hashTypes:{'value': "ID",'cols': "STRING",'rows': "STRING",'placeholder': "STRING",'required': "STRING"},hashContexts:{'value': depth0,'cols': depth0,'rows': depth0,'placeholder': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "textarea", options))));
  data.buffer.push("</label>\r\n			<label><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "cancel", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">Cancel</button>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Create")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["notifications/edit"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "edit", "", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Edit Notification</legend>\r\n			<label>Title<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("title"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Message<br>");
  data.buffer.push(escapeExpression((helper = helpers.textarea || (depth0 && depth0.textarea),options={hash:{
    'value': ("message"),
    'required': ("required"),
    'cols': ("70"),
    'rows': ("5")
  },hashTypes:{'value': "ID",'required': "STRING",'cols': "STRING",'rows': "STRING"},hashContexts:{'value': depth0,'required': depth0,'cols': depth0,'rows': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "textarea", options))));
  data.buffer.push("</label>\r\n			<label><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "cancel", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">Cancel</button>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Submit Changes")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["notifications/index"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});

Ember.TEMPLATES["reset"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "reset", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Please verify your email address to reset your password</legend>\r\n			<label>Email<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("email"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Reset Password")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>\r\n");
  return buffer;
  
});

Ember.TEMPLATES["sessions"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing, self=this;

function program1(depth0,data) {
  
  
  data.buffer.push("Forgot your password?");
  }

  data.buffer.push("<div class=\"centered-box\">\r\n<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "login", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">\r\n	<fieldset>\r\n		<legend>Sign In</legend>\r\n		<label>User ID:<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'valueBinding': ("username"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'valueBinding': "STRING",'required': "STRING"},hashContexts:{'type': depth0,'valueBinding': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		<label>Password:<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("password"),
    'valueBinding': ("password"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'valueBinding': "STRING",'required': "STRING"},hashContexts:{'type': depth0,'valueBinding': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		<label>Remember Me?: ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'checked': ("remember")
  },hashTypes:{'type': "STRING",'checked': "ID"},hashContexts:{'type': depth0,'checked': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n	</fieldset>\r\n	");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Log In")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("<br>\r\n	<br>");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "reset", options) : helperMissing.call(depth0, "link-to", "reset", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n</form>\r\n</div>\r\n");
  return buffer;
  
});

Ember.TEMPLATES["stages"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n				<tr ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "showImage", "id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "description", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "stage_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "signed_by", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "reason", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n					<td>");
  stack1 = helpers._triageMustache.call(depth0, "duration", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n				</tr>\r\n			");
  return buffer;
  }

  data.buffer.push("<div class = \"tables-items\">\r\n	<table class=\"table-hover\">\r\n		<thead>\r\n			<tr>\r\n				<th>Stage</th>\r\n				<th>Description</th>\r\n				<th>Date</th>\r\n				<th>Signed By</th>\r\n				<th>Reason</th>\r\n				<th>Duration</th>\r\n			</tr>\r\n		</thead>\r\n	</table>\r\n	\r\n	<div class=\"inner-table\">\r\n		<table>\r\n			<tbody>\r\n			");
  stack1 = helpers.each.call(depth0, "filteredItems", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n			</tbody>\r\n		</table>\r\n	</div>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["summary"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = '', stack1;
  data.buffer.push("\r\n							<tr ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "goToItem", "id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_stage_desc", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "group", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "last_status_change_duration", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n							</tr>\r\n							");
  return buffer;
  }

  data.buffer.push("<div class=\"container-fluid\">\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span12\">\r\n			<div class=\"header-summary\">\r\n				<span class=\"light-title\">Summary of Overdue Drawings / Items</span>\r\n			</div>\r\n		</div>\r\n	</div>\r\n	<div class=\"row-fluid\">\r\n		<div class=\"span2\">\r\n			<ul class=\"nav nav-inverse dark-list nav-pointer\">\r\n				<li class=\"nav-header\">In-Process</li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "ALL", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">All</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "INTEGRATING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Integrating</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "KITTING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Kitting</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "AWAITING MFG", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Awaiting Mfg</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "MANUFACTURING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Manufacturing</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "QA/QC", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">QA/QC</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "SHIPPING", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Shipping</a></li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "COMPLETE", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Completed</a></li>\r\n				<li class=\"divider\"></li>\r\n				<li class=\"nav-header\">On Hold</li>\r\n				<li><a ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "setStage", "ON HOLD", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">All</a></li>\r\n			</ul>\r\n		</div>\r\n		<div class=\"span10\">\r\n			<div class=\"tables-items\">\r\n				<table>\r\n					<thead>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "current_stage_desc", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Current Stage</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "item_id", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Drawing / Item</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "current_revision", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Revision</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "group", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Group</button></th>\r\n						<th><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "toggleSort", "last_status_change_date", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","STRING"],data:data})));
  data.buffer.push(">Last Change</button></th>\r\n					</thead>\r\n				</table>\r\n				<div class=\"inner-table\">\r\n					<table class=\"table-hover\">\r\n						<tbody>\r\n							");
  stack1 = helpers.each.call(depth0, "filteredItems", {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						</tbody>\r\n					</table>\r\n				</div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</div>\r\n");
  return buffer;
  
});

Ember.TEMPLATES["user"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data) {
  
  
  data.buffer.push("My Info");
  }

function program3(depth0,data) {
  
  
  data.buffer.push("My Summary Settings");
  }

  data.buffer.push("<div class=\"nav-admin\">\r\n	<ul>\r\n		");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("button")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "user.info", options) : helperMissing.call(depth0, "link-to", "user.info", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push(" \r\n		");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'tagName': ("li"),
    'classNames': ("button")
  },hashTypes:{'tagName': "STRING",'classNames': "STRING"},hashContexts:{'tagName': depth0,'classNames': depth0},inverse:self.noop,fn:self.program(3, program3, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "user.settings", options) : helperMissing.call(depth0, "link-to", "user.settings", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n	</ul>\r\n</div>\r\n<div class=\"row-fluid\">\r\n	");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["user/info"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"user-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "edit", "", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Edit Your Information</legend>\r\n			<label>Username<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("username"),
    'disabled': ("disabled")
  },hashTypes:{'type': "STRING",'value': "ID",'disabled': "STRING"},hashContexts:{'type': depth0,'value': depth0,'disabled': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Name<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("name"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Email<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("email"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Password<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("password"),
    'value': ("newpassword"),
    'required': ("required"),
    'placeholder': ("Enter new PW")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'placeholder': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'placeholder': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Verify Password<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("password"),
    'value': ("verify-password"),
    'required': ("required"),
    'placeholder': ("Enter new PW")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'placeholder': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'placeholder': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Admin<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'value': ("admin"),
    'checked': ("admin"),
    'disabled': ("disabled")
  },hashTypes:{'type': "STRING",'value': "ID",'checked': "ID",'disabled': "STRING"},hashContexts:{'type': depth0,'value': depth0,'checked': depth0,'disabled': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Scanner<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'value': ("scanner"),
    'checked': ("scanner"),
    'disabled': ("disabled")
  },hashTypes:{'type': "STRING",'value': "ID",'checked': "ID",'disabled': "STRING"},hashContexts:{'type': depth0,'value': depth0,'checked': depth0,'disabled': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Submit Changes")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["user/settings"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"user-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "edit", "", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Summary Settings - Overdue Limits</legend>\r\n			<label>Integration<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage1day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage1hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Kitting<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage2day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage2hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Awaiting Mfg<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage3day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage3hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Manufacturing<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage4day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage4hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>QA/QC<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage5day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage5hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>Shipping<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage6day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage6hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n			<label>On Hold<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage0day"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Day(s) ");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("stage0hour"),
    'required': ("required"),
    'class': ("small")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING",'class': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0,'class': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("Hour(s)</label>\r\n		</fieldset>\r\n		");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Submit Changes")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["users"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', stack1, helper, options, escapeExpression=this.escapeExpression, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data) {
  
  
  data.buffer.push("<span>Create New User</span>");
  }

function program3(depth0,data) {
  
  var buffer = '', stack1, helper, options;
  data.buffer.push("\r\n							<tr>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "username", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "name", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td>");
  stack1 = helpers._triageMustache.call(depth0, "email", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "resetPW", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push("><img src=\"assets/images/secrecy-icon.png\" style=\"height: 24px;\" alt=\"reset password icon\"></button></td>\r\n								<td>");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(4, program4, data),contexts:[depth0,depth0],types:["STRING","ID"],data:data},helper ? helper.call(depth0, "users.edit", "", options) : helperMissing.call(depth0, "link-to", "users.edit", "", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("</td>\r\n								<td><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "remove", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push("><img src=\"assets/images/delete-icon.png\" alt=\"delete icon\"></button></td>\r\n							</tr>\r\n						");
  return buffer;
  }
function program4(depth0,data) {
  
  
  data.buffer.push("<img src=\"assets/images/edit-icon.png\" alt=\"edit icon\">");
  }

  data.buffer.push("<div class=\"span6\">\r\n	<div class=\"left-col\">\r\n		<div class=\"row\">\r\n			<div class=\"header\">\r\n				");
  stack1 = (helper = helpers['link-to'] || (depth0 && depth0['link-to']),options={hash:{
    'classNames': ("button")
  },hashTypes:{'classNames': "STRING"},hashContexts:{'classNames': depth0},inverse:self.noop,fn:self.program(1, program1, data),contexts:[depth0],types:["STRING"],data:data},helper ? helper.call(depth0, "users.create", options) : helperMissing.call(depth0, "link-to", "users.create", options));
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n			</div>\r\n		</div>\r\n		<div class=\"row\" style=\"margin-top: 10px\">\r\n			<div class=\"tables-items\">\r\n				<table>\r\n					<thead>\r\n						<tr>\r\n							<th>Username</th>\r\n							<th>Name</th>\r\n							<th>Email</th>\r\n							<th>Reset PW</th>\r\n							<th>Edit</th>\r\n							<th>Delete</th>\r\n						</tr>\r\n					</thead>\r\n				</table>\r\n				<div class=\"inner-table\">\r\n					<table>\r\n						<tbody>\r\n						");
  stack1 = helpers.each.call(depth0, {hash:{},hashTypes:{},hashContexts:{},inverse:self.noop,fn:self.program(3, program3, data),contexts:[],types:[],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n						</tbody>\r\n					</table>\r\n				</div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</div>\r\n<div class=\"span5\">\r\n	");
  stack1 = helpers._triageMustache.call(depth0, "outlet", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0],types:["ID"],data:data});
  if(stack1 || stack1 === 0) { data.buffer.push(stack1); }
  data.buffer.push("\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["users/create"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "create", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0],types:["STRING"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Create User</legend>\r\n			<label>Username<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("newUser.username"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Name<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("newUser.name"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Email<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("newUser.email"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Admin<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'checked': ("newUser.admin")
  },hashTypes:{'type': "STRING",'checked': "ID"},hashContexts:{'type': depth0,'checked': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Scanner<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'checked': ("newUser.scanner")
  },hashTypes:{'type': "STRING",'checked': "ID"},hashContexts:{'type': depth0,'checked': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "cancel", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">Cancel</button>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Create")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["users/edit"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '', helper, options, escapeExpression=this.escapeExpression, helperMissing=helpers.helperMissing;


  data.buffer.push("<div class=\"centered-box\">\r\n	<form ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "edit", "", {hash:{
    'on': ("submit")
  },hashTypes:{'on': "STRING"},hashContexts:{'on': depth0},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">\r\n		<fieldset>\r\n			<legend>Edit User</legend>\r\n			<label>Username<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("username"),
    'disabled': ("disabled")
  },hashTypes:{'type': "STRING",'value': "ID",'disabled': "STRING"},hashContexts:{'type': depth0,'value': depth0,'disabled': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Name<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("name"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Email<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("text"),
    'value': ("email"),
    'required': ("required")
  },hashTypes:{'type': "STRING",'value': "ID",'required': "STRING"},hashContexts:{'type': depth0,'value': depth0,'required': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Admin<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'value': ("admin"),
    'checked': ("admin")
  },hashTypes:{'type': "STRING",'value': "ID",'checked': "ID"},hashContexts:{'type': depth0,'value': depth0,'checked': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label>Scanner<br>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("checkbox"),
    'value': ("scanner"),
    'checked': ("scanner")
  },hashTypes:{'type': "STRING",'value': "ID",'checked': "ID"},hashContexts:{'type': depth0,'value': depth0,'checked': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n			<label><button ");
  data.buffer.push(escapeExpression(helpers.action.call(depth0, "cancel", "", {hash:{},hashTypes:{},hashContexts:{},contexts:[depth0,depth0],types:["STRING","ID"],data:data})));
  data.buffer.push(">Cancel</button>");
  data.buffer.push(escapeExpression((helper = helpers.input || (depth0 && depth0.input),options={hash:{
    'type': ("submit"),
    'value': ("Submit Changes")
  },hashTypes:{'type': "STRING",'value': "STRING"},hashContexts:{'type': depth0,'value': depth0},contexts:[],types:[],data:data},helper ? helper.call(depth0, options) : helperMissing.call(depth0, "input", options))));
  data.buffer.push("</label>\r\n		</fieldset>\r\n	</form>\r\n</div>");
  return buffer;
  
});

Ember.TEMPLATES["users/index"] = Ember.Handlebars.template(function anonymous(Handlebars,depth0,helpers,partials,data) {
this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Ember.Handlebars.helpers); data = data || {};
  var buffer = '';


  return buffer;
  
});