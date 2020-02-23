import java.time.LocalDate;

public class FrequentIncome extends Income implements Frequent {
	private int monthFrequent;
	public FrequentIncome(int id, int type, double amount, Category category, String comment, LocalDate date,
			int monthFrequent) {
		super(id, type, amount, category, comment, date);
		this.monthFrequent=monthFrequent;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getMonthFrequent() {
		// TODO Auto-generated method stub
		return monthFrequent;
	}
	@Override
	public void setMonthFrequent(int monthFrequent) {
		this.monthFrequent=monthFrequent;
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		return "FrequentIncome\tid:" + this.getId() + "\ttype:" + this.getType() + "\tamount:" + this.getAmount()
				+ "\tdate:" + this.getDate() + "\tcategory:" + this.getCategory().getId() + "\tcomment:"
				+ this.getComment() + "\t\t\t\t\tfrequent:" + this.getMonthFrequent();

	}

}
