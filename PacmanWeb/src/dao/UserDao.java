package dao;

import java.util.List;

import beans.User;

public interface UserDao {
	void create(User user) throws DAOException;
	
    User find_by_login(String login) throws DAOException;
    User find_by_email(String email) throws DAOException;
    User find_by_ids(String login, String password) throws DAOException;
    
    List<User> allUsers() throws DAOException;
    
    void update(User user) throws DAOException;
    
    void delete(User user) throws DAOException;
}
