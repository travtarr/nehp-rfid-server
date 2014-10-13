App.User = DS.Model.extend({
  username:              DS.attr('string'),
  password:              DS.attr('string'),
  name:                  DS.attr('string'),
  email:                 DS.attr('string'),
  lastLoginDate:		 DS.attr('string'),
  userCreatedDate:		 DS.attr('string'),
  admin:                 DS.attr('boolean'),
  scanner:				 DS.attr('boolean'),
  apiKeys:               DS.hasMany('apiKey'),
  errors:                {}
});