App.User = DS.Model.extend({
  username:              DS.attr('string'),
  password:              DS.attr('string'),
  name:                  DS.attr('string'),
  email:                 DS.attr('string'),
  last_login_date:		 DS.attr('string'),
  user_created_date:	 DS.attr('string'),
  admin:                 DS.attr('boolean'),
  scanner:				 DS.attr('boolean'),
  apiKeys:               DS.hasMany('apiKey')
});