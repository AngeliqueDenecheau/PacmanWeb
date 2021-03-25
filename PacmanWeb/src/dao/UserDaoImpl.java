package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import beans.User;
import static dao.DAOUtilitaire.*;

public class UserDaoImpl implements UserDao {
	
	private DAOFactory daoFactory;

	private static final String SQL_SELECT = "SELECT * FROM Users ORDER BY user_id";
	private static final String SQL_SELECT_BY_LOGIN = "SELECT * FROM Users WHERE login = ?";
	private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM Users WHERE email = ?";
	private static final String SQL_SELECT_BY_IDS = "SELECT * FROM Users WHERE login = ? AND password = MD5(?)";
	private static final String SQL_INSERT = "INSERT INTO Users (created, login, email, password) VALUES (NOW(), ? , ?, MD5(?))";
	private static final String SQL_UPDATE = "UPDATE Users SET login = ?, email = ?, password = ?, image = ? WHERE user_id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM Users WHERE user_id = ?";
	
	public UserDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(User user) throws IllegalArgumentException, DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initRequest(connexion, SQL_INSERT, true, user.getLogin(), user.getEmail(), user.getPassword());
	        int statut = preparedStatement.executeUpdate();
			connexion.commit();
	        
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");}
	       
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        resultSet = preparedStatement.getGeneratedKeys();
	        if (resultSet.next()) {
	            /* Puis initialisation de la propriété user_id du bean User avec sa valeur */
	            user.setUser_id(resultSet.getInt("user_id"));
	            user.setPassword(resultSet.getString("password"));
	        } else {
	            throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }
	}

	@Override
	public User find_by_login(String login) throws DAOException {
		return find(SQL_SELECT_BY_LOGIN, login);
	}

	@Override
	public User find_by_email(String email) throws DAOException {
		return find(SQL_SELECT_BY_EMAIL, email);
	}

	@Override
	public User find_by_ids(String login, String password) throws DAOException {
		return find(SQL_SELECT_BY_IDS, login, password);
	}
	
	private User find(String sql, String... values) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        switch (values.length) {
				case 1:
					preparedStatement = initRequest(connexion, sql, false, values[0]);				
					break;
				case 2:
					preparedStatement = initRequest(connexion, sql, false, values[0], values[1]);
					break;
			}
	        resultSet = preparedStatement.executeQuery();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {user = map(resultSet);}
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }

	    return user;
	}

	@Override
	public List<User> allUsers() throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connection);
        }

        return users;
	}

	@Override
	public void update(User user) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
			preparedStatement = initRequest(connexion, SQL_UPDATE, false, user.getLogin(), user.getEmail(), user.getPassword(), user.getImage(), user.getUser_id());
			int statut = preparedStatement.executeUpdate();
			connexion.commit();
			
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {throw new DAOException("Échec de la modification de l'utilisateur, aucune ligne modifiée dans la table.");}
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }
	}

	@Override
	public void delete(User user) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initRequest(connexion, SQL_DELETE_BY_ID, true, user.getUser_id());
	        int statut = preparedStatement.executeUpdate();
			connexion.commit();
	        
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	        	throw new DAOException("Échec de la suppression de l'utilisateur, aucune ligne supprimée dans la table.");
	        } else {
	        	user.setUser_id(null);
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(preparedStatement, connexion);
	    }
	}
	
	/* Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table des utilisateurs (un ResultSet) et un bean Utilisateur */
	private static User map(ResultSet resultSet) throws SQLException {
	    User user = new User();
	    user.setUser_id(resultSet.getInt("user_id"));
        user.setToken(resultSet.getString("token"));
        user.setCreated(new DateTime(resultSet.getTimestamp("created")));
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setImage(resultSet.getString("image"));
        user.setMoney(resultSet.getInt("money"));
        user.setScore(resultSet.getInt("score"));
        user.setParties_jouees(resultSet.getInt("parties_jouees"));
        user.setParties_gagnees(resultSet.getInt("parties_gagnees"));
	    return user;
	}
}
