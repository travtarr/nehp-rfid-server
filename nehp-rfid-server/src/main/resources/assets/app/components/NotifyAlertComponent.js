App.NotifyAlertComponent = Ember.Component.extend({
	
	classNameBindings: [':notify',
	                    'isOpaque'],
	
	/*
	 * @property {boolean} Should the view be opaque now?
	 */
	isOpaque: false,
	/*
	 * @property {number} Will be set by 'didInsertElement'.
	 * Used for clearing the auto-hide timeout
	 */
	timeoutId: null,
	
	/*
	 * @property {number} Will be hidden after this many ms.
	 */
	hideAfterMs: 5000,
	
	/*
	 * Lifecycle hook - called when the view enters the DOM
	 */
	didInsertElement: function(){
		// Set-up the auto-hide
		this.set('closed', false);
		this.set('timeoutId', setTimeout(function(){
			this.send('close');
		}.bind(this), this.get('hideAfterMs')));
		
		// Fade in the view.
		Ember.run.later(this, function() {
			this.set('isOpaque', true);
		}, 1);
	},
	
	actions: {
		close: function() {
			this.set('isOpaque, false');
			setTimeout(function() {
				this.set('notification.closed', true);
				clearTimeout(this.get('timeoutId'));
			}.bind(this), 300);
		}
	}
});