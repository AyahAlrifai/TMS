import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//type of list TransactionBase in getTransatcions method
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
	public int getMonthFrequent(int id) {
		try {
			String sqlStatment="select monthFrequent from FrequentTransaction where id=?";
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, id);
			ResultSet monthFrequent=this.pstmt.executeQuery();
			monthFrequent.next(); 
			return monthFrequent.getInt("monthFrequent");
		}
		catch (Exception e) {
			
		}
		return 0;
	}
	
	@Override
	public Category getCategory(Integer id) {
		try {
			String sqlStatment="select dkey,value,icon from DictionaryEntries where id=? and enable=true;\n";
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, id);
			ResultSet categortData=this.pstmt.executeQuery();
			categortData.next();
			return new Category(this.result.getInt("category"),categortData.getInt("dkey"),categortData.getString("value"),categortData.getString("icon"));
		}
		catch (Exception e) {
			
		}
		return null;
	}
	
	@Override
	public List<TransactionBase> getTransatcions(TransactionFilters filters) {
		/* type;category;from;to;frequent; */
		String sqlStatment = "";
		if (filters.getType() == null) {
			// get all
			if (filters.getFrequent() != null) {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(d.id,0) as \"paymentMethod\",COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
						+ "FROM Transaction as t\n" 
						+ "LEFT JOIN Expense as e\n" 
						+ "ON t.id=e.id\n"
						+ "LEFT JOIN Income as i\n" 
						+ "ON t.id=i.id\n" 
						+ "LEFT JOIN DictionaryEntries as d\n" 
						+ "ON e.paymentMethod=d.id\n"
						+ "INNER JOIN FrequentTransaction as f \n"
						+ "ON t.id=f.id\n";
			} else {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(d.id,0) as \"paymentMethod\",COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
						+ "FROM Transaction as t\n" 
						+ "LEFT JOIN Expense as e\n" 
						+ "ON t.id=e.id\n"
						+ "LEFT JOIN Income as i\n" 
						+ "ON t.id=i.id\n" 
						+ "LEFT JOIN DictionaryEntries as d\n" 
						+ "ON e.paymentMethod=d.id\n";
			}
		} else if (filters.getType() == 15) {
			// get Income
			if (filters.getFrequent() != null) {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(t.comment,'no comment') as \"comment\",f.monthFrequent as \"Month Frequenr\" \n"
						+ "FROM Transaction as t\n" 
						+ "INNER JOIN Income as i\n" 
						+ "ON t.id=i.id\n"
						+ "INNER JOIN FrequentTransaction as f\n"
						+ "ON t.id=f.id\n";

			} else {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\"\n"
						+ "FROM Transaction as t\n" 
						+ "INNER JOIN Income as i\n" 
						+ "ON t.id=i.id\n";
			}

		} else if (filters.getType() == 16) {
			// get Expense
			if (filters.getFrequent() != null) {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,d.id as \"paymentMethod\",COALESCE(t.comment,'no comment') \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
						+ "FROM Transaction as t\n" 
						+ "INNER JOIN Expense as e\n" 
						+ "ON t.id=e.id\n"
						+ "INNER JOIN DictionaryEntries as d\n" 
						+ "ON e.paymentMethod=d.id\n"
						+ "INNER JOIN FrequentTransaction as f\n"
						+ "ON t.id=f.id\n";

			} else {
				sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,d.id as \"paymentMethod\",COALESCE(t.comment,'no comment') \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\"\n"
						+ "FROM Transaction as t\n" 
						+ "INNER JOIN Expense as e\n" 
						+ "ON t.id=e.id\n"
						+ "INNER JOIN DictionaryEntries as d\n" 
						+ "ON e.paymentMethod=d.id\n";
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
				sqlStatment += " AND t.categort=" + filters.getCategory();
			} else {
				sqlStatment += " WHERE t.category=" + filters.getCategory();
			}
		}
		sqlStatment += " ORDER BY t.date";
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		try {
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.result = this.pstmt.executeQuery();
			while (this.result.next()) {
				Category category=getCategory(this.result.getInt("category"));
				TransactionArgument transactionArgument;
				if (this.result.getBoolean("frequent")) {
					//get monthFrequent
					int monthFrequent=getMonthFrequent(this.result.getInt("id"));
					if (this.result.getInt("type")==15) {
						transactionArgument=new TransactionArgument(this.result.getInt("id"),this.result.getInt("type"),this.result.getDouble("amount"),category,this.result.getString("comment"),this.result.getDate("date").toLocalDate(),0,monthFrequent);
						TransactionBase frequentIncome=Factory.createTransaction("FrequentIncome",transactionArgument);
						transactions.add(frequentIncome);
					} else if (this.result.getInt("type")==16) {
						transactionArgument=new TransactionArgument(this.result.getInt("id"),this.result.getInt("type"),this.result.getDouble("amount"),category,this.result.getString("comment"),this.result.getDate("date").toLocalDate(),this.result.getInt("paymentMethod"),monthFrequent);
						TransactionBase frequentExpense=Factory.createTransaction("FrequentExpense", transactionArgument);
						transactions.add(frequentExpense);
					}
				}
				else {
					if (this.result.getInt("type")==15) {
						transactionArgument=new TransactionArgument(this.result.getInt("id"),this.result.getInt("type"),this.result.getDouble("amount"),category,this.result.getString("comment"),this.result.getDate("date").toLocalDate(),0,0);
						TransactionBase income=Factory.createTransaction("Income", transactionArgument);
						transactions.add(income);
					} else if (this.result.getInt("type")==16) {
						transactionArgument=new TransactionArgument(this.result.getInt("id"),this.result.getInt("type"),this.result.getDouble("amount"),category,this.result.getString("comment"),this.result.getDate("date").toLocalDate(),this.result.getInt("paymentMethod"),0);
						TransactionBase expense=Factory.createTransaction("Expense", transactionArgument);
						transactions.add(expense);
					}
				}
			}
		} 
		catch (Exception e) {

		}
		return transactions;
	}

	@Override
	public List<Category> getCategories(Integer type) {
		//type===dkey income if dkey=1 expense if dkey=2
		String sqlStatment="select * from DictionaryEntries where dkey=?";
		try {
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, type);
			this.result = this.pstmt.executeQuery();
			List<Category> category=new ArrayList<Category>();
			while(this.result.next()) {
				category.add(new Category(this.result.getInt("id"),this.result.getInt("dkey"),this.result.getString("value"),this.result.getString("icon")));
			}
			return category;
		} catch (SQLException e) {

		}
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
