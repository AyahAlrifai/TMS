import java.io.IOException;
import java.sql.SQLException;
import com.tms.exception.*;

public class TMS {

	public static void main(String[] args) {
		try {
			TMSCmdService s = new TMSCmdService();
			while (true) {
				try {
					s.run();
				} catch (TMSException e) {
					System.out.print(TMSError.getMessage(e));
					System.out.print(e.getMessage());
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println(
					"please make sure you import external JAR 'mysql-connector-java-5.1.40-bin'\nyou can find it in ../mysql-connector-java-5.1.40-bin.jar");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1045) {
				System.out.println(
						"Error:1045\ninvalid password or userName\nmake sure you insert valid password and userName in configFile.txt where second line to userName and third line to password \nyou can find it in ../configFile.txt");
			}
			if (e.getErrorCode() == 1049) {
				System.out.println(
						"Error:1049\ninvalid database name\nmake sure you insert valid database name 'TMS' in first line of configFile.txt \nyou can find it in ../configFile.txt\nif you not have database yet,you can find database files in ../database/");
			} else {
				e.fillInStackTrace();
			}
		} catch (IOException e) {
			System.out.println(
					"make sure you have configFile.txt in same directory of src\nthis file must have 3 info\nfirst line:name of database 'TMS'\nsecond line:user name of mysql\nthird line:password");
		}
	}
}