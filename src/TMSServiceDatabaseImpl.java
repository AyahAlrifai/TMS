
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	public TMSServiceDatabaseImpl() throws ClassNotFoundException, SQLException, IOException {

		DataInputStream reader = new DataInputStream(new FileInputStream("configFile.txt"));
		this.databaseName = reader.readLine();
		this.userName = reader.readLine();
		this.password = reader.readLine();

		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.databaseName, this.userName,
				this.password);
	}

	@Override
	public int getMonthFrequent(int id) throws SQLException {
		try {
			String sqlStatment = "select monthFrequent from FrequentTransaction where id=?";
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, id);
			ResultSet monthFrequent = this.pstmt.executeQuery();
			monthFrequent.next();
			return monthFrequent.getInt("monthFrequent");
		} catch (Exception e) {

		}
		return 0;
	}

	@Override
	public Category getCategory(Integer id) throws SQLException {
		try {
			String sqlStatment = "select dkey,value,icon from DictionaryEntries where id=? and enable=true;\n";
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, id);
			ResultSet categortData = this.pstmt.executeQuery();
			categortData.next();
			return new Category(this.result.getInt("category"), categortData.getInt("dkey"),
					categortData.getString("value"), categortData.getString("icon"));
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public List<TransactionBase> getTransatcions(TransactionFilters filters) throws SQLException {
		String sqlStatment = "";
		if (filters.getType() == null) {
			// get all
			if (filters.getFrequent() != null) {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(d.id,0) as \"paymentMethod\",COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
						+ "FROM Transaction as t\n" + "LEFT JOIN Expense as e\n" + "ON t.id=e.id\n"
						+ "LEFT JOIN Income as i\n" + "ON t.id=i.id\n" + "LEFT JOIN DictionaryEntries as d\n"
						+ "ON e.paymentMethod=d.id\n" + "INNER JOIN FrequentTransaction as f \n" + "ON t.id=f.id\n";
			} else {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(d.id,0) as \"paymentMethod\",COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
						+ "FROM Transaction as t\n" + "LEFT JOIN Expense as e\n" + "ON t.id=e.id\n"
						+ "LEFT JOIN Income as i\n" + "ON t.id=i.id\n" + "LEFT JOIN DictionaryEntries as d\n"
						+ "ON e.paymentMethod=d.id\n";
			}
		} else if (filters.getType() == 15) {
			// get Income
			if (filters.getFrequent() != null) {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(t.comment,'no comment') as \"comment\",f.monthFrequent as \"Month Frequenr\" \n"
						+ "FROM Transaction as t\n" + "INNER JOIN Income as i\n" + "ON t.id=i.id\n"
						+ "INNER JOIN FrequentTransaction as f\n" + "ON t.id=f.id\n";

			} else {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\"\n"
						+ "FROM Transaction as t\n" + "INNER JOIN Income as i\n" + "ON t.id=i.id\n";
			}

		} else if (filters.getType() == 16) {
			// get Expense
			if (filters.getFrequent() != null) {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,d.id as \"paymentMethod\",COALESCE(t.comment,'no comment') \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
						+ "FROM Transaction as t\n" + "INNER JOIN Expense as e\n" + "ON t.id=e.id\n"
						+ "INNER JOIN DictionaryEntries as d\n" + "ON e.paymentMethod=d.id\n"
						+ "INNER JOIN FrequentTransaction as f\n" + "ON t.id=f.id\n";

			} else {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,d.id as \"paymentMethod\",COALESCE(t.comment,'no comment') \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\"\n"
						+ "FROM Transaction as t\n" + "INNER JOIN Expense as e\n" + "ON t.id=e.id\n"
						+ "INNER JOIN DictionaryEntries as d\n" + "ON e.paymentMethod=d.id\n";
			}
		}

		if (filters.getFrom() != null && filters.getTo() != null) {
			sqlStatment += " WHERE t.date>=DATE(\"" + filters.getFrom() + "\") and t.date<=DATE(\"" + filters.getTo()
					+ "\")";
		} else if (filters.getFrom() != null) {
			sqlStatment += " WHERE t.date>=DATE(\"" + filters.getFrom() + "\")";
		} else if (filters.getTo() != null) {
			sqlStatment += " WHERE t.date<=DATE(\"" + filters.getTo() + "\")";
		}

		if (filters.getCategory() != null) {
			if (filters.getFrom() != null || filters.getTo() != null) {
				sqlStatment += " AND t.category=" + filters.getCategory();
			} else {
				sqlStatment += " WHERE t.category=" + filters.getCategory();
			}
		}
		sqlStatment += " ORDER BY t.date";
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.result = this.pstmt.executeQuery();
		while (this.result.next()) {
			Category category = getCategory(this.result.getInt("category"));
			TransactionArgument transactionArgument;
			if (this.result.getBoolean("frequent")) {
				// get monthFrequent
				int monthFrequent = getMonthFrequent(this.result.getInt("id"));
				if (this.result.getInt("type") == 15) {
					transactionArgument = new TransactionArgument(this.result.getInt("id"), this.result.getInt("type"),
							this.result.getDouble("amount"), category, this.result.getString("comment"),
							this.result.getDate("date").toLocalDate(), 0, monthFrequent);
					TransactionBase frequentIncome = Factory.createTransaction("FrequentIncome", transactionArgument);
					transactions.add(frequentIncome);
				} else if (this.result.getInt("type") == 16) {
					transactionArgument = new TransactionArgument(this.result.getInt("id"), this.result.getInt("type"),
							this.result.getDouble("amount"), category, this.result.getString("comment"),
							this.result.getDate("date").toLocalDate(), this.result.getInt("paymentMethod"),
							monthFrequent);
					TransactionBase frequentExpense = Factory.createTransaction("FrequentExpense", transactionArgument);
					transactions.add(frequentExpense);
				}
			} else {
				if (this.result.getInt("type") == 15) {
					transactionArgument = new TransactionArgument(this.result.getInt("id"), this.result.getInt("type"),
							this.result.getDouble("amount"), category, this.result.getString("comment"),
							this.result.getDate("date").toLocalDate(), 0, 0);
					TransactionBase income = Factory.createTransaction("Income", transactionArgument);
					transactions.add(income);
				} else if (this.result.getInt("type") == 16) {
					transactionArgument = new TransactionArgument(this.result.getInt("id"), this.result.getInt("type"),
							this.result.getDouble("amount"), category, this.result.getString("comment"),
							this.result.getDate("date").toLocalDate(), this.result.getInt("paymentMethod"), 0);
					TransactionBase expense = Factory.createTransaction("Expense", transactionArgument);
					transactions.add(expense);
				}
			}
		}

		return transactions;
	}

	@Override
	public List<Category> getCategories(Integer type) throws SQLException {
		// type===dkey income if dkey=1 expense if dkey=2
		String sqlStatment = "select * from DictionaryEntries where dkey=?";
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.pstmt.setInt(1, type);
		this.result = this.pstmt.executeQuery();
		List<Category> category = new ArrayList<Category>();
		while (this.result.next()) {
			category.add(new Category(this.result.getInt("id"), this.result.getInt("dkey"),
					this.result.getString("value"), this.result.getString("icon")));
		}
		return category;
	}

	@Override
	public void addIncome(Income income) throws SQLException {

		String addTransactionQuery = "insert into Transaction (type, amount, category, comment, date)"
				+ "values (?, ?, ?, ?, ?)";

		pstmt = connection.prepareStatement(addTransactionQuery);
		pstmt.setInt(1, income.getType()); // 15
		pstmt.setDouble(2, income.getAmount());
		pstmt.setInt(3, income.getCategory().getId()); // category in tran table is int //id or dkey
		pstmt.setString(4, income.getComment());
		pstmt.setDate(5, Date.valueOf(income.getDate()));
		pstmt.executeUpdate();

		int transId = 0;
		String idQuery = "select Transaction.id from Transaction \r\n"
				+ "  where type=15 AND Transaction.id NOT IN (select Income.id from Income)";
		pstmt = connection.prepareStatement(idQuery);
		result = pstmt.executeQuery(idQuery);

		while (result.next()) {
			System.out.println("id= " + result.getInt(1));
			transId = result.getInt(1);
			System.out.println(transId);
		}

		String addIncomeQuery = "insert into Income (id)  \r\n" + " values( ? ) ";
		pstmt = connection.prepareStatement(addIncomeQuery);
		pstmt.setInt(1, transId);
		pstmt.executeUpdate();

		if (income instanceof FrequentIncome) {

			String addIncomeFrequentQuery = " insert into FrequentFransaction (id, monthFrequent) \r\n"
					+ "  values (?, ?)";

			pstmt = connection.prepareStatement(addIncomeFrequentQuery);
			pstmt.setInt(1, transId);
			pstmt.setInt(2, ((FrequentIncome) income).getMonthFrequent());

			pstmt.executeUpdate();
		}
	}

	@Override
	public void addExpense(Expense expense) throws SQLException {

		String addTransactionQuery = "insert into Transaction (type, amount, category, comment, date) values (?, ?, ?, ?, ?) ";

		pstmt = connection.prepareStatement(addTransactionQuery);
		pstmt.setInt(1, expense.getType());
		pstmt.setDouble(2, expense.getAmount());
		pstmt.setInt(3, expense.getCategory().getId());
		pstmt.setString(4, expense.getComment());
		pstmt.setDate(5, Date.valueOf(expense.getDate()));

		pstmt.executeUpdate();
		System.out.println("executeAdd to transaction");

		String query = "select Transaction.id \r\n" + "		from Transaction\r\n"
				+ "		where type=16 AND Transaction.id NOT IN (select Expense.id from Expense)";

		pstmt = connection.prepareStatement(query);
		ResultSet r = pstmt.executeQuery(query);
		int id = 0;
		while (r.next()) {
			id = r.getInt(1);
		}
		System.out.println("id :" + id);
		String addExpenseQuery = "insert into Expense (id,paymentMethod) values (?,?)";

		pstmt = connection.prepareStatement(addExpenseQuery);
		pstmt.setInt(1, id);
		pstmt.setDouble(2, expense.getPymentMethod());

		pstmt.executeUpdate();
		System.out.println("executeAdd to Expense");

		if (expense instanceof FrequentExpense)

		{

			String addFrequantQuery = "insert into FrequentTransaction (id, monthFrequent) values (?,?)";

			pstmt = connection.prepareStatement(addFrequantQuery);
			pstmt.setInt(1, id);
			pstmt.setInt(2, ((FrequentExpense) expense).getMonthFrequent());
			pstmt.executeUpdate();

		}

	}

	@Override
	public void addCategory(Category category) throws SQLException {
		pstmt = connection.prepareStatement("SELECT EXISTS(SELECT * from DictionaryEntries WHERE dkey=? and value=?)");
		pstmt.setInt(1, category.getDkey());
		pstmt.setString(2, category.getValue());
		result = pstmt.executeQuery();
		result.next();
		if (result.getBoolean(1) == false) {
			pstmt = connection.prepareStatement("INSERT INTO DictionaryEntries(dkey,value,icon) VALUES(?,?,?)");

			pstmt.setInt(1, category.getDkey());
			pstmt.setString(2, category.getValue());
			pstmt.setString(3, category.getIconPath());
			pstmt.executeUpdate();
		}
	}

	@Override
	public void removeCategory(Integer id) throws SQLException {

		String removeCategoryQuery = "update  DictionaryEntries set enable = '0' where id = ? ";
		pstmt = connection.prepareStatement(removeCategoryQuery);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();

	}

	@Override
	public void updateCategory(Category category) throws SQLException {

		pstmt = connection
				.prepareStatement("UPDATE  DictionaryEntries set dkey=?,value=?,icon=? where id=? and enable='1'");

		pstmt.setInt(1, category.getDkey());
		pstmt.setString(2, category.getValue());
		pstmt.setString(3, category.getIconPath());
		pstmt.setInt(4, category.getId());

		pstmt.executeUpdate();

	}

	@Override
	public void updateTranFrequant(Integer transactionId, Integer monthFrequent) throws SQLException {
		if (monthFrequent > 0) {
			try {
				String addFrequantQuery = "insert into FrequentTransaction (id, monthFrequent) values (?,?)";

				pstmt = connection.prepareStatement(addFrequantQuery);
				pstmt.setInt(1, transactionId);
				pstmt.setInt(2, monthFrequent);
				pstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// return false;
			}

		} else if (monthFrequent == 0) {
			try {
				String removeFrequantQuery = "delete from FrequentTransaction where id =?";

				pstmt = connection.prepareStatement(removeFrequantQuery);
				pstmt.setInt(1, transactionId);
				pstmt.execute();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// return false;
			}
		}
		// return true;
	}

	@Override
	public double getBalance(TransactionFilters filters) throws SQLException {

		String sqlStatment = "";
		ResultSet total = null;
		if (filters.getType() == null) {
			// get all
			if (filters.getFrequent() != null) { // all frequent transaction
				if (filters.getFrequent() == true) {
					sqlStatment = ("SELECT sum(amount) from Transaction as t  ,  FrequentTransaction as ft where t.id=ft.id");
				} else { // all non frequent transactions
					sqlStatment = ("select sum(amount) from Transaction as t  where Transaction.id not in (select FrequentTransaction.id from FrequentTransaction)");
				}
			}
		} else if (filters.getType() == 15) {
			// get Income
			if (filters.getFrequent() != null) { // (income and frequent) transactions
				if (filters.getFrequent() == true) {
					sqlStatment = ("SELECT sum(amount) from Transaction as t  ,  FrequentTransaction as ft where  t.id=ft.id and t.type=15");

				} else { // (income and non-frequent) transactions
					sqlStatment = ("select sum(amount) from Transaction as t  where type=15 and id not in (select id from FrequentTransaction)  ");
				}
			}
		} else if (filters.getType() == 16) {
			// get Expense
			if (filters.getFrequent() != null) {
				if (filters.getFrequent() == true) {
					// ( expense and frequent ) transactions
					sqlStatment = ("SELECT sum(amount) from Transaction as t  ,  FrequentTransaction as ft where t.id=ft.id and t.type=16");

				} else { // (expense and non-frequent) transactions
					sqlStatment = ("select sum(amount) from Transaction as t  where type=16 and id not in (select id from FrequentTransaction)");
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
