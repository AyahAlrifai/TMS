import java.time.LocalDate;

public class Expense extends TransactionBase {
	private int pymentMethod;

	public Expense(int id, int type, double amount, int category, String comment, LocalDate date, int pymentMethod) {
		super(id, type, amount, category, comment, date);
		this.pymentMethod = pymentMethod;
	}

	public int getPymentMethod() {
		return pymentMethod;
	}

	public void setPymentMethod(int pymentMethod) {
		this.pymentMethod = pymentMethod;
	}

}
