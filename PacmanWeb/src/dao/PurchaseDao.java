package dao;

import java.util.List;

import beans.Purchase;

public interface PurchaseDao {
	void create(Purchase purchase) throws DAOException;

	List<Purchase> find(int user_id, int item_id, String type) throws DAOException;
	List<Purchase> find_by_item(int item_id, String type) throws DAOException;
	List<Purchase> find_by_user(int user_id, String type) throws DAOException;
    
    List<Purchase> allPurchases() throws DAOException;
}
