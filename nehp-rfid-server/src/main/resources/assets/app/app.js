// Application
window.App = Ember.Application.create({
		LOG_TRANSITIONS:true, // debugging == true
		LOG_TRANSITIONS_INTERNAL: true,
		LOG_ACTIVE_GENERATION: true,
		LOG_RESOLVER: true,
		rootElement:'body'
		});

// Adapter
App.ApplicationAdapter = DS.RESTAdapter.extend({
	namespace: "service"
});

App.ApiKeyAdapter = DS.LSAdapter.extend({
	namespace: 'nehp-rfid'
});

// Store
App.Store = DS.Store.extend({
  revision:12,
  adapter: App.ApplicationAdapter.create()
});