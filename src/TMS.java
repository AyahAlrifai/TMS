import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class TMS {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		TMSServiceDatabaseImpl tms=new TMSServiceDatabaseImpl();
		//(Integer type, Integer category, LocalDate from, LocalDate to, Boolean frequent) {
		TransactionFilters f=new TransactionFilters(null,1,LocalDate.of(2020, 1, 1),LocalDate.of(2020, 2, 2),null);
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
/*		TMSServiceDatabaseImpl tms = new TMSServiceDatabaseImpl();
		Category c = new Category(15, 1, null, null);

		TransactionArgument arg = new TransactionArgument(0, 16, 30.0, c, "hello", LocalDate.now(), 13, 0);
		TransactionBase TBexpense = Factory.createTransaction("Income", arg);
		tms.addIncome((Income) TBexpense);
		Category c = new Category(16, 2, null, null);

		TransactionArgument arg = new TransactionArgument(0, 16, 111222.0, c, "", LocalDate.now(), 13,15);
		TransactionBase TBexpense = Factory.createTransaction("FrequentExpense", arg);
		tms.addExpense((FrequentExpense) TBexpense);
		tms.updateTranFrequant(6, 0);
		 Category(int id, int dkey, String value, String iconPath);
		 Category c= new Category(null ,2,"Shopping", "C:\\Users\\deema\\Desktop\\TrainingSSCD\\food.jbg");
		 tms.addCategory(c);
		 tms.removeCategory(169);
		 Category c2= new Category(167 ,2,"no", "C:\\Users\\deema\\Desktop\\TrainingSSCD\\food.jbg");
		 tms.updateCategory(c2);
		TransactionFilters filter = new TransactionFilters(15, null, LocalDate.of(2005, 12, 1), null, false);
		double balance = tms.getBalance(filter);
		System.out.println(balance);
		*/
	}
}