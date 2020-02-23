import java.util.List;
//type of list TransactionBase in getTransatcions method
public interface TMSService {
	//ayah
	public List<TransactionBase> getTransatcions(TransactionFilters filters);
	//ayah
	public List<Category> getCategories(Integer type);
	//samer
	public void addIncome(Income income);
	//raneem
	public void addExpense(Expense expense);
	//deema
	public void addCategory(Category category);
	//samar
	public void removeCategory(Integer id);
	//deema
	public void updateCategory(Category category);
	//raneem
	public void updateTranFrequant(Integer transactionId,Integer monthFrequent);
	//deema/ayah
	public double getBalance(TransactionFilters filters);
	
	public Category getCategory(Integer id);
	
	public int getMonthFrequent(int id);

}
