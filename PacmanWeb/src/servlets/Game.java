package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Maze;
import beans.User;
import dao.DAOFactory;
import dao.GameDao;
import dao.MazeDao;
import dao.UserDao;

/**
 * Servlet implementation class Game
 */
@WebServlet("/game")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATTR_USER = "user";
    public static final String CONF_DAO_FACTORY = "daofactory";
    
    private UserDao userDao;
    private GameDao gameDao;
    private MazeDao mazeDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Game() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO User */
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
        this.mazeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMazeDao();
        this.gameDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGameDao();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String score = request.getParameter("score");
		String login = request.getParameter("login");
		String maze_filename = request.getParameter("maze");
		String victory = request.getParameter("victory");
		
		User user = userDao.find_by_login(login);
		if(user != null && Integer.parseInt(score) > 0) {
			user.setScore(user.getScore() + Integer.parseInt(score));
			user.setParties_jouees(user.getParties_jouees() + 1);
			if(victory != null) {
				user.setMoney(user.getMoney() + 200);
				user.setParties_gagnees(user.getParties_gagnees() + 1);
			}else {
				user.setMoney(user.getMoney() + 30);				
			}
			userDao.update(user);
			
			beans.Game game = new beans.Game();
			game.setGamemode("singleplayer");
			game.setPlayer_one(user.getUser_id());
			game.setPlayer_two(null);
			game.setWinner((victory != null) ? user.getUser_id() : null);
			
			Maze maze = mazeDao.find(maze_filename);
			if(maze != null) {
				game.setMaze_id(maze.getMaze_id());
			}else {
				game.setMaze_id(null);
			}
			
			gameDao.create(game);
		}
	}
}
