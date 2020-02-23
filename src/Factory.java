
public class Factory {

	public static TransactionBase createTransaction(String transactionType, TransactionArgument arguments) {

		switch (transactionType) {
		case "Income":
			return new Income(arguments.id, arguments.type, arguments.amount, arguments.category, arguments.comment,
					arguments.date);
		case "Expense":
			return new Expense(arguments.id, arguments.type, arguments.amount, arguments.category, arguments.comment,
					arguments.date, arguments.pymentMethod);
		case "FrequentIncome":
			return new FrequentIncome(arguments.id, arguments.type, arguments.amount, arguments.category,
					arguments.comment, arguments.date, arguments.monthFrequent);
		case "FrequentExpense":
			return new FrequentExpense(arguments.id, arguments.type, arguments.amount, arguments.category,
					arguments.comment, arguments.date, arguments.pymentMethod, arguments.monthFrequent);
		default:
			return null;
		}
	}
}
