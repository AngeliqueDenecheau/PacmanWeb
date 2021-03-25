package servlets;

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
import forms.LoginForm;

/**
 * Servlet implementation class Connect
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATTR_USER = "user";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_FORM = "form";
	public static final String VUE_CONNECTION = "/WEB-INF/connection.jsp";
	public static final String VUE_HOME = "/WEB-INF/home.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";
    
    private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
			this.getServletContext().getRequestDispatcher(VUE_HOME).forward(request, response);
		}else {
			request.setAttribute(ATTR_TYPE, "login");
			this.getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginForm loginForm = new LoginForm(userDao);
		
		User user = loginForm.login(request);
		
		if(user != null) {
			/* Création ou récupération de la session */
			HttpSession session = request.getSession();

			/* Stockage du bean dans la session */
			session.setAttribute(ATTR_USER, user);		
			
			/* Transmission de la paire d'objets request/response à notre JSP */
			response.sendRedirect(request.getContextPath());
		}else {
			/* Stockage du formulaire et du type de connection dans l'objet request */
			request.setAttribute(ATTR_FORM, loginForm);
			request.setAttribute(ATTR_TYPE, "login");
			
			this.getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
		}
	}
}
