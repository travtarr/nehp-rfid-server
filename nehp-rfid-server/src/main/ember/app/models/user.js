App.User = DS.Model.extend({
  user: DS.attr('string'),
  name: DS.attr('string'),
  admin: DS.attr('boolean')
});