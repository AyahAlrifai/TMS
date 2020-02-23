import java.time.LocalDate;
public class Expense extends TransactionBase {
	private int paymentMethod;

	public Expense(int id, int type, double amount, Category category, String comment, LocalDate date, int paymentMethod) {
		super(id, type, amount, category, comment, date);
		this.paymentMethod = paymentMethod;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "EXpense\t\tid:"+this.getId()+"\ttype:"+this.getType()+"\tamount:"+this.getAmount()+"\tdate:"+this.getDate()+"\tcategory:"+this.getCategory().getId()+"\tcomment:"+this.getComment()+"\tpaymentMethod:"+this.getPaymentMethod();
	}
}
