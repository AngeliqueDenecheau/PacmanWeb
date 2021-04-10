package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cosmetic;
import beans.Maze;
import beans.Purchase;
import beans.User;
import dao.CosmeticDao;
import dao.DAOFactory;
import dao.MazeDao;
import dao.PurchaseDao;
import dao.UserDao;

/**
 * Servlet implementation class Purchase
 */
@WebServlet("/buy")
public class Buy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_CONNECTION = "/connection";
	public static final String VUE_COSMETICS = "/cosmetics";
	public static final String VUE_MAZES = "/mazes";
	public static final String VUE_HOME = "/home";
	public static final String ATTR_ID_COSMETIC = "id_cosmetic";
	public static final String ATTR_ID_MAZE = "id_maze";
	public static final String ATTR_ERREUR = "erreur";
	public static final String ATTR_SUCCES = "succes";
	public static final String CONF_DAO_FACTORY = "daofactory";
    
    private CosmeticDao cosmeticDao;
    private MazeDao mazeDao;
    private PurchaseDao purchaseDao;
    private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Buy() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de nos DAO */
        this.cosmeticDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCosmeticDao();
        this.mazeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMazeDao();
        this.purchaseDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPurchaseDao();
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String cosmetic_id = request.getParameter(ATTR_ID_COSMETIC);
		String maze_id = request.getParameter(ATTR_ID_MAZE);
		
		if(cosmetic_id != null && !cosmetic_id.trim().isBlank()) {
			Cosmetic cosmetic = cosmeticDao.find(Integer.parseInt(cosmetic_id));
			if(cosmetic == null) {
				session.setAttribute(ATTR_ERREUR, "Erreur : Ce cosmétique n'existe pas.");
			}else {
				if(user == null) {
					response.sendRedirect(request.getContextPath() + VUE_CONNECTION);
					return;
				}
				
				List<Purchase> purchases = purchaseDao.find(user.getUser_id(), cosmetic.getCosmetic_id(), "cosmetic");
				if(!purchases.isEmpty()) {
					session.setAttribute(ATTR_ERREUR, "Erreur : Vous possédez déjà ce cosmétique.");
				}else {
					if(user.getMoney() < cosmetic.getPrice()) {
						session.setAttribute(ATTR_ERREUR, "Erreur : Vous n'avez pas assez d'argent pour acheter ce cosmétique.");	
					}else {
						Purchase purchase = new Purchase();
						purchase.setItem_id(Integer.parseInt(cosmetic_id));
						purchase.setUser_id(user.getUser_id());
						purchase.setType("cosmetic");
						purchaseDao.create(purchase);
						
						user.setMoney(user.getMoney() - cosmetic.getPrice());
						userDao.update(user);

						session.setAttribute(ATTR_SUCCES, "Votre achat a été effectué avec succès !");
					}
				}
			}
			
			response.sendRedirect(request.getContextPath() + VUE_COSMETICS);
		}else if(maze_id != null && !maze_id.trim().isBlank()) {
			Maze maze = mazeDao.find(Integer.parseInt(maze_id));
			if(maze == null) {
				session.setAttribute(ATTR_ERREUR, "Erreur : Ce labyrinthe n'existe pas.");
			}else {
				if(user == null) {
					response.sendRedirect(request.getContextPath() + VUE_CONNECTION);
					return;
				}
				
				List<Purchase> purchases = purchaseDao.find(user.getUser_id(), maze.getMaze_id(), "maze");
				if(!purchases.isEmpty()) {
					session.setAttribute(ATTR_ERREUR, "Erreur : Vous possédez déjà ce labyrinthe.");
				}else {
					if(user.getMoney() < maze.getPrice()) {
						session.setAttribute(ATTR_ERREUR, "Erreur : Vous n'avez pas assez d'argent pour acheter ce labyrinthe.");	
					}else {
						Purchase purchase = new Purchase();
						purchase.setItem_id(Integer.parseInt(maze_id));
						purchase.setUser_id(user.getUser_id());
						purchase.setType("maze");
						purchaseDao.create(purchase);
						
						user.setMoney(user.getMoney() - maze.getPrice());
						userDao.update(user);

						session.setAttribute(ATTR_SUCCES, "Votre achat a été effectué avec succès !");
					}
				}
			}
			
			response.sendRedirect(request.getContextPath() + VUE_MAZES);
		}else {
			response.sendRedirect(request.getContextPath());	
		}
	}
}
