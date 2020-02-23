import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
//import java.util.Date;
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
	public void addIncome(Income income) {
		// TODO Auto-generated method stub
		//return null;
	}

	//public void addExpense(Expense expense) throw .....
	@Override
	public void addExpense(Expense expense) {
		try {
			String addTransactionQuery = "insert into transaction (type, amount, category, comment, date)"
					+ "values (?, ?, ?, ?, ?)";

			pstmt = connection.prepareStatement(addTransactionQuery);
			pstmt.setInt(1, expense.getType());
			pstmt.setDouble(2, expense.getAmount());
			pstmt.setInt(3, expense.getCategory().getId());
			pstmt.setString(4, expense.getComment());
			pstmt.setDate(5, Date.valueOf(expense.getDate()));

			pstmt.executeUpdate(addTransactionQuery);

			String query ="select transaction.id \r\n"
					+ "		from transaction\r\n"
					+ "		where type=16 AND transaction.id NOT IN (select expense.id from expense)";
			
			pstmt = connection.prepareStatement(query);
			ResultSet r= pstmt.executeQuery();
			int id=0;
		      while(r.next()) 
		      {
		    	  id =r.getInt(1);
		      }
			
		     
			String addExpenseQuery = "insert into expense (id,paymentMethod) values (?,?)" ;

			pstmt = connection.prepareStatement(addExpenseQuery);
			pstmt.setInt(1, id);
			pstmt.setDouble(2, expense.getPymentMethod());
			
			pstmt.executeUpdate();
			
			if (expense instanceof FrequentExpense)
			
			{
				
				try {
					String addFrequantQuery = "insert into frequenttransaction (id, monthFrequent) values (?,?)";

					pstmt = connection.prepareStatement(addFrequantQuery);
					pstmt.setInt(1, id);
					pstmt.setInt(2, ((FrequentExpense) expense).getMonthFrequent());
					pstmt.executeUpdate();
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
					//return false;
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			//return false;
		}
		//return true;
	}

	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public void removeCategory(Integer id) {
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public void updateTranFrequant(Integer transactionId, Integer monthFrequent) {
		if (monthFrequent > 0) {
			try {
				String addFrequantQuery = "insert into frequenttransaction (id, monthFrequent) values (?,?)";

				pstmt = connection.prepareStatement(addFrequantQuery);
				pstmt.setInt(1, transactionId);
				pstmt.setInt(2, monthFrequent);
				pstmt.executeUpdate();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				//return false;
			}

		}
		else if (monthFrequent==0)
		{
			try {
				String removeFrequantQuery = "delete from frequenttransaction where id =?";

				pstmt = connection.prepareStatement(removeFrequantQuery);
				pstmt.setInt(1, transactionId);
				pstmt.execute();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				//return false;
			}
		}
		//return true;
	}

	@Override
	public double getBalance(TransactionFilters filters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Category getCategory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMonthFrequent(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
