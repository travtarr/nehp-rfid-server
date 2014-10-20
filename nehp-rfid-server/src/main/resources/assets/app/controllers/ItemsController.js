App.ItemsController = Ember.ArrayController.extend({
	sortProperties: ['rfid'],
	stage: 'ALL',
	filteredItems: function(){
		var stage = this.get('stage');
		var items = this.get('arrangedContent');
		
		if(stage == 'ALL')
			return items;
		else {
			return items.filter(function(item) {
				return item.get('current_stage') == stage;
			});
		}
	}.property('stage', 'arrangedContent'),
	
	actions: {
		setStage: function(stage) {
			this.set('stage', [stage]);
		}
	}
});