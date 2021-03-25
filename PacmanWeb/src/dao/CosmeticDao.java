package dao;

import java.util.List;

import beans.Cosmetic;

public interface CosmeticDao {
	
	Cosmetic find(String cosmetic_id) throws DAOException;
    
    List<Cosmetic> allCosmetics() throws DAOException;
}
