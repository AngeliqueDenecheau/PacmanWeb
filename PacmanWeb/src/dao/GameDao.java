package dao;

import java.util.List;

import beans.Game;

public interface GameDao {
	void create(Game game) throws DAOException;

	List<Game> find_by_user(int user_id) throws DAOException;
}
