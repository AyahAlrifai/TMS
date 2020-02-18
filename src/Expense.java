import java.time.LocalDate;

public class Expense extends TransactionBase {
	private int paymentMethod;

	public Expense(int id, int type, double amount, int category, String comment, LocalDate date, int paymentMethod) {
		super(id, type, amount, category, comment, date);
		this.paymentMethod = paymentMethod;
	}

	public int getPymentMethod() {
		return paymentMethod;
	}

	public void setPymentMethod(int pymentMethod) {
		this.paymentMethod = pymentMethod;
	}

}
