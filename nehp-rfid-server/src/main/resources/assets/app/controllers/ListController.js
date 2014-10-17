App.ListController = Ember.ArrayController.extend({
	queryParams: ['stage'],
	stage: 'ALL',
	filteredItems: Ember.computed.filterBy('model', 'current_stage', function(){
		return this.get('stage');
	})
});