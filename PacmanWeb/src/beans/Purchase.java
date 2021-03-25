package beans;

import org.joda.time.DateTime;

public class Purchase {
	
	private Integer purchase_id;
	private DateTime created;
	private Integer user_id;
	private Integer cosmetic_id;
	
	public Purchase() {}
	
	public Integer getPurchase_id() {return purchase_id;}
	public void setPurchase_id(Integer purchase_id) {this.purchase_id = purchase_id;}
	
	public DateTime getCreated() {return created;}
	public void setCreated(DateTime created) {this.created = created;}
	
	public Integer getUser_id() {return user_id;}
	public void setUser_id(Integer user_id) {this.user_id = user_id;}
	
	public Integer getCosmetic_id() {return cosmetic_id;}
	public void setCosmetic_id(Integer cosmetic_id) {this.cosmetic_id = cosmetic_id;}
}
