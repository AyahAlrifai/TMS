import java.time.LocalDate;

public interface Transaction {

	public int getId();

	public void setId(int id);

	public int getType();

	public void setType(int type);

	public double getAmount();

	public void setAmount(double amount);

	public Category getCategory();

	public void setCategory(Category category);

	public String getComment();

	public void setComment(String comment);

	public LocalDate getDate();

	public void setDate(LocalDate date);
}
