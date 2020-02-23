//add dkey as attribute and build getter and setter
//override toString method 
public class Category {

	private int id;
	private int dkey;
	private String value;
	private String iconPath;

	public Category(int id, int dkey, String value, String iconPath) {
		super();
		this.id = id;
		this.value = value;
		this.iconPath = iconPath;
		this.dkey = dkey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDkey() {
		return dkey;
	}

	public void setDkey(int dkey) {
		this.dkey = dkey;
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

	@Override
	public String toString() {
		return "Category id:" + id + "\tdkey:" + dkey +  "\tvalue:" + value + "\ticonPath:" + iconPath;
	}
}