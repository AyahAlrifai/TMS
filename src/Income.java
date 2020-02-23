import java.time.LocalDate;
//constructor receive category as class<Category> not integer !!
//override toString method
public class Income extends TransactionBase {

	public Income(int id, int type, double amount, Category category, String comment, LocalDate date) {
		super(id, type, amount, category, comment, date);
	}

	@Override
	public String toString() {
		return "Income\t\tid:"+this.getId()+"\ttype:"+this.getType()+"\tamount:"+this.getAmount()+"\tdate:"+this.getDate()+"\tcategory:"+this.getCategory().getId()+"\tcomment:"+this.getComment();

	}

}