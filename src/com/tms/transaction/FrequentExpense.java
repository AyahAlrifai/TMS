package com.tms.transaction;

import java.time.LocalDate;

public class FrequentExpense extends Expense implements Frequent {

	private int monthFrequent;

	FrequentExpense(int id, int type, double amount, Category category, String comment, LocalDate date,
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

	@Override
	public String toString() {
		return "FrequentExpense\tid:" + this.getId() + "\ttype:" + this.getType() + "\tamount:" + this.getAmount()
				+ "\tdate" + this.getDate() + "\tcategory:" + this.getCategory().getId() + "\tcomment:"
				+ this.getComment() + "\tpaymentMethod:" + this.getPymentMethod() + "\tfrequent:"
				+ this.getMonthFrequent();

	}

}
