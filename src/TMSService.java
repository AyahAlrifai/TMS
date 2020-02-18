import java.util.List;

public interface TMSService {
	public List<Transaction> getTransatcions(TransactionFilters filters);
	
	public List<Category> getCategories(Integer type);
	
	public Boolean addIncome(Income income);
	
	public Boolean addExpense(Expense expense);
	
	public Boolean addCategory(Category category);
	
	public Boolean removeCategory(Integer id);
	
	public Boolean updateCategory(Category category);
	
	public Boolean updateTranFrequant(Integer transactionId,Integer monthFrequent);
		
	public double getBalance(TransactionFilters filters);
}
