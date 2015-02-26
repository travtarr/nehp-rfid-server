App.StagesController = Ember.ArrayController.extend({
	filteredItems: function(){
		var items = this.get('arrangedContent');
		var prevItem = null;
		items.forEach(function(item){
			if (prevItem !== null){
				var prevStage = prevItem.get('stage_date');
				var stage = item.get('stage_date');
				var a = moment(prevStage, "YYYY-MM-DD HH:mm:ss");
				var b = null;
				if( stage === null ){
					b = moment($.now());
				} else {
					b = moment(stage, "YYYY-MM-DD HH:mm:ss");
				}
				var diff = b.diff(a, 'hours');
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
					item.set('duration', days + " " + dayStr +
							", " + hours + " " + hourStr);
				} else {
					days = 0;
					hours = diff;
					if (hours == 1)
						hourStr = "hour";
					item.set('duration', hours + " " + hourStr);
				}
			}
			prevItem = item;
		});
		return items;
		
	}.property('arrangedContent')
});