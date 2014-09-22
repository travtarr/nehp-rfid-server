/**
 * 
 */
App.MainListRoute = Ember.Route.extend({
  model: function() {
    var listObjects = [];
    Ember.$.getJSON(config.baseURL + '/list', function(lists) {
      lists.forEach(function(data) {
        listObjects.pushObject(App.Item.createRecord(data));
      });
    });
    return listObjects;
  }
});