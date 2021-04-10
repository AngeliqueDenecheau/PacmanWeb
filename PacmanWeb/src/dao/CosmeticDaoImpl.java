package dao;

import static dao.DAOUtilitaire.close;
import static dao.DAOUtilitaire.initRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Cosmetic;

public class CosmeticDaoImpl implements CosmeticDao {

	private DAOFactory daoFactory;

	private static final String SQL_SELECT = "SELECT * FROM Cosmetics ORDER BY cosmetic_id";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM Cosmetics WHERE cosmetic_id = ?";
	
	public CosmeticDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public Cosmetic find(int cosmetic_id) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Cosmetic cosmetic = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
			preparedStatement = initRequest(connexion, SQL_SELECT_BY_ID, false, cosmetic_id);				
	        resultSet = preparedStatement.executeQuery();
			connexion.commit();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {cosmetic = map(resultSet);}
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(resultSet, preparedStatement, connexion);
	    }

	    return cosmetic;
	}

	@Override
	public List<Cosmetic> allCosmetics() throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Cosmetic> cosmetics = new ArrayList<Cosmetic>();

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
			connexion.commit();
            while (resultSet.next()) {
            	cosmetics.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }

        return cosmetics;
	}

	/* Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table des cosmétiques (un ResultSet) et un bean Cosmetic */
	private static Cosmetic map(ResultSet resultSet) throws SQLException {
	    Cosmetic cosmetic = new Cosmetic();
	    cosmetic.setCosmetic_id(resultSet.getInt("cosmetic_id"));
	    cosmetic.setName(resultSet.getString("name"));
	    cosmetic.setType(resultSet.getString("type"));
	    cosmetic.setPrice(resultSet.getInt("price"));
	    cosmetic.setImage_filename(resultSet.getString("image_filename"));
	    return cosmetic;
	}
}
