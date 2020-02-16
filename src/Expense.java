import java.time.LocalDate;

public class Expense extends Transaction {
	private int pymentMethod;

	public Expense(int id, int type, double amount, int category, String comment, LocalDate date,int pymentMethod, int monthFrequent) {
		super(id, type, amount, category, comment, date, monthFrequent);
		this.pymentMethod=pymentMethod;
	}
	public int getPymentMethod() {
		return pymentMethod;
	}
	public void setPymentMethod(int pymentMethod) {
		this.pymentMethod = pymentMethod;
	}

}
