package beans;

import org.joda.time.DateTime;

public class Maze {

	private Integer maze_id;
	private DateTime created;
	private Integer user_id;
	private String name;
	private String filename;
	private Integer price;

	public Maze() {}
	
	public Integer getMaze_id() {return maze_id;}
	public void setMaze_id(Integer maze_id) {this.maze_id = maze_id;}
	
	public DateTime getCreated() {return created;}
	public void setCreated(DateTime created) {this.created = created;}
	
	public Integer getUser_id() {return user_id;}
	public void setCreator_id(Integer user_id) {this.user_id = user_id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}
	
	public Integer getPrice() {return price;}
	public void setPrice(Integer price) {this.price = price;}
}
