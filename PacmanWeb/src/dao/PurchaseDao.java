package dao;

import java.util.List;

import beans.Purchase;

public interface PurchaseDao {

	List<Purchase> find_by_cosmetic(int cosmetic_id) throws DAOException;
	List<Purchase> find_by_user(int user_id) throws DAOException;
    
    List<Purchase> allPurchases() throws DAOException;
}
