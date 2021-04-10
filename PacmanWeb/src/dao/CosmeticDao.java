package dao;

import java.util.List;

import beans.Cosmetic;

public interface CosmeticDao {
	
	Cosmetic find(int cosmetic_id) throws DAOException;
    
    List<Cosmetic> allCosmetics() throws DAOException;
}
