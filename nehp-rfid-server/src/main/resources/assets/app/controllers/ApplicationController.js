App.ApplicationController = Ember.Controller.extend({
	// requires the sessions controller
	needs : [ 'sessions' ],

	notification: {
		type: null,
		title: null,
		message: null,
		closed: true
	},		
		
	// creates a computed property called currentUser that will be
	// binded on the curretUser of the sessions controller and will return its
	// value
	currentUser : (function() {
		return this.get('controllers.sessions.currentUser');
	}).property('controllers.sessions.currentUser'),

	// creates a computed property called admin that will be binded on
	// the admin of the sessions controller to verify if the user is
	// an administrator
	isAdmin : (function() {
		return this.get('controllers.sessions.admin');
	}).property('controllers.sessions.admin'),

	// creates a computed property called isAuthenticated that will be
	// binded on the curretUser of the sessions controller and will verify if
	// the object is empty
	isAuthenticated : (function() {
		return !Ember.isEmpty(this.get('controllers.sessions.currentUser.username'));
	}).property('controllers.sessions.currentUser'),

	actions : {
		/**
		 * Action handler for creating a new notification. Could be called from
		 * elsewhere throughout the application.
		 * 
		 * @param type
		 *            {String} classification; used for which icon to show
		 * @param title
		 *            {String} leading text
		 * @param message
		 *            {String} supporting text
		 */
		setNotification : function(type, title, message) {
			var notification = {
				type : type,
				title : title,
				message : message,
				closed : false
			};
			this.set('notification', notification);
		},
		testNotification : function() {
			this.send('setNotification', 'success', 'Success', 'Yay you did it.');
		}
	}
});