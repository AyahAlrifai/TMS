public class Factory {
	public static TransactionBase createTransaction(String transactionType, TransactionArgument arguments) {
		switch (transactionType) {
		case "Income":
			return new Income(arguments.getId(), arguments.getType(), arguments.getAmount(), arguments.getCategory(), arguments.getComment(),
					arguments.getDate());
		case "Expense":
			return new Expense(arguments.getId(), arguments.getType(), arguments.getAmount(), arguments.getCategory(), arguments.getComment(),
					arguments.getDate(), arguments.getPymentMethod());
		case "FrequentIncome":
			return new FrequentIncome(arguments.getId(), arguments.getType(), arguments.getAmount(), arguments.getCategory(),
					arguments.getComment(), arguments.getDate(), arguments.getMonthFrequent());
		case "FrequentExpense":
			return new FrequentExpense(arguments.getId(), arguments.getType(), arguments.getAmount(), arguments.getCategory(),
					arguments.getComment(), arguments.getDate(), arguments.getPymentMethod(), arguments.getMonthFrequent());
		default:
			return null;
		}
	}
}
