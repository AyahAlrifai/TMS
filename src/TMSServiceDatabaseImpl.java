import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
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
		Class.forName("com.mysql.jdbc.Driver");
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
	public Boolean addIncome(Income income) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addExpense(Expense expense) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeCategory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateTranFrequant(Integer transactionId, Integer monthFrequent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBalance(TransactionFilters filters) {
		// TODO Auto-generated method stub
		return 0;
	}

}
