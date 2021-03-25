package servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.DAOFactory;
import dao.UserDao;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_HOME = "/WEB-INF/home.jsp";
	public static final String VUE_CONNECTION = "/WEB-INF/connection.jsp";
	public static final String VUE_COMPTE = "/WEB-INF/restricted/compte.jsp";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_SUCCES = "succes";
	public static final String ATTR_ERREUR = "erreur";
	public static final String CONF_DAO_FACTORY = "daofactory";

    private UserDao userDao;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			userDao.delete(user);
			if(user.getUser_id() == null) {
				session.setAttribute("user", null);
				String chemin = this.getServletContext().getRealPath(this.getServletContext().getContextPath());
			   	chemin = chemin.substring(0, chemin.indexOf("/.") + 1) + "images/";
				File file = new File(chemin + user.getImage());
				file.delete();
				
				request.setAttribute(ATTR_SUCCES, "Compte supprimé avec succès !");
				this.getServletContext().getRequestDispatcher(VUE_HOME).forward(request, response);
			}else {
				request.setAttribute(ATTR_ERREUR, "Un problème inattendu est survenu lors de la suppression du compte, veuillez réessayer ultérieurement.");
				this.getServletContext().getRequestDispatcher(VUE_COMPTE).forward(request, response);
			}
		}else {
			request.setAttribute(ATTR_TYPE, "login");
			this.getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
		}
	}
}
