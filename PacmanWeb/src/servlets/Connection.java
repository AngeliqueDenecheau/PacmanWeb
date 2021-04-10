package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.DAOFactory;
import dao.UserDao;
import forms.LoginForm;
import forms.SubscriptionForm;

/**
 * Servlet implementation class Connection
 */
@WebServlet("/connection")
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PARAM_REMEMBERME = "rememberme";
	public static final String ATTR_USER = "user";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_FORM = "form";
	public static final String COOKIE_USER_ID = "user_id";
	public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365;  // 1 an
	public static final String VUE_CONNECTION = "/WEB-INF/connection.jsp";
	public static final String VUE_HOME = "/home";
    public static final String CONF_DAO_FACTORY = "daofactory";
    
    private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connection() {
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
			//this.getServletContext().getRequestDispatcher(VUE_HOME).forward(request, response);
			response.sendRedirect(request.getContextPath() + VUE_HOME);
		}else {
			System.out.println("user null");
			String value = getCookieValue(request, COOKIE_USER_ID);
			System.out.println(value);
			if(value != null) {
				user = userDao.find_by_id(Integer.parseInt(value));
				System.out.println(user);
				if(user != null) {
					session.setAttribute(ATTR_USER, user);
					response.sendRedirect(request.getContextPath());
					return;
				}
			}		
			//request.setAttribute(ATTR_TYPE, "subscribe");
			this.getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter(ATTR_TYPE);
		if(type == null || type.isBlank() || (!type.equals("login") && !type.equals("subscribe"))) {
			this.getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
			return;
		}
		
		User user = null;
		if(type.equals("login")) {
			LoginForm loginForm = new LoginForm(userDao);
			user = loginForm.login(request);
			request.setAttribute(ATTR_FORM, loginForm);
			request.setAttribute(ATTR_TYPE, "login");
		}else {
			SubscriptionForm subscriptionForm = new SubscriptionForm(userDao);
			user = subscriptionForm.subscription(request);
			request.setAttribute(ATTR_FORM, subscriptionForm);
			request.setAttribute(ATTR_TYPE, "subscribe");
		}
		
		if(user != null) {
			/* Création ou récupération de la session */
			HttpSession session = request.getSession();
			/* Stockage du bean dans la session */
			session.setAttribute(ATTR_USER, user);	
			
			/* Si et seulement si la case du formulaire est cochée */
			System.out.println("param : " + request.getParameter(PARAM_REMEMBERME));
		    if (request.getParameter(PARAM_REMEMBERME) != null) {
		        /* Création du cookie, et ajout à la réponse HTTP */
		        setCookie(response, COOKIE_USER_ID, Integer.toString(user.getUser_id()), COOKIE_MAX_AGE);
		    } else {
		        /* Demande de suppression du cookie du navigateur */
		        setCookie(response, COOKIE_USER_ID, "", 0);
		    }
			
			/* Transmission de la paire d'objets request/response à notre JSP */
			response.sendRedirect(request.getContextPath());
		}else {			
			this.getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
		}
	}

    /*
     * Méthode utilitaire gérant la récupération de la valeur d'un cookie donné depuis la requête HTTP.
     */
    private static String getCookieValue(HttpServletRequest request, String nom) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie != null && nom.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

	/*
	 * Méthode utilitaire gérant la création d'un cookie et son ajout à la réponse HTTP.
	 */
	private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) {
	    Cookie cookie = new Cookie(nom, valeur);
	    cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
}
