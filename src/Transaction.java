import java.time.LocalDate;

public class Transaction {
	private int id;
	private int type;
	private double amount;
	private int category;
	private String comment;
	private LocalDate date;
	private int monthFrequent;
	public Transaction(int id, int type, double amount, int category, String comment, LocalDate date,
			int monthFrequent) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.category = category;
		this.comment = comment;
		this.date = date;
		this.monthFrequent = monthFrequent;
	}
	public int getMonthFrequent() {
		return monthFrequent;
	}
	public void setMonthFrequent(int monthFrequent) {
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
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
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

}
