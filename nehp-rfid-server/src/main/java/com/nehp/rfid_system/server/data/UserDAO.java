package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;
import java.util.Random;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.EmailCredentials;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.helpers.PasswordHelper;

public class UserDAO extends AbstractDAO<User> {
	
	private static final int PASSWORD_LENGTH = 20;
	private static final String AVAILABLE_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890";
	
	private final EmailCredentials emailCreds;
	SessionFactory factory;

	public UserDAO(SessionFactory sessionFactory, EmailCredentials emailCreds) {
		super(sessionFactory);
		this.factory = sessionFactory;
		this.emailCreds = emailCreds;
	}

	public Optional<User> getUserByUsernameAndPassword(String username,
			String password) {
		Optional<User> user = null;
		
		user = Optional.of(list(namedQuery("users.getByUsername")
						.setParameter("username", username, StringType.INSTANCE)).get(0));
		System.out.println(user.get().getPassword());
		try {
			if(PasswordHelper.check(password, user.get().getPassword()))
				return user;
			else
				return Optional.absent();
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.absent();
		}
	}

	public Optional<User> getUserById(Long id) {
		User user = null;
		user = get(id);
		
		if(user == null)
			return Optional.absent();
				
		return Optional.of(user);
	}

	public Optional<Long> create(User user) {
		User newUser = user;
		String newPassword = randomizedPassword();
		
		try {
			newUser.setPassword( PasswordHelper.getSaltedHash(newPassword) );
		} catch (Exception e) {
			System.out.println("Unable to set hashed password");
			return Optional.absent();
		}

		user.setPasswordReset(true);
		
		// set-up email 
		String txtMsg = String.format("Your account has been created.  Username: %s, Password: %s", newUser.getUsername(), newPassword);
		String htmlMsg = "Hello " + user.getName() + ",<br>"
						+ "Your account has been created. <br>"
						+ "Username: " + user.getUsername() + "<br>"
						+ "Password: " + newPassword + "<br>"
						+ "Please login <a href=\"http://www.nehp-tracker.com\">here</a> using the above username and password.<br>"
						+ "<p>Upon first-time logging in, you will be asked to change your password.  No access will be given until "
						+ "this has been completed. </p>"
						+ "Thank you, <br>"
						+ "NEHP TRACKER Administrator";
		String subject = "NEHP TRACKER - Account Creation";
		
		// send email
		if(!sendMail(user.getName(), user.getEmail(), txtMsg, htmlMsg, subject)){
			System.out.println("Sending email failed");
			return Optional.absent();
		}
		
		return Optional.of(persist(newUser).getId());
	}
	
	
	/**
	 * Resets the user's password and sets the password reset flag
	 * to force user to set their own new password next time they log in.
	 * 
	 * @param userId - user that needs a new password
	 * @return String - password that was reset
	 */
	public boolean resetPassword (Long userId){
		User user = get(userId);
		String newPassword = randomizedPassword();
				
		// update user
		try {
			user.setPassword(PasswordHelper.getSaltedHash(newPassword));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		user.setPasswordReset(true);
		
		// set-up email 
		String txtMsg = String.format("Your password has been reset: %s", newPassword);
		String htmlMsg = "Hello " + user.getName() + ",<br>"
						+ "Your password has been reset: <br>"
						+ newPassword + "<br>"
						+ "Please login <a href=\"http://www.nehp-tracker.com\">here</a> using the above password.<br>"
						+ "<br> Thank you, <br>"
						+ "NEHP TRACKER Administrator";
		String subject = "NEHP TRACKER - Password Reset";
		
		// send email
		if(!sendMail(user.getName(), user.getEmail(), txtMsg, htmlMsg, subject)){
			return false;
		}
		
		return true;
	}
	

	public boolean update(User user) {

		// Make sure we update the correct user
		User updateUser = get(user.getId());

		if (updateUser == null)
			return false;

		updateUser = user;
		persist(updateUser);

		return true;
	}

	public List<User> getUsersAll() {
		return list(namedQuery("users.getAll"));
	}
	
	public boolean deleteById(Long id){
		return delete(get(id));
	}

	public boolean delete(User user) {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}
	
	
	/**
	 * Check if user is an admin based.
	 * @param id - user's id to check if admin
	 * @return true if user is admin
	 */
	public boolean isAdmin(Long id){
		return get(id).getAdmin();
	}
	
	
	/**
	 * Create a randomized password of length determined by class constant
	 * @return String - randomized password
	 */
	private String randomizedPassword (){
		Random rand = new Random();
		String password = "";
		
		for ( int i = 0; i < PASSWORD_LENGTH; i++ ){
			int randomInt = rand.nextInt( AVAILABLE_CHARS.length() );
			password = password + AVAILABLE_CHARS.charAt(randomInt);
		}
		
		return password;
	}
	

	/**
	 * <h2>Email function for user services such as:</h2>
	 * <ul>
	 * 	<li>Password sent for user creation</li>
	 * 	<li>Password reset per admin or user</li>
	 * </ul>
	 * @param receiverName - name of the recipient
	 * @param receiverEmail - email address of recipient
	 * @param txtMessage - body of the email if html not supported
	 * @param htmlMessage - html body of the email
	 * @param subject - subject header of the email
	 */
	private boolean sendMail(String receiverName, String receiverEmail, 
			String txtMessage, String htmlMessage, String subject){
		
		// set as html email
		HtmlEmail email = new HtmlEmail();
		
		// set-up connection details
		email.setHostName( emailCreds.getHostName() );
		email.setSmtpPort( emailCreds.getHostPort() );
		if(emailCreds.getEmailUser() != null && emailCreds.getEmailPassword() != null){
			email.setAuthenticator(
				new DefaultAuthenticator(
					emailCreds.getEmailUser(), 
					emailCreds.getEmailPassword())
			);
		}
		email.setSSL( emailCreds.getSSL() );
		
		// set email details and try sending
		email.setSubject( subject );
		try {
			email.setFrom( emailCreds.getEmailAddress() );
			email.setHtmlMsg( htmlMessage );
			email.setTextMsg( txtMessage );
			email.addTo( receiverEmail, receiverName );
			email.send();
		} catch (EmailException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
