public class Category {
	private int id;
	private String value;
	private String iconPath;
	public Category(int id, String value, String iconPath) {
		super();
		this.id = id;
		this.value = value;
		this.iconPath = iconPath;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

}
