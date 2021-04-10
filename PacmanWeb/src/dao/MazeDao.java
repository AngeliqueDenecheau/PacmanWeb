package dao;

import java.util.List;

import beans.Maze;

public interface MazeDao {
	
	Maze find(int maze_id) throws DAOException;
	Maze find(String filename) throws DAOException;
    
    List<Maze> allMazes() throws DAOException;	
}
