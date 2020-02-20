import java.sql.SQLException;
import java.util.List;

public interface TMSService {
	//ayah
	public List<Transaction> getTransatcions(TransactionFilters filters);
	//ayah
	public List<Category> getCategories(Integer type);
	//samer
	public Boolean addIncome(Income income);
	//raneem
	public Boolean addExpense(Expense expense);
	//deema
	public Boolean addCategory(Category category) throws SQLException;
	//samar
	public Boolean removeCategory(Integer id) throws SQLException;
	//deema
	public Boolean updateCategory(Category category) throws SQLException;
	//raneem
	public Boolean updateTranFrequant(Integer transactionId,Integer monthFrequent);
	//deema/ayah
	public double getBalance(TransactionFilters filters) throws SQLException;
}
