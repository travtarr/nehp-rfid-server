App.Setting = DS.Model.extend({
  user:                 DS.attr('number'),
  user_changed:         DS.attr('boolean'),
  stage:				DS.attr('number'),
  duration:				DS.attr('number')
});