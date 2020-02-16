import java.time.LocalDate;
import java.util.List;

public interface TMSServices {
	public List<Transaction> getTransatcions(int type,int category,LocalDate from,LocalDate to );
	
}
