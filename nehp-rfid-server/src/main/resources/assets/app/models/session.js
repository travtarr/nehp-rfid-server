App.Session = DS.Model.extend({
  authToken: DS.attr('string'),
  account:   DS.belongsTo('App.Account')
});