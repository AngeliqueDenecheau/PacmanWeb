package servlets;

import java.io.IOException;
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
import beans.Purchase;
import beans.User;
import dao.CosmeticDao;
import dao.DAOFactory;
import dao.PurchaseDao;

/**
 * Servlet implementation class Cosmetics
 */
@WebServlet("/cosmetics")
public class Cosmetics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_COMPTE = "/WEB-INF/cosmetics.jsp";
	public static final String ATTR_CURRENTPAGE = "currentpage";
	public static final String ATTR_COSMETICS = "cosmetics";
	public static final String ATTR_PURCHASES = "purchases";
	public static final String CONF_DAO_FACTORY = "daofactory";
    
    private CosmeticDao cosmeticDao;
    private PurchaseDao purchaseDao;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cosmetics() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO User */
        this.cosmeticDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCosmeticDao();
        this.purchaseDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPurchaseDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Cosmetic> cosmetics = cosmeticDao.allCosmetics();
		/*Map<Integer, Cosmetic> mapCosmetics = new HashMap<Integer, Cosmetic>();
        for(Cosmetic cosmetic : cosmetics) {
            mapCosmetics.put(cosmetic.getCosmetic_id(), cosmetic);
        }*/
        
        HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user != null) {		
	        List<Purchase> purchases = purchaseDao.find_by_user(user.getUser_id());
			Map<Integer, Purchase> mapPurchases = new HashMap<Integer, Purchase>();
	        for(Purchase purchase : purchases) {
	        	mapPurchases.put(purchase.getCosmetic_id(), purchase);
	        }
			request.setAttribute(ATTR_PURCHASES, mapPurchases);
		}
        
		request.setAttribute(ATTR_COSMETICS, cosmetics);
		request.setAttribute(ATTR_CURRENTPAGE, "cosmetics");
		this.getServletContext().getRequestDispatcher(VUE_COMPTE).forward(request, response);
	}
}
