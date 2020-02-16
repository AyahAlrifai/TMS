import java.time.LocalDate;
import java.util.List;

public interface TMSServices {
	public List<Transaction> getTransatcions(Integer type,Integer category,LocalDate from,LocalDate to ,int monthFrequent);
	public List<Category> getCategorys(Integer type);
	
	public Boolean addIncome(Income income);
	public Income addIncome(int type,double amount,LocalDate date ,String comment,int category,int monthFrequent);
	
	public Boolean addExpense(Expense expense);
	public Expense addExpense(int type,double amount,LocalDate date ,String comment,int category,int pymentMethod,int monthFrequent);
	
	public Boolean addCategory(Category category);
	public Category addCategory(int type,String value,String iconPath);
	
	public Boolean updateCategory(String value,String iconPath);
	//set monthly frequent for any transaction 
	public Boolean setFrequant(int id,int monthFrequent);
	
	public double getBalance(List<Transaction> transactions);
}
