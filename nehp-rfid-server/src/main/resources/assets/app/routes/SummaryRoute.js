App.SummaryRoute = App.AuthenticatedRoute.extend({
	model: function() {
		// grab settings based upon current user
		var _this = this;
		
		return Ember.$.getJSON('service/settings/' + this.controllerFor('application').get('currentUser.id')).then(function(data){
			var setting = data.setting;
			
			
			// visible items = (now - stage_date) > stage_date_setting
			return _this.store.filter('item', function(item){
				var curStage = item.get('current_stage');
				var curStageNum = 0;
				switch(curStage){
					case 'STOPPED':
						curStageNum = 0;
						break;
					case 'MODELING':
						curStageNum = 1;
						break;
					case 'KITTING':
						curStageNum = 2;
						break;
					case 'MANUFACUTURING':
						curStageNum = 3;
						break;
					case 'QA/QC':
						curStageNum = 4;
						break;
					case 'SHIPPED':
						curStageNum = 5;
						break;
					case 'ARRIVAL':
						curStageNum = 6;
						break;
					case 'INSTALLED':
						curStageNum = 7;
						break;
				}
				var stage = item.get('stage' + curStageNum + '_date');
				if( stage !== null){
					var a = moment($.now());
					var diff = a.diff(moment(stage), 'days');
					if( diff > setting[curStage] ){
						return item;
					}
				}
			});
		});	
	}
});