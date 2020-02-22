import java.sql.SQLException;
import java.util.List;

public interface TMSService {

	public List<Transaction> getTransatcions(TransactionFilters filters);
	
	public List<Category> getCategories(Integer type);
	
	public void addIncome(Income income);
	
	public void addExpense(Expense expense);
	
	public void addCategory(Category category) throws SQLException;
	
	public void removeCategory(Integer id) throws SQLException;
	
	public void updateCategory(Category category) throws SQLException;
	
	public void updateTranFrequant(Integer transactionId,Integer monthFrequent);

	public double getBalance(TransactionFilters filters) throws SQLException;
}
