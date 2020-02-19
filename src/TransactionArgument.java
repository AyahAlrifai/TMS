import java.time.LocalDate;
// datatype of category is class<Category> not integer
//constructor receive category as class<Category> 
//edit get and set methods for category
public class TransactionArgument {
	private int id;
	private int type;
	private double amount;
	private Category category;
	private String comment;
	private LocalDate date;
	private int pymentMethod;
	private int monthFrequent;

	public TransactionArgument(int id, int type, double amount, Category category, String comment, LocalDate date,
			int pymentMethod, int monthFrequent) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.category = category;
		this.comment = comment;
		this.date = date;
		this.pymentMethod = pymentMethod;
		this.monthFrequent = monthFrequent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getPymentMethod() {
		return pymentMethod;
	}

	public void setPymentMethod(int pymentMethod) {
		this.pymentMethod = pymentMethod;
	}

	public int getMonthFrequent() {
		return monthFrequent;
	}

	public void setMonthFrequent(int monthFrequent) {
		this.monthFrequent = monthFrequent;
	}

}
