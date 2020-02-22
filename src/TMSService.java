import java.util.List;
//type of list TransactionBase in getTransatcions method
public interface TMSService {
	//ayah
	public List<TransactionBase> getTransatcions(TransactionFilters filters);
	//ayah
	public List<Category> getCategories(Integer type);
	//samer
	public Boolean addIncome(Income income);
	//raneem
	public Boolean addExpense(Expense expense);
	//deema
	public Boolean addCategory(Category category);
	//samar
	public Boolean removeCategory(Integer id);
	//deema
	public Boolean updateCategory(Category category);
	//raneem
	public Boolean updateTranFrequant(Integer transactionId,Integer monthFrequent);
	//deema/ayah
	public double getBalance(TransactionFilters filters);
	
	public Category getCategory(Integer id);
	
	public int getMonthFrequent(int id);

}
