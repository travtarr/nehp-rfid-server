App.SummaryRoute = App.AuthenticatedRoute.extend({
	model: function() {
		// grab settings based upon current user
		var _this = this;
		
		return this.store.find('item').then(function(items) {
			return Ember.$.getJSON('service/settings/' + _this.controllerFor('application').get('currentUser.id')).then(function(data){
				var setting = data.setting;
				// visible items = (now - stage_date) > stage_date_setting
				return _this.store.filter('item', function(item){
					var curStageNum = item.get('current_stage_num');
					
					var stage = item.get('last_status_change_date');
					if( stage !== null ){
						var a = moment($.now());
						var diff = a.diff(moment(stage), 'days');
						if( diff > setting['stage' + curStageNum] ){
							return item;
						}
					}
				});
			});	
		});
	}
});