import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.databaseName, this.userName,
				this.password);
	}

	@Override
	public List<TransactionBase> getTransatcions(TransactionFilters filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCategories(Integer type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addIncome(Income income) throws SQLException {

		String addTransactionQuery = "insert into transaction (type, amount, category, comment, date)"
				+ "values (?, ?, ?, ?, ?)";

		pstmt = connection.prepareStatement(addTransactionQuery);
		pstmt.setInt(1, income.getType()); // 15
		pstmt.setDouble(2, income.getAmount());
		pstmt.setInt(3, income.getCategory().getId()); // category in tran table is int //id or dkey
		pstmt.setString(4, income.getComment());
		pstmt.setDate(5, Date.valueOf(income.getDate()));
		pstmt.executeUpdate();

		int transId = 0;
		String idQuery = "select transaction.id from transaction \r\n"
				+ "  where type=15 AND transaction.id NOT IN (select income.id from income)";
		pstmt = connection.prepareStatement(idQuery);
		result = pstmt.executeQuery(idQuery);

		while (result.next()) {
			System.out.println("id= " + result.getInt(1));
			transId = result.getInt(1);
			System.out.println(transId);
		}

		String addIncomeQuery = "insert into income (id)  \r\n" + " values( ? ) ";
		pstmt = connection.prepareStatement(addIncomeQuery);
		pstmt.setInt(1, transId);
		pstmt.executeUpdate();

		if (income instanceof FrequentIncome) {

			String addIncomeFrequentQuery = " insert into frequenttransaction (id, monthFrequent) \r\n"
					+ "  values (?, ?)";

			pstmt = connection.prepareStatement(addIncomeFrequentQuery);
			pstmt.setInt(1, transId);
			pstmt.setInt(2, ((FrequentIncome) income).getMonthFrequent());

			pstmt.executeUpdate();
		}
	}

	@Override
	public void addExpense(Expense expense) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeCategory(Integer id) throws SQLException {
		String removeCategoryQuery = "update  DictionaryEntries set enable = '0' where id = ? ";
		pstmt = connection.prepareStatement(removeCategoryQuery);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
	}

	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateTranFrequant(Integer transactionId, Integer monthFrequent) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getBalance(TransactionFilters filters) throws SQLException {
		// try

		String sqlStatment = "";
		ResultSet total = null;
		if (filters.getType() == null) {
			// get all
			if (filters.getFrequent() != null) { // all frequent transaction
				sqlStatment = ("SELECT sum(amount) from transaction as t  ,  frequenttransaction as ft where t.id=ft.id");
			} else { // all non frequent transactions
				sqlStatment = ("select sum(amount) from transaction as t  where transaction.id not in (select frequenttransaction.id from frequenttransaction)");
			}
		} else if (filters.getType() == 15) {
			// get Income
			if (filters.getFrequent() != null) { // (income and frequent) transactions
				sqlStatment = ("SELECT sum(amount) from transaction as t  ,  frequenttransaction as ft where  t.id=ft.id and t.type=15");

			} else { // (income and non-frequent) transactions
				sqlStatment = ("select sum(amount) from transaction as t  where type=15 and id not in (select id from frequenttransaction)  ");
			}

		} else if (filters.getType() == 16) {
			// get Expense
			if (filters.getFrequent() != null) { // ( expense and frequent ) transactions
				sqlStatment = ("SELECT sum(amount) from transaction as t  ,  frequenttransaction as ft where t.id=ft.id and t.type=16");

			} else { // (expense and non-frequent) transactions
				sqlStatment = ("select sum(amount) from transaction as t  where type=16 and id not in (select id from frequenttransaction)");
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

		try {
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			total = this.pstmt.executeQuery();
			if (total.next())
				return total.getDouble(1);
			return 0;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;

		}

	}

	@Override
	public Category getCategory(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMonthFrequent(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
