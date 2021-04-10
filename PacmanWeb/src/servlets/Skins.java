package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cosmetic;
import beans.User;
import dao.CosmeticDao;
import dao.DAOFactory;
import dao.UserDao;
import forms.SkinsForm;

/**
 * Servlet implementation class Skins
 */
@WebServlet("/skins")
public class Skins extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_COMPTE = "/compte";
	public static final String VUE_COSMETICS = "/WEB-INF/cosmetics.jsp";
	public static final String PARAM_LOGIN_CLIENT = "loginClient";
	public static final String ATTR_USER = "user";
	public static final String ATTR_SUCCES = "succes";
	public static final String ATTR_ERREUR = "erreur";
	public static final String ATTR_PACMAN_SKIN = "pacman_skin";
	public static final String ATTR_GHOST_SKIN = "ghost_skin";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private UserDao userDao;
    private CosmeticDao cosmeticDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Skins() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO User */
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
        this.cosmeticDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCosmeticDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginClient = request.getParameter(PARAM_LOGIN_CLIENT);
		
		User user = userDao.find_by_login(loginClient);
		if(user != null) {
			if(user.getPacman_skin() != null && user.getPacman_skin() > 0) {
				Cosmetic pacmanSkin = cosmeticDao.find(user.getPacman_skin());
				response.addHeader(ATTR_PACMAN_SKIN, pacmanSkin.getImage_filename());
			}
			if(user.getGhost_skin() != null && user.getGhost_skin() > 0) {
				Cosmetic ghostSkin = cosmeticDao.find(user.getGhost_skin());
				response.addHeader(ATTR_GHOST_SKIN, ghostSkin.getImage_filename());
			}
		}

		this.getServletContext().getRequestDispatcher(VUE_COSMETICS).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginClient = request.getParameter(PARAM_LOGIN_CLIENT);
		
		if(loginClient != null) {
			System.out.println("post");
			doGet(request, response);
		}else {
			SkinsForm skinsForm = new SkinsForm(userDao, cosmeticDao);
			
			User user = skinsForm.update(request);
			
			/* Création ou récupération de la session */
			HttpSession session = request.getSession();
	
			if(user != null) {
				/* Stockage du bean dans la session */
				session.setAttribute(ATTR_USER, user);
				
				/* Stockage du message de succès */	
				session.setAttribute(ATTR_SUCCES, "Choix du cosmétique réussi avec succès !");	
				
				/* Transmission de la paire d'objets request/response à notre JSP */
				response.sendRedirect(request.getContextPath() + VUE_COMPTE);
				//this.getServletContext().getRequestDispatcher(VUE_COMPTE).forward(request, response);
			} else {
				/* Stockage du message d'erreur dans l'objet request */
				session.setAttribute(ATTR_ERREUR, skinsForm.getErreur());
				
				response.sendRedirect(request.getContextPath() + VUE_COMPTE);
			}
		}
	}
}
