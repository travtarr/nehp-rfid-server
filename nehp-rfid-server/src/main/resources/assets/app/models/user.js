App.User = DS.Model.extend({
  username:              DS.attr('string'),
  password:              DS.attr('string'),
  password_reset:        DS.attr('boolean'),
  name:                  DS.attr('string'),
  email:                 DS.attr('string'),
  last_login_date:		 DS.attr('simpledate'),
  user_created_date:	 DS.attr('simpledate'),
  admin:                 DS.attr('boolean'),
  scanner:				 DS.attr('boolean'),
  apiKeys:               DS.hasMany('apiKey')
});