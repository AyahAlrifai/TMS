import java.time.LocalDate;
// datatype of category is class<Category> not integer
//constructor receive category as class<Category> 
//edit get and set methods for category
public class TransactionArgument {
	public int id;
	public int type;
	public double amount;
	public Category category;
	public String comment;
	public LocalDate date;
	public int pymentMethod;
	public int monthFrequent;
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


}
