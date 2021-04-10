package forms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import beans.User;
import dao.DAOException;
import dao.UserDao;

public class SubscriptionForm {
	public static final String PARAM_LOGIN = "login";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_CONFIRM_PASSWORD = "confirm_password";
	
	private String erreurMsg;
	private List<String> erreurs = new ArrayList<String>();
	
	public String getErreurMsg() {return erreurMsg;}
	public List<String> getErreurs() {return erreurs;}
	
	private UserDao userDao;
	
	public SubscriptionForm(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User subscription(HttpServletRequest request) {
		String login = getValeurParam(request, PARAM_LOGIN);
		String email = getValeurParam(request, PARAM_EMAIL);
		String password = getValeurParam(request, PARAM_PASSWORD);
		String confirm_password = getValeurParam(request, PARAM_CONFIRM_PASSWORD);
		
		User user = new User();
		try {
			traiterLogin(login, user);
			traiterEmail(email, user);
			traiterPassword(password, confirm_password, user);
	
			if(erreurs.size() > 0) {
				erreurMsg = ((erreurs.size() == 1) ? "Erreur : " : "Erreurs : ") + String.join("</br>", erreurs);
				return null;
			}
			
			userDao.create(user);
		} catch (DAOException e) {
			erreurMsg = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	        System.out.println(e.getMessage());
			return null;
		}
		
	    return userDao.find_by_id(user.getUser_id());
	}
	
	private static String getValeurParam(HttpServletRequest request, String param) {
	    String valeur = request.getParameter(param);
	    if (valeur == null || valeur.trim().length() == 0) return null;
	    return valeur.trim();
	}
	
	public void traiterLogin(String login, User user) {
		try {
			validationLogin(login);
		} catch (FormValidationException e) {
			erreurs.add(e.getMessage());
		}
		user.setLogin(login);
	}
	
	public void validationLogin(String login) throws FormValidationException {
    	if(login == null || login.isBlank() || login.length() < 3 || login.length() > 20) throw new FormValidationException("Votre login doit être compris entre 3 et 20 caractères !");
    	if(userDao.find_by_login(login) != null) throw new FormValidationException("Ce login est déjà pris !");
    }
	
	public void traiterEmail(String email, User user) {
		try {
			validationEmail(email);
		} catch (FormValidationException e) {
			erreurs.add(e.getMessage());
		}
		user.setEmail(email);
	}
    
    public void validationEmail(String email) throws FormValidationException {
    	if(email == null || email.isBlank() || email.length() < 1) throw new FormValidationException("Veuillez renseigner votre adresse email !");
    	if(email.length() > 60) throw new FormValidationException("Votre adresse email est trop longue !");
    	if(!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) throw new FormValidationException("Veuillez renseigner une adresse email valide !");
    	if(userDao.find_by_email(email) != null) throw new FormValidationException("Un compte existe déjà avec cette adresse email !");
    }
	
	public void traiterPassword(String password, String confirm_password, User user) {
		try {
			validationPassword(password, confirm_password);
		} catch (FormValidationException e) {
			erreurs.add(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			erreurs.add(e.getMessage());
		}
		user.setPassword(password);
	}
    
    public void validationPassword(String password, String confirm_password) throws FormValidationException, NoSuchAlgorithmException {
    	if(password == null || password.isBlank() || password.length() < 8 || password.length() > 20) throw new FormValidationException("Votre mot de passe doit être compris entre 8 et 20 caractères !");
    	Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.find()) throw new FormValidationException("Votre mot de passe doit contenir au minimum un chiffre, une lettre et un caractère spécial !");
    	if(!password.equals(confirm_password)) throw new FormValidationException("Vos mots de passe ne correspondent pas !");
    }
}
