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

}
