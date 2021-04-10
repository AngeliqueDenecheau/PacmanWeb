package beans;

import org.joda.time.DateTime;

public class Game {
	
	private Integer game_id;
	private DateTime created;
	private String gamemode;
	private Integer player_one;
	private Integer player_two;
	private Integer winner;
	private Integer maze_id;
	
	public Game() {}
	
	public Integer getGame_id() {return game_id;}
	public void setGame_id(Integer game_id) {this.game_id = game_id;}
	
	public DateTime getCreated() {return created;}
	public void setCreated(DateTime created) {this.created = created;}
	
	public String getGamemode() {return gamemode;}
	public void setGamemode(String gamemode) {this.gamemode = gamemode;}

	public Integer getPlayer_one() {return player_one;}
	public void setPlayer_one(Integer player_one) {this.player_one = player_one;}

	public Integer getPlayer_two() {return player_two;}
	public void setPlayer_two(Integer player_two) {this.player_two = player_two;}
	
	public Integer getWinner() {return winner;}
	public void setWinner(Integer winner) {this.winner = winner;}
	
	public Integer getMaze_id() {return maze_id;}
	public void setMaze_id(Integer maze_id) {this.maze_id = maze_id;}
}
