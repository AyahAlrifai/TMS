import java.time.LocalDate;

public class FrequentExpense extends Expense implements Frequent {

	private int monthFrequent;

	public FrequentExpense(int id, int type, double amount, int category, String comment, LocalDate date,
			int pymentMethod, int monthFrequent) {
		super(id, type, amount, category, comment, date, pymentMethod);
		this.monthFrequent = monthFrequent;
	}

	@Override
	public int getMonthFrequent() {
		return monthFrequent;
	}

	@Override
	public void setMonthFrequent(int monthFrequent) {
		this.monthFrequent = monthFrequent;

	}

}
