package forms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.CosmeticDao;
import dao.UserDao;

public class SkinsForm {
	public static final String PARAM_SUBMIT = "submit";

	private String erreur;
	
	public String getErreur() {return erreur;};
	
	private UserDao userDao;
	private CosmeticDao cosmeticDao;
	
	public SkinsForm(UserDao userDao, CosmeticDao cosmeticDao) {
		this.userDao = userDao;
		this.cosmeticDao = cosmeticDao;
	}
	
	public User update(HttpServletRequest request) {
		/* Récupération du contenu des champs text */
		String submitValue = getValeurParam(request, PARAM_SUBMIT);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(submitValue == null || submitValue.isBlank() || (!submitValue.startsWith("pacman") && !submitValue.startsWith("ghost")) || submitValue.split("_").length < 2 || Integer.parseInt(submitValue.split("_")[1]) < 0) {
			erreur = "Skin incorrect !";
			return null;
		}
		
		int id = Integer.parseInt(submitValue.split("_")[1]);
		if(id == 0 || cosmeticDao.find(id) != null) {
			if(submitValue.startsWith("pacman")) user.setPacman_skin(id);
			if(submitValue.startsWith("ghost")) user.setGhost_skin(id);
		}
		
		userDao.update(user);
			
		return userDao.find_by_id(user.getUser_id());
	}
    
    /* Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon */
	private static String getValeurParam(HttpServletRequest request, String param) {
	    String valeur = request.getParameter(param);
	    if (valeur == null || valeur.trim().length() == 0) return null;
	    return valeur.trim();
	}
}
