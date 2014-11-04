// Application
window.App = Ember.Application.create({
	LOG_TRANSITIONS : true, // debugging == true
	LOG_TRANSITIONS_INTERNAL : true,
	LOG_ACTIVE_GENERATION : true,
	LOG_RESOLVER : true,
	rootElement : 'body'
});

// Adapter
App.ApplicationAdapter = DS.RESTAdapter.extend({
	namespace : "service"
});

App.ApiKeyAdapter = DS.LSAdapter.extend({
	namespace : 'nehp-rfid'
});

// Store
App.Store = DS.Store.extend({
	revision : 12,
	adapter : App.ApplicationAdapter.create()
});

App.SimpledateTransform = DS.Transform.extend({
		
	// from server
	deserialize : function(serialized) {
		var tz = jstz.determine();
		if (serialized) {
			return moment.tz(serialized, tz.name()).format('YYYY-MM-DD HH:mm:ss z');
		}
		return serialized;
	},
	
	// send to server
	serialize : function(deserialized) {
		if (deserialized) {
			return moment.utc(deserialized).toDate();
		}
		return deserialized;
	}
});