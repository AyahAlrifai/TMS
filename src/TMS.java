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
		/*
		 * Category cat= new Category(15,1,"","");
		 * 
		 * TransactionArgument argss= new TransactionArgument
		 * (null,null,null,null,null,null,null,null); argss.amount=10.0; argss.id =20
		 * ;//what should be ?? argss.type = 15; argss.category = cat; argss.comment =
		 * "success"; argss.date = LocalDate.of(2005, 12, 06);
		 * 
		 * TransactionBase income= Factory.createTransaction("Income", argss); Boolean
		 * b=tms.addIncome((Income) income); System.out.println(b);
		 */
		// new TransactionFilters(type, category, from, to, frequent)LocalDate.of(2005, 12, 1)
		TransactionFilters filter = new TransactionFilters(15, null, LocalDate.of(2005, 12, 1), null, false);
		double balance = tms.getBalance(filter);
		System.out.println(balance);
	}
}