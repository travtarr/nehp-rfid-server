App.Item = DS.Model.extend({
    rfid: DS.attr('string'),
    itemId: DS.attr('string'),
    description: DS.attr('string'),
    lastStatusChangeDate: DS.attr('string'),
    currentStage: DS.attr('string'),
    currentRevision: DS.attr('string')
});