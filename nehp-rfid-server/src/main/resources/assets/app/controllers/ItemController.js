App.ItemController = Ember.ObjectController.extend({
	itemid: function() {
		return this.model.get('id');
	}.property('model')
});