package dao;

import static dao.DAOUtilitaire.close;
import static dao.DAOUtilitaire.initRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import beans.Cosmetic;
import beans.Maze;

public class MazeDaoImpl implements MazeDao {

	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT = "SELECT * FROM Mazes ORDER BY maze_id";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM Mazes WHERE maze_id = ?";
	private static final String SQL_SELECT_BY_FILENAME = "SELECT * FROM Mazes WHERE filename = ?";
	
	public MazeDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Maze find(int maze_id) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Maze maze = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
			preparedStatement = initRequest(connexion, SQL_SELECT_BY_ID, false, maze_id);				
	        resultSet = preparedStatement.executeQuery();
			connexion.commit();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {maze = map(resultSet);}
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }

	    return maze;
	}

	@Override
	public Maze find(String filename) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Maze maze = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
			preparedStatement = initRequest(connexion, SQL_SELECT_BY_FILENAME, false, filename);				
	        resultSet = preparedStatement.executeQuery();
			connexion.commit();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {maze = map(resultSet);}
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }

	    return maze;
	}

	@Override
	public List<Maze> allMazes() throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Maze> mazes = new ArrayList<Maze>();

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
			connexion.commit();
            while (resultSet.next()) {
            	mazes.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }

        return mazes;
	}

	/* Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table des cosmétiques (un ResultSet) et un bean Cosmetic */
	private static Maze map(ResultSet resultSet) throws SQLException {
	    Maze maze = new Maze();
	    maze.setMaze_id(resultSet.getInt("maze_id"));
	    maze.setCreated(new DateTime(resultSet.getTimestamp("created")));
	    maze.setUser_id(resultSet.getInt("user_id"));
	    maze.setName(resultSet.getString("name"));
	    maze.setFilename(resultSet.getString("filename"));
	    maze.setPrice(resultSet.getInt("price"));
	    return maze;
	}
}
