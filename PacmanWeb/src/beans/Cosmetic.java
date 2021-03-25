package beans;

public class Cosmetic {

	private Integer cosmetic_id;
	private String name;
	private String type;
	private Integer price;
	private String image_filename;
	
	public Cosmetic() {}
	
	public Integer getCosmetic_id() {return cosmetic_id;}
	public void setCosmetic_id(Integer cosmetic_id) {this.cosmetic_id = cosmetic_id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public Integer getPrice() {return price;}
	public void setPrice(Integer price) {this.price = price;}
	
	public String getImage_filename() {return image_filename;}
	public void setImage_filename(String image_filename) {this.image_filename = image_filename;}
}
