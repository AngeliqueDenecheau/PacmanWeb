package beans;

import org.joda.time.DateTime;

public class Game {
	
	private Integer game_id;
	private DateTime created;
	private String gamemode;
	private Integer winner;
	private Integer maze_id;
	
	public Game() {}
	
	public Integer getGame_id() {return game_id;}
	public void setGame_id(Integer game_id) {this.game_id = game_id;}
	
	public DateTime getCreated() {return created;}
	public void setCreated(DateTime created) {this.created = created;}
	
	public String getGamemode() {return gamemode;}
	public void setGamemode(String gamemode) {this.gamemode = gamemode;}
	
	public Integer getWinner() {return winner;}
	public void setWinner(Integer winner) {this.winner = winner;}
	
	public Integer getMaze_id() {return maze_id;}
	public void setMaze_id(Integer maze_id) {this.maze_id = maze_id;}
}
