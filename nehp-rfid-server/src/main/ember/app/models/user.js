App.User = DS.Model.extend({
  username: 			 DS.attr('string'),
  password: 			 DS.attr('string'),
  password_confirmation: DS.attr('string'),
  name: 				 DS.attr('string'),
  email: 				 DS.attr('string'),
  admin: 				 DS.attr('boolean'),
  apiKeys:				 DS.hasMany('apiKey'),
  errors:				 {}
});