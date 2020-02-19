import java.time.LocalDate;

public class FrequentIncome extends Income implements Frequent {
	private int monthFrequent;
	public FrequentIncome(int id, int type, double amount, int category, String comment, LocalDate date,
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

}
