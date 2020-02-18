import java.util.List;

public class TMSServiceDatabaseImpl implements TMSService {

	@Override
	public List<Transaction> getTransatcions(TransactionFilters filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCategories(Integer type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addIncome(Income income) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addExpense(Expense expense) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeCategory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateTranFrequant(Integer transactionId, Integer monthFrequent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBalance(TransactionFilters filters) {
		// TODO Auto-generated method stub
		return 0;
	}

}
