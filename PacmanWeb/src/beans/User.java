package beans;

import org.joda.time.DateTime;

public class User {
	
	private Integer user_id;
	private String token;
	private DateTime created;
	private String login;
	private String email;
	private String password;
	private String image;
	private Integer money;
	private Integer score;
	private Integer parties_jouees;
	private Integer parties_gagnees;
	private Integer pacman_skin;
	private Integer ghost_skin;
	
	public User() {}
	
	public Integer getUser_id() {return user_id;}
	public void setUser_id(Integer user_id) {this.user_id = user_id;}
	
	public String getToken() {return token;}
	public void setToken(String token) {this.token = token;}
	
	public DateTime getCreated() {return created;}
	public void setCreated(DateTime created) {this.created = created;}
	
	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}

	public String getImage() {return image;}
	public void setImage(String image) {this.image = image;}
	
	public Integer getMoney() {return money;}
	public void setMoney(Integer money) {this.money = money;}
	
	public Integer getScore() {return score;}
	public void setScore(Integer score) {this.score = score;}

	public Integer getParties_jouees() {return parties_jouees;}
	public void setParties_jouees(Integer parties_jouees) {this.parties_jouees = parties_jouees;}

	public Integer getParties_gagnees() {return parties_gagnees;}
	public void setParties_gagnees(Integer parties_gagnees) {this.parties_gagnees = parties_gagnees;}

	public Integer getPacman_skin() {return pacman_skin;}
	public void setPacman_skin(Integer pacman_skin) {this.pacman_skin = pacman_skin;}

	public Integer getGhost_skin() {return ghost_skin;}
	public void setGhost_skin(Integer ghost_skin) {this.ghost_skin = ghost_skin;}
}
