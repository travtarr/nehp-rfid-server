App.Item = DS.Model.extend({
    rfid: DS.attr('string'),
    item_id: DS.attr('string'),
    comment: DS.attr('string'),
    printed: DS.attr('boolean'),
    reason: DS.attr('string'),
    group: DS.attr('number'),
    created_by: DS.attr('string'),
    created_date: DS.attr('simpledate'),
    revision: DS.attr('string'),
    current_stage: DS.attr('number'),
    current_stage_num: DS.attr('number'),
    current_stage_desc: DS.attr('string'),
    last_status_change_date: DS.attr('simpledate'),
    last_status_change_user: DS.attr('string')
});