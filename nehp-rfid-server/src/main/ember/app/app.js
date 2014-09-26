// Application
var App = Ember.Application.create({
		LOG_TRANSITIONS:true, // debugging == true
		rootElement:'body'
		});

// Adapter
App.ApplicationAdapter = DS.RESTAdapter.extend({});
App.ApiKeyAdapter = DS.LSAdapter.extend({
	namespace: 'emberauth-keys'
});

// Store
App.Store = DS.Store.extend({
  revision:12,
  adapter: App.ApplicationAdapter.create()
});