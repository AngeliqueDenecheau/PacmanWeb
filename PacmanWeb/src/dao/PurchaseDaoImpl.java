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

import beans.Purchase;

public class PurchaseDaoImpl implements PurchaseDao {

	private DAOFactory daoFactory;

	private static final String SQL_SELECT = "SELECT * FROM Purchases WHERE user_id = ? AND item_id = ? AND type = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Purchases ORDER BY purchase_id";
	private static final String SQL_SELECT_BY_ITEM_ID = "SELECT * FROM Purchases WHERE item_id = ? AND type = ?";
	private static final String SQL_SELECT_BY_USER_ID = "SELECT * FROM Purchases WHERE user_id = ? AND type = ?";
	private static final String SQL_INSERT = "INSERT INTO Purchases (created, user_id, type, item_id) VALUES (NOW(), ? , ? , ?)";
	
	public PurchaseDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Purchase purchase) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initRequest(connexion, SQL_INSERT, true, purchase.getUser_id(), purchase.getType(), purchase.getItem_id());
	        int statut = preparedStatement.executeUpdate();
			connexion.commit();
	        
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {throw new DAOException("Échec de la création de l'achat, aucune ligne ajoutée dans la table.");}
	       
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        resultSet = preparedStatement.getGeneratedKeys();
	        if (resultSet.next()) {
	            /* Puis initialisation de la propriété purchase_id du bean Purchase avec sa valeur */
	            purchase.setPurchase_id(resultSet.getInt(1));
	        } else {
	            throw new DAOException("Échec de la création de l'achat en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }
	}

	@Override
	public List<Purchase> find(int user_id, int item_id, String type) throws DAOException {
		return find(SQL_SELECT, user_id, item_id, type);
	}

	@Override
	public List<Purchase> find_by_item(int item_id, String type) throws DAOException {
		return find(SQL_SELECT_BY_ITEM_ID, item_id, type);
	}

	@Override
	public List<Purchase> find_by_user(int user_id, String type) throws DAOException {
		return find(SQL_SELECT_BY_USER_ID, user_id, type);
	}
	
	private List<Purchase> find(String sql, Object... values) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Purchase> purchases = new ArrayList<Purchase>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initRequest(connexion, sql, false, values);
	        /*switch (values.length) {
				case 1:
					preparedStatement = initRequest(connexion, sql, false, values[0]);	
					break;
				case 2:
					preparedStatement = initRequest(connexion, sql, false, values[0], values[1]);	
					break;
				case 3:
					preparedStatement = initRequest(connexion, sql, false, values);
					break;
			}*/			
	        resultSet = preparedStatement.executeQuery();
			connexion.commit();
	        
	        while (resultSet.next()) {
            	purchases.add(map(resultSet));
            }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }

	    return purchases;
	}

	@Override
	public List<Purchase> allPurchases() throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Purchase> purchases = new ArrayList<Purchase>();

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement(SQL_SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
			connexion.commit();
            while (resultSet.next()) {
            	purchases.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }

        return purchases;
	}

	/* Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table des cosmétiques (un ResultSet) et un bean Cosmetic */
	private static Purchase map(ResultSet resultSet) throws SQLException {
	    Purchase purchase = new Purchase();
	    purchase.setPurchase_id(resultSet.getInt("purchase_id"));
	    purchase.setCreated(new DateTime(resultSet.getTimestamp("created")));
	    purchase.setUser_id(resultSet.getInt("user_id"));
	    purchase.setType(resultSet.getString("type"));
	    purchase.setItem_id(resultSet.getInt("item_id"));
	    return purchase;
	}
}
