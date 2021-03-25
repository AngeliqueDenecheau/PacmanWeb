package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.DAOFactory;
import dao.UserDao;
import forms.UpdateForm;

/**
 * Servlet implementation class UpdateProfil
 */
@WebServlet("/update")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, maxRequestSize = 5 * 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_UPDATE = "/WEB-INF/restricted/update.jsp";
	public static final String VUE_COMPTE = "/compte";
	public static final String ATTR_FORM = "form";
	public static final String ATTR_USER = "user";
	public static final String ATTR_SUCCES = "succes";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private UserDao userDao;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO User */
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_UPDATE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chemin = this.getServletContext().getRealPath(this.getServletContext().getContextPath());
	   	chemin = chemin.substring(0, chemin.indexOf("/.") + 1) + "images/users/";
		
		UpdateForm updateForm = new UpdateForm(userDao);
		
		User user = updateForm.update(request, chemin);		
		
		if(user != null) {
			/* Création ou récupération de la session */
			HttpSession session = request.getSession();

			/* Stockage du bean dans la session */
			session.setAttribute(ATTR_USER, user);
			
			/* Stockage du message de succès */	
			request.setAttribute(ATTR_SUCCES, "Mise à jour des informations réussie avec succès !");	
			
			/* Transmission de la paire d'objets request/response à notre JSP */
			this.getServletContext().getRequestDispatcher(VUE_COMPTE).forward(request, response);
		} else {
			/* Stockage du formulaire dans l'objet request */
			request.setAttribute(ATTR_FORM, updateForm);
			
			this.getServletContext().getRequestDispatcher(VUE_UPDATE).forward(request, response);
		}
	}
}
