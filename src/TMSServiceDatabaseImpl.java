import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TMSServiceDatabaseImpl implements TMSService {
	private Connection connection;
	private String databaseName;
	private String userName;
	private String password;
	private PreparedStatement pstmt;
	private ResultSet result;

	public TMSServiceDatabaseImpl() throws ClassNotFoundException, SQLException {
		try {
			DataInputStream reader = new DataInputStream(new FileInputStream("configFile.txt"));
			this.databaseName = reader.readLine();
			this.userName = reader.readLine();
			this.password = reader.readLine();
		} catch (Exception e) {
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.databaseName, this.userName,
				this.password);
	}

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
	public void addIncome(Income income) {
		
	
	}

	@Override
	public void addExpense(Expense expense) {
		
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		pstmt = connection.prepareStatement("SELECT EXISTS(SELECT * from dictionaryentries WHERE dkey=? and value=?)");
		pstmt.setInt(1, category.getDkey());
		pstmt.setString(2, category.getValue());
		result = pstmt.executeQuery();
		result.next();
		if (result.getBoolean(1)== false) {
			pstmt = connection.prepareStatement("INSERT INTO dictionaryentries(dkey,value,icon) VALUES(?,?,?)");

			pstmt.setInt(1, category.getDkey());
			pstmt.setString(2, category.getValue());
			pstmt.setString(3, category.getIconPath());
			pstmt.executeUpdate();
		}
	}

	@Override
	public void removeCategory(Integer id) throws SQLException {

		pstmt = connection.prepareStatement("UPDATE  DictionaryEntries set enable='0' where id=?");

		pstmt.setInt(1, id);

		pstmt.executeUpdate(); 
			
	}

	@Override
	public void updateCategory(Category category) throws SQLException {

		pstmt = connection.prepareStatement("UPDATE  DictionaryEntries set dkey=?,value=?,icon=? where id=?");

		pstmt.setInt(1, category.getDkey());
		pstmt.setString(2, category.getValue());
		pstmt.setString(3, category.getIconPath());
		pstmt.setInt(4, category.getId());

		pstmt.executeUpdate() ;
			
	}

	@Override
	public void updateTranFrequant(Integer transactionId, Integer monthFrequent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBalance(TransactionFilters filters) throws SQLException {

		String sqlStatment = "";
		ResultSet total = null;
		if (filters.getType() == null) {
			// get all
			if (filters.getFrequent() != null) { // all frequent transaction
				if (filters.getFrequent() == true) {
					sqlStatment = ("SELECT sum(amount) from transaction as t  ,  frequenttransaction as ft where t.id=ft.id");
				} else { // all non frequent transactions
					sqlStatment = ("select sum(amount) from transaction as t  where transaction.id not in (select frequenttransaction.id from frequenttransaction)");
				}
			}
		} else if (filters.getType() == 15) {
			// get Income
			if (filters.getFrequent() != null) { // (income and frequent) transactions
				if (filters.getFrequent() == true) {
					sqlStatment = ("SELECT sum(amount) from transaction as t  ,  frequenttransaction as ft where  t.id=ft.id and t.type=15");

				} else { // (income and non-frequent) transactions
					sqlStatment = ("select sum(amount) from transaction as t  where type=15 and id not in (select id from frequenttransaction)  ");
				}
			}
		} else if (filters.getType() == 16) {
			// get Expense
			if (filters.getFrequent() != null) {
				if (filters.getFrequent() == true) {
					// ( expense and frequent ) transactions
					sqlStatment = ("SELECT sum(amount) from transaction as t  ,  frequenttransaction as ft where t.id=ft.id and t.type=16");

				} else { // (expense and non-frequent) transactions
					sqlStatment = ("select sum(amount) from transaction as t  where type=16 and id not in (select id from frequenttransaction)");
				}
			}
		}
		if (filters.getFrom() != null && filters.getTo() != null) {
			sqlStatment += " and t.date>=DATE(\"" + filters.getFrom() + "\") and t.date<=DATE(\"" + filters.getTo()
					+ "\")";
		} else if (filters.getFrom() != null) {
			sqlStatment += " and t.date>=DATE(\"" + filters.getFrom() + "\")";
		} else if (filters.getTo() != null) {
			sqlStatment += " and t.date<=DATE(\"" + filters.getTo() + "\")";
		}

		if (filters.getCategory() != null) {
			if (filters.getFrom() != null || filters.getTo() != null) {
				sqlStatment += " and t.category=" + filters.getCategory();
			} else {
				sqlStatment += " and t.category=" + filters.getCategory();
			}
		}

		
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			total = this.pstmt.executeQuery();
			if (total.next())
				return total.getDouble(1);
			return 0;

		

		

	}

}
