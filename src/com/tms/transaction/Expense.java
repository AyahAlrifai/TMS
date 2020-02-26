package com.tms.transaction;

import java.time.LocalDate;

public class Expense extends TransactionBase {

	private int paymentMethod;

	Expense(int id, int type, double amount, Category category, String comment, LocalDate date,
			int paymentMethod) {
		super(id, type, amount, category, comment, date);
		this.paymentMethod = paymentMethod;
	}

	public int getPymentMethod() {
		return paymentMethod;
	}

	public void setPymentMethod(int pymentMethod) {
		this.paymentMethod = pymentMethod;
	}

	@Override
	public String toString() {
		return "EXpense\t\tid:" + this.getId() + "\ttype:" + this.getType() + "\tamount:" + this.getAmount() + "\tdate:"
				+ this.getDate() + "\tcategory:" + this.getCategory().getId() + "\tcomment:" + this.getComment()
				+ "\tpaymentMethod:" + this.getPymentMethod();
	}

}
