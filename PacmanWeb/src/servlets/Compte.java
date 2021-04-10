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

import beans.Cosmetic;
import beans.Game;
import beans.Maze;
import beans.Purchase;
import beans.User;
import dao.CosmeticDao;
import dao.DAOFactory;
import dao.GameDao;
import dao.MazeDao;
import dao.PurchaseDao;
import dao.UserDao;

/**
 * Servlet implementation class Compte
 */
@WebServlet("/compte")
public class Compte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_COMPTE = "/WEB-INF/restricted/compte.jsp";
	public static final String ATTR_SKINSPACMANS = "skinsPacmans";
	public static final String ATTR_SKINSGHOSTS = "skinsGhosts";
	public static final String ATTR_USER = "user";
	public static final String ATTR_MAZES = "mazes";
	public static final String ATTR_GAMES = "games";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private PurchaseDao purchaseDao;
	private CosmeticDao cosmeticDao;
	private MazeDao mazeDao;
	private GameDao gameDao;
	private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Compte() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO */
        this.purchaseDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPurchaseDao();
        this.cosmeticDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCosmeticDao();
        this.mazeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMazeDao();
        this.gameDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGameDao();
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			//Mise à jour de l'utilisateur au cas où ses stats (score, parties...) ont été modifiées dans la base de données
			user = userDao.find_by_id(user.getUser_id());
			session.setAttribute(ATTR_USER, user);	
						
			if(user != null) {
				//Récupérer la liste des achats de cosmétiques effectués par l'utilisateur
		        List<Purchase> purchasesCosmetics = purchaseDao.find_by_user(user.getUser_id(), "cosmetic");
		        
				List<Cosmetic> skinsPacmans = new ArrayList<Cosmetic>();
				List<Cosmetic> skinsGhosts = new ArrayList<Cosmetic>();
		        for(Purchase purchase : purchasesCosmetics) {
		        	//Récupérer le cosmétique acheté
		        	Cosmetic cosmetic = cosmeticDao.find(purchase.getItem_id());
		        	//Ajouter le cosmétique dans la liste correspondante
		        	if(cosmetic.getType().equals("pacman")) {
		        		skinsPacmans.add(cosmetic);
		        	}else {
		        		skinsGhosts.add(cosmetic);
		        	}
		        }
		        
				request.setAttribute(ATTR_SKINSPACMANS, skinsPacmans);
				request.setAttribute(ATTR_SKINSGHOSTS, skinsGhosts);
				
				//Récupérer la liste des achats de labyrinthes effectués par l'utilisateur
		        List<Purchase> purchasesMazes = purchaseDao.find_by_user(user.getUser_id(), "maze");
		        
				List<Maze> mazes = new ArrayList<Maze>();
		        for(Purchase purchase : purchasesMazes) {
		        	//Récupérer le cosmétique acheté
		        	Maze maze = mazeDao.find(purchase.getItem_id());
		        	//Ajouter le labyrinthe dans la liste
		        	mazes.add(maze);
		        }
		        
				request.setAttribute(ATTR_MAZES, mazes);
				
				//Récupérer la liste des parties effectuées par l'utilisateur
		        List<Game> games = gameDao.find_by_user(user.getUser_id());
		        
				request.setAttribute(ATTR_GAMES, games);
			}
		}
		
		this.getServletContext().getRequestDispatcher(VUE_COMPTE).forward(request, response);
	}
}
