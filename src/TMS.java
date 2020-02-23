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

		Category cat = new Category(15, 1, "", "");
		TransactionArgument argss = new TransactionArgument(1, 15, 10.0, cat, "", LocalDate.of(2005, 12, 06), null, null);
		TransactionBase income = Factory.createTransaction("Income", argss);
		 tms.addIncome((Income) income);
		 
		TransactionArgument fArgs = new TransactionArgument(2, 15, 119.0, cat, "", LocalDate.of(2005, 2, 06), null, 17);
		TransactionBase incomeF=Factory.createTransaction("FrequentIncome", fArgs);
		tms.addIncome((FrequentIncome) incomeF);
		
		tms.removeCategory(23);
		 
		/*// new TransactionFilters(type, category, from, to, frequent)
		TransactionFilters filter = new TransactionFilters(15, null, LocalDate.of(2005, 12, 1), null, false);
		double balance = tms.getBalance(filter);
		System.out.println(balance);*/
	}
}

class Services {

	Connection connection;
	String database;
	String userName;
	String password;
	PreparedStatement pst;

	public Services() throws ClassNotFoundException, SQLException {

		try {
			// Class.forName("com.mysql.jdbc.Driver");
			DataInputStream reader = new DataInputStream(new FileInputStream("configFile.txt"));
			this.database = reader.readLine();
			this.userName = reader.readLine();
			this.password = reader.readLine();
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.database, this.userName,
					this.password);

		} catch (Exception e) {

		}

		// connection =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.database,this.userName,this.password);
	}

	public void InsertTransaction(int type, double amount, LocalDate date, String comment, int category)
			throws SQLException {
		// pass date as LocalDate.parse("2019-11-01")
		try {
			Statement st = connection.createStatement();
			String insertQuery = "insert into transaction (type, amount, date, comment, category) values ('" + type
					+ "','" + amount + "','" + date + "','" + comment + "','" + category + "')";
			st.executeUpdate(insertQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void getTable(String tableName) {

		try {
			Statement s = connection.createStatement();
			String showQuery = "select * from " + tableName;
			ResultSet r = s.executeQuery(showQuery);

			while (r.next()) {
				System.out.println(r.getInt(1) + " " + r.getInt(2) + " " + r.getDouble(3));

			}

		} catch (Exception e) {

		}
	}

	public int Mapping(String dictEntry) throws SQLException {
		int dictKey = 0;

		String mapQuery = "select dictionaryentries.id\r\n" + "From dictionaryentries\r\n" + "where value=?";

		pst = connection.prepareStatement(mapQuery);
		pst.setString(1, dictEntry);
		ResultSet r = pst.executeQuery();
		while (r.next()) {
			dictKey = r.getInt(1);
		}
		return dictKey;
	}

}