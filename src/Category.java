
public class Category {
	public Category(int id, int dkey, String value, String iconPath) {
		super();
		this.id = id;
		this.value = value;
		this.iconPath = iconPath;
		this.dkey = dkey;
	}

	private int id;
	private String value;
	private int dkey;
	private String iconPath;

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

	public int getDkey() {
		return dkey;
	}

	public void setDkey(int dkey) {
		this.dkey = dkey;
	}
	
	@Override
	public String toString() {
		return "Category id:" + id + "\tdkey:" + dkey +  "\tvalue:" + value + "\ticonPath:" + iconPath;
	}
}
