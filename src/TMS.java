import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TMS {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		TMSServiceDatabaseImpl tms=new TMSServiceDatabaseImpl();
		//(Integer type, Integer category, LocalDate from, LocalDate to, Boolean frequent) {
		TransactionFilters f=new TransactionFilters(null,null,null,null,null);
		List<Category> expenseCategory=tms.getCategories(2);
		List<Category> incomeCategory=tms.getCategories(1);
		List<TransactionBase> transaction=tms.getTransatcions(f);
		System.out.println("Expense Category\n");
		expenseCategory.forEach((n) -> System.out.println(n)); 
		System.out.println("\n\n");
		System.out.println("Income Category\n");
		incomeCategory.forEach((n) -> System.out.println(n)); 
		System.out.println("\n\n");
		transaction.forEach((n) -> System.out.println(n+"\n-------------------------------------------------------------------\n")); 
	}
}
















