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

	private static final String SQL_SELECT = "SELECT * FROM Purchases ORDER BY purchase_id";
	private static final String SQL_SELECT_BY_COSMETIC_ID = "SELECT * FROM Purchases WHERE cosmetic_id = ?";
	private static final String SQL_SELECT_BY_USER_ID = "SELECT * FROM Purchases WHERE user_id = ?";
	
	public PurchaseDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<Purchase> find_by_cosmetic(int cosmetic_id) throws DAOException {
		return find(SQL_SELECT_BY_COSMETIC_ID, cosmetic_id);
	}

	@Override
	public List<Purchase> find_by_user(int user_id) throws DAOException {
		return find(SQL_SELECT_BY_USER_ID, user_id);
	}
	
	private List<Purchase> find(String sql, int id) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Purchase> purchases = new ArrayList<Purchase>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
			preparedStatement = initRequest(connexion, sql, false, id);				
	        resultSet = preparedStatement.executeQuery();
	        
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
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Purchase> purchases = new ArrayList<Purchase>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	purchases.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connection);
        }

        return purchases;
	}

	/* Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table des cosmétiques (un ResultSet) et un bean Cosmetic */
	private static Purchase map(ResultSet resultSet) throws SQLException {
	    Purchase purchase = new Purchase();
	    purchase.setPurchase_id(resultSet.getInt("purchase_id"));
	    purchase.setCreated(new DateTime(resultSet.getTimestamp("created")));
	    purchase.setUser_id(resultSet.getInt("user_id"));
	    purchase.setCosmetic_id(resultSet.getInt("cosmetic_id"));
	    return purchase;
	}
}
