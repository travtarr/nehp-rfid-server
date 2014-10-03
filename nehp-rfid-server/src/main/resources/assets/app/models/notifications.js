App.Notifications = DS.Model.extend({
  date: DS.attr('date'),
  author: DS.attr('string'),
  message: DS.attr('string')
});