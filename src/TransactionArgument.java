import java.time.LocalDate;
//change int ti Integer, double to Double
public class TransactionArgument {
	public TransactionArgument(Integer id, Integer type, Double amount, Category category, String comment, LocalDate date,
			Integer pymentMethod, Integer monthFrequent) {
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
	private Integer id;
	private Integer type;
	private Double amount;
	private Category category;
	private String comment;
	private LocalDate date;
	private Integer pymentMethod;
	private Integer monthFrequent;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
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
	public Integer getPymentMethod() {
		return pymentMethod;
	}
	public void setPymentMethod(Integer pymentMethod) {
		this.pymentMethod = pymentMethod;
	}
	public Integer getMonthFrequent() {
		return monthFrequent;
	}
	public void setMonthFrequent(Integer monthFrequent) {
		this.monthFrequent = monthFrequent;
	}
}
