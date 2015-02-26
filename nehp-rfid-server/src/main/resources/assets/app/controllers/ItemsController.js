App.ItemsController = Ember.ArrayController.extend({
	sortProperties: ['item_id'],
	stage: 'ALL',
	sortedColumn: (function() {
	    var properties = this.get('sortProperties');
	    if(!properties) return undefined;
	    return properties.get('firstObject');
	  }).property('sortProperties.[]'),
	filteredItems: function(){
		var stage = this.get('stage');
		var items = this.get('arrangedContent');
		items.forEach(function(item){
			var stage = item.get('last_status_change_date');
			if( stage !== null ){
				var a = moment($.now());
				var diff = a.diff(moment(stage, "YYYY-MM-DD HH:mm:ss"), 'hours');
				var days = 0;
				var hours = 0;
				var dayStr = "days";
				var hourStr = "hours";
				if (diff > 24){
					days = Math.floor(diff / 24);
					hours = (diff - (days * 24));	
					if (hours == 1)
						hourStr = "hour";
					if (days == 1)
						dayStr = "day";
					item.set('last_status_change_duration', days + " " + dayStr +
							", " + hours + " " + hourStr + " ago");
				} else {
					days = 0;
					hours = diff;
					if (hours == 1)
						hourStr = "hour";
					item.set('last_status_change_duration', hours + " " + hourStr + " ago");
				}
			} else {
				item.set('last_status_change_duration', "");
			}
		});
		if(stage == 'ALL')
			return items;
		else {
			return items.filter(function(item) {
				return item.get('current_stage_desc') == stage;
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
		},
		
		goToItem: function(id) {
			this.transitionToRoute('item', id); 
		}
	}
});