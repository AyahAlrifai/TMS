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
		
		//Category(int id, int dkey, String value, String iconPath);
		 Category c= new Category(null ,2,"yjc", null);
		 tms.addCategory(c);
		 tms.removeCategory(166);
		 Category c2= new Category(166 ,2,"ers", null);
		 tms.updateCategory(c2);
		TransactionFilters filter = new TransactionFilters(15, null, LocalDate.of(2005, 12, 1), null, false);
		double balance = tms.getBalance(filter);
		System.out.println(balance);
	}
}