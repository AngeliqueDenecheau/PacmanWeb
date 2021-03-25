package forms;

import javax.servlet.http.HttpServletRequest;

import beans.User;
import dao.UserDao;

public class LoginForm {
	public static final String PARAM_LOGIN = "login";
	public static final String PARAM_PASSWORD = "password";

	private String erreurMsg;
	
	public String getErreurMsg() {return erreurMsg;}
	
	private UserDao userDao;
	
	public LoginForm(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User login(HttpServletRequest request) {
		String login = getValeurParam(request, PARAM_LOGIN);
		String password = getValeurParam(request, PARAM_PASSWORD);

		User user = null;
		try {
			user = validation(login, password, user);
		} catch (Exception e) {
			erreurMsg = e.getMessage();
		}

	    return user;
	}
	
	private static String getValeurParam(HttpServletRequest request, String param) {
	    String valeur = request.getParameter(param);
	    if (valeur == null || valeur.trim().length() == 0) return null;
	    return valeur.trim();
	}
	
	public User validation(String login, String password, User user) throws Exception {
    	if(login == null || login.isBlank() || password == null || password.isBlank()) throw new Exception("Veuillez saisir vos identifiants de connection !");
    	user = userDao.find_by_ids(login, password);
    	if(user == null) throw new FormValidationException("L'identifiant et le mot de passe ne correspondent pas !");
    	return user;
	}
}
