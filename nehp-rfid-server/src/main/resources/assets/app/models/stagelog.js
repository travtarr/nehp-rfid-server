App.Stagelog = DS.Model.extend({
	item: DS.attr('number'),
	stage: DS.attr('number'),
	description: DS.attr('string'),
	stage_date: DS.attr('simpledate'),
	signed_by: DS.attr('string'),
	reason: DS.attr('string')
});