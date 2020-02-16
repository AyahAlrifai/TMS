import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;    
import java.util.Scanner;

class Services {
	Connection con;
	String database;
	String userName;
	String password;
	PreparedStatement stmt;
	ResultSet rs;
	Scanner sc=new Scanner(System.in);
	public Services() throws ClassNotFoundException, SQLException {
		try {
			DataInputStream reader=new DataInputStream( new FileInputStream("configFile.txt"));
			this.database=reader.readLine();
			this.userName=reader.readLine();
			this.password=reader.readLine();
		}
		catch(Exception e) {
		}
	    Class.forName("com.mysql.jdbc.Driver");
	    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.database,this.userName,this.password);
	}
	public void showAll(String from,String to) throws SQLException {
		 stmt = con.prepareStatement("SELECT t.id,dd.value as \"type\" ,abs(t.amount),t.date,d.value as \"category\",COALESCE(ddd.value,'') as \"paymentMethod\",COALESCE(t.comment,'')\n" + 
				"FROM Transaction as t\n" + 
				"LEFT JOIN Expense as e\n" + 
				"      ON t.id=e.id\n" + 
				"INNER JOIN DictionaryEntries as d\n" + 
				"      ON t.category=d.id\n" + 
				"INNER JOIN DictionaryEntries as dd\n" + 
				"      ON t.type=dd.id\n" + 
				"LEFT JOIN DictionaryEntries as ddd\n" + 
				"      ON e.paymentMethod=ddd.id\n" +  
				"WHERE t.date>? and t.date<? \n"+
				"ORDER BY \n" +
				"	t.date \n");
		stmt.setDate(1,Date.valueOf(from));
		stmt.setDate(2,Date.valueOf(to));
		rs = stmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
		}
		stmt=con.prepareStatement("SELECT SUM(amount) from Transaction;");
		rs = stmt.executeQuery();
		rs.next();
		System.out.println("\ntotal balance:"+rs.getDouble(1));
	}
	public void showIncome(String from,String to) throws SQLException {
		stmt = con.prepareStatement("SELECT t.id,dd.value as \"type\" ,t.amount,t.date,d.value as \"category\",COALESCE(t.comment,'')\n" + 
				"FROM Transaction as t\n" + 
				"INNER JOIN Income as i\n" + 
				"ON t.id=i.id\n" + 
				"INNER JOIN DictionaryEntries as d\n" + 
				"ON t.category=d.id\n" + 
				"INNER JOIN DictionaryEntries as dd\n" + 
				"ON t.type=dd.id\n" + 
				"WHERE t.date>? and t.date<?\n" + 
				"ORDER BY t.date; ");
		stmt.setDate(1,Date.valueOf(from));
		stmt.setDate(2,Date.valueOf(to));
		rs = stmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6));
		}
		stmt=con.prepareStatement("SELECT SUM(amount) from Transaction where type=15;");
		rs = stmt.executeQuery();
		rs.next();
		System.out.println("\ntotal Income:"+rs.getDouble(1));

	}
	public void showExpense(String from,String to) throws SQLException {
		stmt = con.prepareStatement("SELECT t.id,dd.value as \"type\" ,abs(t.amount),t.date,d.value as \"category\",ddd.value as \"paymentMethod\",COALESCE(t.comment,'')\n" + 
				"FROM Transaction as t\n" + 
				"INNER JOIN Expense as e\n" + 
				"	ON t.id=e.id\n" + 
				"INNER JOIN DictionaryEntries as ddd\n" + 
				"	ON e.paymentMethod=ddd.id\n" + 
				"INNER JOIN DictionaryEntries as d\n" + 
				"	ON t.category=d.id\n" + 
				"INNER JOIN DictionaryEntries as dd\n" + 
				"	ON t.type=dd.id\n" + 
				"WHERE t.date>? and t.date<?\n" + 
				"ORDER BY t.date;");
		stmt.setDate(1,Date.valueOf(from));
		stmt.setDate(2,Date.valueOf(to));
		rs = stmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6));
		}
		stmt=con.prepareStatement("SELECT ABS(SUM(amount)) from Transaction where type=16;");
		rs = stmt.executeQuery();
		rs.next();
		System.out.println("\ntotal Expense:"+rs.getDouble(1));
		
	}
	public void showCategory(String categoryName) throws SQLException {
		stmt = con.prepareStatement("SELECT * FROM DictionaryEntries where value=?");
		stmt.setString(1, categoryName);
		rs = stmt.executeQuery();
		if(rs.next()) {
			if(rs.getInt(2)==1) {
				//income
				stmt = con.prepareStatement("SELECT t.id,dd.value as \"type\" ,t.amount,t.date,d.value as \"category\",COALESCE(t.comment,'')\n" + 
						"FROM Transaction as t\n" + 
						"INNER JOIN Income as i\n" + 
						"ON t.id=i.id\n" + 
						"INNER JOIN DictionaryEntries as d\n" + 
						"ON t.category=d.id\n" + 
						"INNER JOIN DictionaryEntries as dd\n" + 
						"ON t.type=dd.id\n" + 
						"WHERE t.category=?\n" + 
						"ORDER BY t.date; ");
				int dkey=rs.getInt(1);
				stmt.setInt(1,dkey);
				rs = stmt.executeQuery();
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6));
				}
				stmt=con.prepareStatement("SELECT ABS(SUM(amount)) from Transaction where category=?;");
				stmt.setInt(1,dkey);
				rs = stmt.executeQuery();
				rs.next();
				System.out.println("\ntotal "+categoryName+":"+rs.getDouble(1));
			}
			else if(rs.getInt(2)==2) {
				//expense
					stmt = con.prepareStatement("SELECT t.id,dd.value as \"type\" ,abs(t.amount),t.date,d.value as \"category\",ddd.value as \"paymentMethod\",COALESCE(t.comment,'')\n" + 
							"FROM Transaction as t\n" + 
							"INNER JOIN Expense as e\n" + 
							"	ON t.id=e.id\n" + 
							"INNER JOIN DictionaryEntries as ddd\n" + 
							"	ON e.paymentMethod=ddd.id\n" + 
							"INNER JOIN DictionaryEntries as d\n" + 
							"	ON t.category=d.id\n" + 
							"INNER JOIN DictionaryEntries as dd\n" + 
							"	ON t.type=dd.id\n" + 
							"WHERE t.category=?\n" + 
							"ORDER BY t.date;");
					int dkey=rs.getInt(1);
					stmt.setInt(1,dkey);
					rs = stmt.executeQuery();
					while(rs.next()) {
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6));
					}
					stmt=con.prepareStatement("SELECT ABS(SUM(amount)) from Transaction where category=?;");
					stmt.setInt(1,dkey);
					rs = stmt.executeQuery();
					rs.next();
					System.out.println("\ntotal "+categoryName+":"+rs.getDouble(1));
			}
		}
		else {
			System.out.println("no category in database");
		}
	}
	public void addCategory(String Value,String categoryName) throws SQLException {
		stmt=con.prepareStatement("select dkey from Dictionary where value=?");
		stmt.setString(1,categoryName);
		rs=stmt.executeQuery();
		if(rs.next()) {
		 stmt = con.prepareStatement("INSERT INTO DictionaryEntries(value,dkey) "
				+ "VALUES(?,?)");
		stmt.setString(1,Value);
		stmt.setInt(2, rs.getInt(1));
		stmt.executeUpdate();
		}
	}
	public void addIcon(String value,String address) throws SQLException {
		stmt = con.prepareStatement("select id from DictionaryEntries where value=?");
		stmt.setString(1,value);
		rs = stmt.executeQuery();
		if(rs.next()) {
			stmt = con.prepareStatement( "update  DictionaryEntries set icon =? where id=?");
			stmt.setString(1,address);
			stmt.setInt(2, rs.getInt(1));
		    stmt.executeUpdate();
		}
	}
}

class UI {
	 Services services;
	 DataInputStream reader;
	 Scanner sc=new Scanner(System.in);
	 public UI() throws FileNotFoundException, ClassNotFoundException, SQLException {
		 	services=new Services();
			this.reader=new DataInputStream( new FileInputStream("command.txt"));
		}
	 public void run() throws IOException, SQLException {
		String command;
		while((command=reader.readLine())!=null) {
		String[] splitCommand=command.split(",");
		switch(splitCommand[0].toLowerCase()) {
			case "show":
				String from=splitCommand[2],to=splitCommand[3];
				if(splitCommand[2].equals(" ")) {
					from="1970-1-1";
				}
				if(splitCommand[3].equals(" ")) {
					Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
					String[] time=date.toString().split(" ");
					to=time[0];
				}
				switch(splitCommand[1].toLowerCase()) {
				case "all":
					this.services.showAll(from,to);
					break;
				case "income":
					this.services.showIncome(from, to);
					break;
				case "expense":
					this.services.showExpense(from, to);
					break;
				case "category":
					this.services.showCategory(splitCommand[2].toLowerCase());
					break;
				}
				System.out.println("_____________________________________________________________");	
				break;
			case "add":
				switch(splitCommand[1].toLowerCase()) {
					case "category":
						 services.addCategory(splitCommand[3].toLowerCase() ,splitCommand[2].toLowerCase());
					     break;
					case "icon" :
						 services.addIcon(splitCommand[2].toLowerCase(),splitCommand[3]);
						 break;
				}
				break;
			case "remove":
				break;
			}
		}
	 }
}

public class MTS {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		UI ui=new UI();
		ui.run();
	}
}
