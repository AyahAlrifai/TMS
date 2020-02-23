import java.time.LocalDate;

public class Income extends TransactionBase {

	public Income(int id, int type, double amount, Category category, String comment, LocalDate date) {
		super(id, type, amount, category, comment, date);
	}

	
}
