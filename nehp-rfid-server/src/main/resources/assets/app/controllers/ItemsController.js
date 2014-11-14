App.ItemsController = Ember.ArrayController.extend({
	sortProperties: ['rfid'],
	stage: 'ALL',
	sortedColumn: (function() {
	    var properties = this.get('sortProperties');
	    if(!properties) return undefined;
	    return properties.get('firstObject');
	  }).property('sortProperties.[]'),
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
		},
		
		toggleSort: function(column) {
		    if(this.get('sortedColumn') == column) {
		      this.toggleProperty('sortAscending');
		    } else {
		      this.set('sortProperties', [column]);
		      this.set('sortAscending', true);
		    }
		}
	}
});