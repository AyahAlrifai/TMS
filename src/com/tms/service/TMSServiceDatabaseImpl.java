package com.tms.service;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tms.resource.*;
import com.tms.transaction.*;
import com.tms.exception.*;

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
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.databaseName+"?useSSL=false", this.userName,
				this.password);
	}

	@Override
	public int getMonthFrequent(int id) throws SQLException {
		String sqlStatment = "select " + R.Attribute.monthFrequent + " from " + R.Table.frequentTransaction + " where "
				+ R.Attribute.id + "=?";
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.pstmt.setInt(1, id);
		ResultSet monthFrequent = this.pstmt.executeQuery();
		if (monthFrequent.next())
			return monthFrequent.getInt(R.Attribute.monthFrequent);
		else
			return 0;
	}

	@Override
	public Category getCategory(Integer id) throws SQLException {
		String sqlStatment = "select " + R.Attribute.id + "," + R.Attribute.dkey + "," + R.Attribute.value + ","
				+ R.Attribute.icon + " from " + R.Table.dictionaryEntries + " where " + R.Attribute.id + "=? and "
				+ R.Attribute.enable + "=true;\n";
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.pstmt.setInt(1, id);
		ResultSet categortData = this.pstmt.executeQuery();
		if (categortData.next()) {
			return new Category(categortData.getInt(R.Attribute.id), categortData.getInt(R.Attribute.dkey),
					categortData.getString(R.Attribute.value), categortData.getString(R.Attribute.icon));
		} else
			return null;
	}

	@Override
	public List<TransactionBase> getTransatcions(TransactionFilters filters) throws SQLException, TMSException {
		Integer type = filters.getType();
		Integer category = filters.getCategory();
		LocalDate from = filters.getFrom();
		LocalDate to = filters.getTo();
		Boolean frequent = filters.getFrequent();

		String sqlStatment = "";
		if (type == null) {
			if (frequent != null) {
				sqlStatment = "SELECT t." + R.Attribute.id + ",t." + R.Attribute.type + ",t." + R.Attribute.amount
						+ ",t." + R.Attribute.date + ",t." + R.Attribute.category + ",COALESCE(d." + R.Attribute.id
						+ ",0) as \"paymentMethod\",COALESCE(t." + R.Attribute.comment
						+ ",'no comment') as \"comment\",t." + R.Attribute.id + " in(select " + R.Attribute.id
						+ " from " + R.Table.frequentTransaction + ") as \"frequent\" \n" + "FROM "
						+ R.Table.transaction + " as t\n" + "LEFT JOIN " + R.Table.expense + " as e\n" + "ON t."
						+ R.Attribute.id + "=e." + R.Attribute.id + "\n" + "LEFT JOIN " + R.Table.income + " as i\n"
						+ "ON t." + R.Attribute.id + "=i." + R.Attribute.id + "\n" + "LEFT JOIN "
						+ R.Table.dictionaryEntries + " as d\n" + "ON e." + R.Attribute.paymentMethod + "=d."
						+ R.Attribute.id + "\n" + "INNER JOIN " + R.Table.frequentTransaction + " as f \n" + "ON t."
						+ R.Attribute.id + "=f." + R.Attribute.id + "\n";
			} else {
				sqlStatment = "SELECT t." + R.Attribute.id + ",t." + R.Attribute.type + ",t.amount,t."
						+ R.Attribute.date + ",t.category,COALESCE(d." + R.Attribute.id
						+ ",0) as \"paymentMethod\",COALESCE(t." + R.Attribute.comment
						+ ",'no comment') as \"comment\",t." + R.Attribute.id + " in(select " + R.Attribute.id
						+ " from " + R.Table.frequentTransaction + ") as \"frequent\" \n" + "FROM "
						+ R.Table.transaction + " as t\n" + "LEFT JOIN " + R.Table.expense + " as e\n" + "ON t."
						+ R.Attribute.id + "=e." + R.Attribute.id + "\n" + "LEFT JOIN " + R.Table.income + " as i\n"
						+ "ON t." + R.Attribute.id + "=i." + R.Attribute.id + "\n" + "LEFT JOIN "
						+ R.Table.dictionaryEntries + " as d\n" + "ON e." + R.Attribute.paymentMethod + "=d."
						+ R.Attribute.id + "\n";
			}
		} else if (type == 15) {
			// get Income
			if (frequent != null) {
				sqlStatment = "SELECT t." + R.Attribute.id + ",t." + R.Attribute.type + ",t." + R.Attribute.amount
						+ ",t." + R.Attribute.date + ",t." + R.Attribute.category + ",COALESCE(t." + R.Attribute.comment
						+ ",'no comment') as \"comment\",f." + R.Attribute.monthFrequent + " as \"Month Frequenr\" \n"
						+ "FROM " + R.Table.transaction + " as t\n" + "INNER JOIN " + R.Table.income + " as i\n"
						+ "ON t." + R.Attribute.id + "=i." + R.Attribute.id + "\n" + "INNER JOIN "
						+ R.Table.frequentTransaction + " as f\n" + "ON t." + R.Attribute.id + "=f." + R.Attribute.id
						+ "\n";

			} else {
				sqlStatment = "SELECT t." + R.Attribute.id + ",t." + R.Attribute.type + ",t." + R.Attribute.amount
						+ ",t." + R.Attribute.date + ",t." + R.Attribute.category + ",COALESCE(t." + R.Attribute.comment
						+ ",'no comment') as \"comment\",t." + R.Attribute.id + " in(select " + R.Attribute.id
						+ " from " + R.Table.frequentTransaction + ") as \"frequent\"\n" + "FROM " + R.Table.transaction
						+ " as t\n" + "INNER JOIN " + R.Table.income + " as i\n" + "ON t." + R.Attribute.id + "=i."
						+ R.Attribute.id + "\n";
			}

		} else if (type == 16) {
			// get Expense
			if (frequent != null) {
				sqlStatment = "SELECT t." + R.Attribute.id + ",t." + R.Attribute.type + ",t." + R.Attribute.amount
						+ ",t." + R.Attribute.date + ",t." + R.Attribute.category + ",d." + R.Attribute.id
						+ " as \"paymentMethod\",COALESCE(t." + R.Attribute.comment + ",'no comment') \"comment\",t."
						+ R.Attribute.id + " in(select " + R.Attribute.id + " from " + R.Table.frequentTransaction
						+ ") as \"frequent\" \n" + "FROM " + R.Table.transaction + " as t\n" + "INNER JOIN "
						+ R.Table.expense + " as e\n" + "ON t." + R.Attribute.id + "=e." + R.Attribute.id + "\n"
						+ "INNER JOIN " + R.Table.dictionaryEntries + " as d\n" + "ON e." + R.Attribute.paymentMethod
						+ "=d." + R.Attribute.id + "\n" + "INNER JOIN " + R.Table.frequentTransaction + " as f\n"
						+ "ON t." + R.Attribute.id + "=f." + R.Attribute.id + "\n";

			} else {
				sqlStatment = "SELECT t." + R.Attribute.id + ",t." + R.Attribute.type + ",t." + R.Attribute.amount
						+ ",t." + R.Attribute.date + ",t." + R.Attribute.category + ",d." + R.Attribute.id
						+ " as \"paymentMethod\",COALESCE(t." + R.Attribute.comment + ",'no comment') \"comment\",t."
						+ R.Attribute.id + " in(select " + R.Attribute.id + " from " + R.Table.frequentTransaction
						+ ") as \"frequent\"\n" + "FROM " + R.Table.transaction + " as t\n" + "INNER JOIN "
						+ R.Table.expense + " as e\n" + "ON t." + R.Attribute.id + "=e." + R.Attribute.id + "\n"
						+ "INNER JOIN " + R.Table.dictionaryEntries + " as d\n" + "ON e." + R.Attribute.paymentMethod
						+ "=d." + R.Attribute.id + "\n";
			}
		} else {
			throw new InvalidValueOfType("");
		}

		if (from != null && to != null) {
			if (from.isAfter(to)) {
				throw new InvalidDate("FromDate must be befor ToDate");
			}
			sqlStatment += " WHERE t." + R.Attribute.date + ">=DATE(\"" + from + "\") and t." + R.Attribute.date
					+ "<=DATE(\"" + to + "\")";
		} else if (filters.getFrom() != null) {
			if (from.isAfter(LocalDate.now())) {
				throw new InvalidDate("can not insert date in future\n");
			}
			sqlStatment += " WHERE t." + R.Attribute.date + ">=DATE(\"" + from + "\")";
		} else if (filters.getTo() != null) {
			if (to.isAfter(LocalDate.now())) {
				throw new InvalidDate("can not insert date in future\n");
			}
			sqlStatment += " WHERE t." + R.Attribute.date + "<=DATE(\"" + to + "\")";
		}

		if (category != null) {
			Category categoryv = getCategory(category);
			if (categoryv == null) {
				throw new InvalidValueOfCategory("");
			}
			if ((categoryv.getDkey() == 1 && type != null && type != 15)
					|| (categoryv.getDkey() == 2 && type != null && type != 16)) {
				throw new InvalidCategoryForType("");

			}
			if (from != null || to != null) {
				sqlStatment += " AND t." + R.Attribute.category + "=" + category;
			} else {
				sqlStatment += " WHERE t." + R.Attribute.category + "=" + category;
			}
		}
		sqlStatment += " ORDER BY t." + R.Attribute.date;
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.result = this.pstmt.executeQuery();
		while (this.result.next()) {
			Category categoryd = getCategory(this.result.getInt(R.Attribute.category));
			TransactionArgument transactionArgument;
			if (this.result.getBoolean("frequent")) {
				// get monthFrequent
				int monthFrequent = getMonthFrequent(this.result.getInt(R.Attribute.id));
				if (this.result.getInt(R.Attribute.type) == 15) {
					transactionArgument = new TransactionArgument(this.result.getInt(R.Attribute.id),
							this.result.getInt(R.Attribute.type), this.result.getDouble(R.Attribute.amount), categoryd,
							this.result.getString(R.Attribute.comment),
							this.result.getDate(R.Attribute.date).toLocalDate(), 0, monthFrequent);
					TransactionBase frequentIncome = Factory.createTransaction("FrequentIncome", transactionArgument);
					transactions.add(frequentIncome);
				} else if (this.result.getInt(R.Attribute.type) == 16) {
					transactionArgument = new TransactionArgument(this.result.getInt(R.Attribute.id),
							this.result.getInt(R.Attribute.type), this.result.getDouble(R.Attribute.amount), categoryd,
							this.result.getString(R.Attribute.comment),
							this.result.getDate(R.Attribute.date).toLocalDate(),
							this.result.getInt(R.Attribute.paymentMethod), monthFrequent);
					TransactionBase frequentExpense = Factory.createTransaction("FrequentExpense", transactionArgument);
					transactions.add(frequentExpense);
				}
			} else {
				if (this.result.getInt(R.Attribute.type) == 15) {
					transactionArgument = new TransactionArgument(this.result.getInt(R.Attribute.id),
							this.result.getInt(R.Attribute.type), this.result.getDouble(R.Attribute.amount), categoryd,
							this.result.getString(R.Attribute.comment),
							this.result.getDate(R.Attribute.date).toLocalDate(), 0, 0);
					TransactionBase income = Factory.createTransaction("Income", transactionArgument);
					transactions.add(income);
				} else if (this.result.getInt(R.Attribute.type) == 16) {
					transactionArgument = new TransactionArgument(this.result.getInt(R.Attribute.id),
							this.result.getInt(R.Attribute.type), this.result.getDouble(R.Attribute.amount), categoryd,
							this.result.getString(R.Attribute.comment),
							this.result.getDate(R.Attribute.date).toLocalDate(),
							this.result.getInt(R.Attribute.paymentMethod), 0);
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
		String sqlStatment = "select * from " + R.Table.dictionaryEntries + " where " + R.Attribute.dkey + "=?";
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.pstmt.setInt(1, type);
		this.result = this.pstmt.executeQuery();
		List<Category> category = new ArrayList<Category>();
		while (this.result.next()) {
			category.add(new Category(this.result.getInt(R.Attribute.id), this.result.getInt(R.Attribute.dkey),
					this.result.getString(R.Attribute.value), this.result.getString(R.Attribute.icon)));
		}
		return category;
	}

	@Override
	public void addIncome(Income income) throws SQLException {

		String addTransactionQuery = "insert into " + R.Table.transaction + " (" + R.Attribute.type + ", amount, "
				+ R.Attribute.category + ", " + R.Attribute.comment + ", " + R.Attribute.date + ")"
				+ "values (?, ?, ?, ?, ?)";

		pstmt = connection.prepareStatement(addTransactionQuery);
		pstmt.setInt(1, income.getType()); // 15
		pstmt.setDouble(2, income.getAmount());
		pstmt.setInt(3, income.getCategory().getId()); // category in tran table is int //id or dkey
		pstmt.setString(4, income.getComment());
		pstmt.setDate(5, Date.valueOf(income.getDate()));
		pstmt.executeUpdate();

		int transId = 0;
		String idQuery = "select " + R.Table.transaction + "." + R.Attribute.id + " from " + R.Table.transaction
				+ " \r\n" + "  where " + R.Attribute.type + "=15 AND " + R.Table.transaction + "." + R.Attribute.id
				+ " NOT IN (select " + R.Table.income + "." + R.Attribute.id + " from " + R.Table.income + ")";
		pstmt = connection.prepareStatement(idQuery);
		result = pstmt.executeQuery(idQuery);

		while (result.next()) {
			System.out.println(R.Attribute.id + "= " + result.getInt(1));
			transId = result.getInt(1);
			System.out.println(transId);
		}

		String addIncomeQuery = "insert into " + R.Table.income + " (" + R.Attribute.id + ")  \r\n" + " values( ? ) ";
		pstmt = connection.prepareStatement(addIncomeQuery);
		pstmt.setInt(1, transId);
		pstmt.executeUpdate();

		if (income instanceof FrequentIncome) {

			String addIncomeFrequentQuery = " insert into FrequentFransaction (" + R.Attribute.id + ", "
					+ R.Attribute.monthFrequent + ") \r\n" + "  values (?, ?)";

			pstmt = connection.prepareStatement(addIncomeFrequentQuery);
			pstmt.setInt(1, transId);
			pstmt.setInt(2, ((FrequentIncome) income).getMonthFrequent());

			pstmt.executeUpdate();
		}
	}

	@Override
	public void addExpense(Expense expense) throws SQLException {

		String addTransactionQuery = "insert into " + R.Table.transaction + " (" + R.Attribute.type + ", amount, "
				+ R.Attribute.category + ", " + R.Attribute.comment + ", " + R.Attribute.date
				+ ") values (?, ?, ?, ?, ?) ";

		pstmt = connection.prepareStatement(addTransactionQuery);
		pstmt.setInt(1, expense.getType());
		pstmt.setDouble(2, expense.getAmount());
		pstmt.setInt(3, expense.getCategory().getId());
		pstmt.setString(4, expense.getComment());
		pstmt.setDate(5, Date.valueOf(expense.getDate()));

		pstmt.executeUpdate();
		System.out.println("executeAdd to transaction");

		String query = "select " + R.Table.transaction + "." + R.Attribute.id + " \r\n" + "		from "
				+ R.Table.transaction + "\r\n" + "		where " + R.Attribute.type + "=16 AND " + R.Table.transaction
				+ "." + R.Attribute.id + " NOT IN (select " + R.Table.expense + "." + R.Attribute.id + " from "
				+ R.Table.expense + ")";

		pstmt = connection.prepareStatement(query);
		ResultSet r = pstmt.executeQuery(query);
		int id = 0;
		while (r.next()) {
			id = r.getInt(1);
		}
		System.out.println("id :" + id);
		String addExpenseQuery = "insert into " + R.Table.expense + " (" + R.Attribute.id + ","
				+ R.Attribute.paymentMethod + ") values (?,?)";

		pstmt = connection.prepareStatement(addExpenseQuery);
		pstmt.setInt(1, id);
		pstmt.setDouble(2, expense.getPymentMethod());

		pstmt.executeUpdate();
		System.out.println("executeAdd to " + R.Table.expense + "");

		if (expense instanceof FrequentExpense)

		{

			String addFrequantQuery = "insert into " + R.Table.frequentTransaction + " (" + R.Attribute.id + ", "
					+ R.Attribute.monthFrequent + ") values (?,?)";

			pstmt = connection.prepareStatement(addFrequantQuery);
			pstmt.setInt(1, id);
			pstmt.setInt(2, ((FrequentExpense) expense).getMonthFrequent());
			pstmt.executeUpdate();

		}

	}

	@Override
	public void addCategory(Category category) throws SQLException {
		pstmt = connection.prepareStatement("SELECT EXISTS(SELECT * from " + R.Table.dictionaryEntries + " WHERE "
				+ R.Attribute.dkey + "=? and " + R.Attribute.value + "=?)");
		pstmt.setInt(1, category.getDkey());
		pstmt.setString(2, category.getValue());
		result = pstmt.executeQuery();
		result.next();
		if (result.getBoolean(1) == false) {
			pstmt = connection.prepareStatement("INSERT INTO " + R.Table.dictionaryEntries + "(" + R.Attribute.dkey
					+ "," + R.Attribute.value + "," + R.Attribute.icon + ") VALUES(?,?,?)");

			pstmt.setInt(1, category.getDkey());
			pstmt.setString(2, category.getValue());
			pstmt.setString(3, category.getIconPath());
			pstmt.executeUpdate();
		}
	}

	@Override
	public void removeCategory(Integer id) throws SQLException {

		String removeCategoryQuery = "update  " + R.Table.dictionaryEntries + " set " + R.Attribute.enable
				+ " = '0' where " + R.Attribute.id + " = ? ";
		pstmt = connection.prepareStatement(removeCategoryQuery);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();

	}

	@Override
	public void updateCategory(Category category) throws SQLException {

		pstmt = connection.prepareStatement(
				"UPDATE  " + R.Table.dictionaryEntries + " set " + R.Attribute.dkey + "=?," + R.Attribute.value + "=?,"
						+ R.Attribute.icon + "=? where " + R.Attribute.id + "=? and " + R.Attribute.enable + "='1'");

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
				String addFrequantQuery = "insert into " + R.Table.frequentTransaction + " (" + R.Attribute.id + ", "
						+ R.Attribute.monthFrequent + ") values (?,?)";

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
				String removeFrequantQuery = "delete from " + R.Table.frequentTransaction + " where " + R.Attribute.id
						+ " =?";

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
					sqlStatment = ("SELECT sum(" + R.Attribute.amount + ") from " + R.Table.transaction + " as t  ,  "
							+ R.Table.frequentTransaction + " as ft where t." + R.Attribute.id + "=ft." + R.Attribute.id
							+ "");
				} else { // all non frequent transactions
					sqlStatment = ("select sum(" + R.Attribute.amount + ") from " + R.Table.transaction
							+ " as t  where " + R.Table.transaction + "." + R.Attribute.id + " not in (select "
							+ R.Table.frequentTransaction + "." + R.Attribute.id + " from "
							+ R.Table.frequentTransaction + ")");
				}
			}
		} else if (filters.getType() == 15) {
			// get Income
			if (filters.getFrequent() != null) { // (income and frequent) transactions
				if (filters.getFrequent() == true) {
					sqlStatment = ("SELECT sum(" + R.Attribute.amount + ") from " + R.Table.transaction + " as t  ,  "
							+ R.Table.frequentTransaction + " as ft where  t." + R.Attribute.id + "=ft."
							+ R.Attribute.id + " and t." + R.Attribute.type + "=15");

				} else { // (income and non-frequent) transactions
					sqlStatment = ("select sum(" + R.Attribute.amount + ") from " + R.Table.transaction
							+ " as t  where " + R.Attribute.type + "=15 and " + R.Attribute.id + " not in (select "
							+ R.Attribute.id + " from " + R.Table.frequentTransaction + ")  ");
				}
			}
		} else if (filters.getType() == 16) {
			// get Expense
			if (filters.getFrequent() != null) {
				if (filters.getFrequent() == true) {
					// ( expense and frequent ) transactions
					sqlStatment = ("SELECT sum(" + R.Attribute.amount + ") from " + R.Table.transaction + " as t  ,  "
							+ R.Table.frequentTransaction + " as ft where t." + R.Attribute.id + "=ft." + R.Attribute.id
							+ " and t." + R.Attribute.type + "=16");

				} else { // (expense and non-frequent) transactions
					sqlStatment = ("select sum(" + R.Attribute.amount + ") from " + R.Table.transaction
							+ " as t  where " + R.Attribute.type + "=16 and " + R.Attribute.id + " not in (select "
							+ R.Attribute.id + " from " + R.Table.frequentTransaction + ")");
				}
			}
		}
		if (filters.getFrom() != null && filters.getTo() != null) {
			sqlStatment += " and t." + R.Attribute.date + ">=DATE(\"" + filters.getFrom() + "\") and t."
					+ R.Attribute.date + "<=DATE(\"" + filters.getTo() + "\")";
		} else if (filters.getFrom() != null) {
			sqlStatment += " and t." + R.Attribute.date + ">=DATE(\"" + filters.getFrom() + "\")";
		} else if (filters.getTo() != null) {
			sqlStatment += " and t." + R.Attribute.date + "<=DATE(\"" + filters.getTo() + "\")";
		}

		if (filters.getCategory() != null) {
			if (filters.getFrom() != null || filters.getTo() != null) {
				sqlStatment += " and t." + R.Attribute.category + "=" + filters.getCategory();
			} else {
				sqlStatment += " and t." + R.Attribute.category + "=" + filters.getCategory();
			}
		}

		this.pstmt = this.connection.prepareStatement(sqlStatment);
		total = this.pstmt.executeQuery();
		if (total.next())
			return total.getDouble(1);
		return 0;

	}

}
