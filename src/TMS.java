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

public class TMS {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		TMSServiceDatabaseImpl tms = new TMSServiceDatabaseImpl();
//		Category c = new Category(15, 1, null, null);
//
//		TransactionArgument arg = new TransactionArgument(0, 16, 30.0, c, "hello", LocalDate.now(), 13, 0);
//		TransactionBase TBexpense = Factory.createTransaction("Income", arg);
//		tms.addIncome((Income) TBexpense);
/*
		Category c = new Category(16, 2, null, null);

		TransactionArgument arg = new TransactionArgument(0, 16, 111222.0, c, "", LocalDate.now(), 13,15);
		TransactionBase TBexpense = Factory.createTransaction("FrequentExpense", arg);
		tms.addExpense((FrequentExpense) TBexpense);
		*/
		tms.updateTranFrequant(6, 0);
		// Category(int id, int dkey, String value, String iconPath);
//		 Category c= new Category(null ,2,"Shopping", "C:\\Users\\deema\\Desktop\\TrainingSSCD\\food.jbg");
//		 tms.addCategory(c);
		// tms.removeCategory(169);
//		 Category c2= new Category(167 ,2,"no", "C:\\Users\\deema\\Desktop\\TrainingSSCD\\food.jbg");
//		 tms.updateCategory(c2);
//		TransactionFilters filter = new TransactionFilters(15, null, LocalDate.of(2005, 12, 1), null, false);
//		double balance = tms.getBalance(filter);
//		System.out.println(balance);
	}
}