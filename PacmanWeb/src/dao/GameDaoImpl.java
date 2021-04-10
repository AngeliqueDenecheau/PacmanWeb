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

import beans.Game;

public class GameDaoImpl implements GameDao {

	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT_BY_USER = "SELECT * FROM Games WHERE player_one = ? OR player_two = ?";
	private static final String SQL_INSERT = "INSERT INTO Games (created, gamemode, player_one, player_two, winner, maze_id) VALUES (NOW(), ?, ?, ?, ?, ?)";
	
	public GameDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Game game) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initRequest(connexion, SQL_INSERT, true, game.getGamemode(), game.getPlayer_one(), game.getPlayer_two(), game.getWinner(), game.getMaze_id());
	        int statut = preparedStatement.executeUpdate();
			connexion.commit();
	        
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {throw new DAOException("Échec de la création de la partie, aucune ligne ajoutée dans la table.");}
	       
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        resultSet = preparedStatement.getGeneratedKeys();
	        if (resultSet.next()) {
	            /* Puis initialisation de la propriété game_id du bean Game avec sa valeur */
	        	game.setGame_id(resultSet.getInt(1));
	        } else {
	            throw new DAOException("Échec de la création de la partie en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }
	}

	@Override
	public List<Game> find_by_user(int user_id) throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Game> games = new ArrayList<Game>();

        try {
            connexion = daoFactory.getConnection();
			preparedStatement = initRequest(connexion, SQL_SELECT_BY_USER, false, user_id, user_id);
            resultSet = preparedStatement.executeQuery();
			connexion.commit();
            while (resultSet.next()) {
            	games.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }

        return games;
	}

	/* Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table des cosmétiques (un ResultSet) et un bean Cosmetic */
	private static Game map(ResultSet resultSet) throws SQLException {
	    Game game = new Game();
	    game.setGame_id(resultSet.getInt("game_id"));
	    game.setCreated(new DateTime(resultSet.getTimestamp("created")));
	    game.setGamemode(resultSet.getString("gamemode"));
	    game.setPlayer_one(resultSet.getInt("player_one"));
	    game.setPlayer_one(resultSet.getInt("player_two"));
	    game.setWinner(resultSet.getInt("winner"));
	    game.setMaze_id(resultSet.getInt("maze_id"));
	    return game;
	}
}
