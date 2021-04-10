package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.DAOFactory;
import dao.UserDao;

/**
 * Servlet implementation class Ranking
 */
@WebServlet("/ranking")
public class Ranking extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_RANKING = "/WEB-INF/ranking.jsp";
	public static final String ATTR_CURRENTPAGE = "currentpage";
	public static final String ATTR_USERS = "users";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ranking() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO */
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users = userDao.allUsersOrderByScore();
		
		request.setAttribute(ATTR_USERS, users);
		request.setAttribute(ATTR_CURRENTPAGE, "ranking");
		this.getServletContext().getRequestDispatcher(VUE_RANKING).forward(request, response);
	}
}
