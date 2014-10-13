App.Notification = DS.Model.extend({
  title: DS.attr('string'),
  date: DS.attr('date'),
  created_by: DS.attr('string'),
  message: DS.attr('string')
});