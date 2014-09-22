App.User = DS.Model.extend({
  name: DS.attr('string'),
  admin: DS.attr('int')
});