App.Setting = DS.Model.extend({
  user:                 DS.attr('number'),
  user_changed:         DS.attr('boolean'),
  stage1:				DS.attr('string'),
  stage2:				DS.attr('string'),
  stage3:				DS.attr('string'),
  stage4:				DS.attr('string'),
  stage5:				DS.attr('string'),
  stage6:				DS.attr('string'),
  stage7:				DS.attr('string'),
  stage0:				DS.attr('string')
});