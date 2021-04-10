package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Maze;
import beans.Purchase;
import beans.User;
import dao.DAOFactory;
import dao.MazeDao;
import dao.PurchaseDao;
import dao.UserDao;

/**
 * Servlet implementation class Mazes
 */
@WebServlet("/mazes")
public class Mazes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_MAZES = "/WEB-INF/mazes.jsp";
	public static final String PARAM_LOGIN_CLIENT = "loginClient";
	public static final String ATTR_CURRENTPAGE = "currentpage";
	public static final String ATTR_MAZES = "mazes";
	public static final String ATTR_PURCHASES = "purchases";
	public static final String CONF_DAO_FACTORY = "daofactory";
    
    private MazeDao mazeDao;
    private PurchaseDao purchaseDao;
    private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mazes() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de nos DAO */
        this.mazeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMazeDao();
        this.purchaseDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPurchaseDao();
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginClient = request.getParameter(PARAM_LOGIN_CLIENT);
		if(loginClient != null) { //Requête du serveur pour récupérer les labyrinthes achetés par le joueur
			User user = userDao.find_by_login(loginClient);
			if(user != null) {
				List<Purchase> purchases = purchaseDao.find_by_user(user.getUser_id(), "maze");
				List<String> mazes = new ArrayList<String>();
				for(int i = 0; i < purchases.size(); i++) {
					Maze maze = mazeDao.find(purchases.get(i).getItem_id());
					if(maze != null) {
						mazes.add(maze.getFilename());
					}
				}
				String mazesString = String.join("/", mazes);
				response.addHeader(ATTR_MAZES, mazesString);
			}
			
			this.getServletContext().getRequestDispatcher(VUE_MAZES).forward(request, response);
		}else { //Site web
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			List<Maze> mazes = mazeDao.allMazes();
	        
			if(user != null) {
		        List<Purchase> purchases = purchaseDao.find_by_user(user.getUser_id(), "maze");
				Map<Integer, Purchase> mapPurchases = new HashMap<Integer, Purchase>();
		        for(Purchase purchase : purchases) {
		        	mapPurchases.put(purchase.getItem_id(), purchase);
		        }
				request.setAttribute(ATTR_PURCHASES, mapPurchases);
			}
	        
			request.setAttribute(ATTR_MAZES, mazes);
			request.setAttribute(ATTR_CURRENTPAGE, "mazes");
			this.getServletContext().getRequestDispatcher(VUE_MAZES).forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginClient = request.getParameter(PARAM_LOGIN_CLIENT);
		
		User user = userDao.find_by_login(loginClient);
		if(user != null) {
			List<Purchase> purchases = purchaseDao.find_by_user(user.getUser_id(), "maze");
			List<String> mazes = new ArrayList<String>();
			for(int i = 0; i < purchases.size(); i++) {
				Maze maze = mazeDao.find(purchases.get(i).getItem_id());
				if(maze != null) {
					String filename = maze.getFilename().substring(0, maze.getFilename().length() - 4);
					System.out.println(filename);
					mazes.add(filename);
				}
			}
			String mazesString = String.join("/", mazes);
			response.addHeader(ATTR_MAZES, mazesString);
		}
		
		this.getServletContext().getRequestDispatcher(VUE_MAZES).forward(request, response);
	}
}
