package beans;

import org.joda.time.DateTime;

public class Purchase {
	
	private Integer purchase_id;
	private DateTime created;
	private Integer user_id;
	private String type;
	private Integer item_id;
	
	public Purchase() {}
	
	public Integer getPurchase_id() {return purchase_id;}
	public void setPurchase_id(Integer purchase_id) {this.purchase_id = purchase_id;}
	
	public DateTime getCreated() {return created;}
	public void setCreated(DateTime created) {this.created = created;}
	
	public Integer getUser_id() {return user_id;}
	public void setUser_id(Integer user_id) {this.user_id = user_id;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public Integer getItem_id() {return item_id;}
	public void setItem_id(Integer item_id) {this.item_id = item_id;}
}
