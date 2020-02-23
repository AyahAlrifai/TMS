import java.sql.SQLException;
import java.util.List;

public interface TMSService {
	// ayah
	public List<TransactionBase> getTransatcions(TransactionFilters filters);

	// ayah
	public List<Category> getCategories(Integer type);

	// samar
	public void addIncome(Income income) throws SQLException;

	// raneem
	public void addExpense(Expense expense) throws SQLException;

	// deema
	public void addCategory(Category category) throws SQLException;

	// samar
	public void removeCategory(Integer id) throws SQLException;

	// deema
	public void updateCategory(Category category) throws SQLException;

	// raneem
	public void updateTranFrequant(Integer transactionId, Integer monthFrequent) throws SQLException;

	// deema/ayah
	public double getBalance(TransactionFilters filters) throws SQLException;
	
	public Category getCategory(int id);
	
	public int getMonthFrequent(int id);
}
