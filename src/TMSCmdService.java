import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import com.tms.exception.*;
import com.tms.service.TMSServiceDatabaseImpl;
import com.tms.transaction.*;

@SuppressWarnings("unchecked")
public class TMSCmdService {
	TMSServiceDatabaseImpl tmsService;
	Scanner sc = new Scanner(System.in);

	public TMSCmdService() throws ClassNotFoundException, SQLException, IOException {
		tmsService = new TMSServiceDatabaseImpl();

	}

	public void run() throws TMSException, ClassNotFoundException, SQLException, IOException {
		System.out.print("\n>>>");
		String command = sc.nextLine();
		HashMap<String, String> cmd = this.splitCmd(command);
		if (cmd.containsKey("cmd")) {
			TMSError.getError(cmd, tmsService);
			if (cmd.containsKey("service")) {
				String service = cmd.get("service");
				if (service.equals("--help")) {
					System.out.print("welcome to TMS application\n" + "each command must start with 'TMS'\n"
							+ "then service like 'getTransaction','getCategory',....\n"
							+ "[-t] type income:15 expense:16\n"
							+ "[-c] category, category type depends on transaction type\n"
							+ "[-fd] fromDate in format yyyy-mm-dd\n" + "[-td] toDate in format yy-mm-dd\n"
							+ "[-f] frequent the value must be 'true'");
				} else if (service.equals("getTransaction")) {
					try {
						TransactionFilters f = new TransactionFilters();
						if (cmd.containsKey("-t")) {
							f.setType(Integer.parseInt(cmd.get("-t")));
						}
						if (cmd.containsKey("-c")) {
							f.setCategory(Integer.parseInt(cmd.get("-c")));
						}
						if (cmd.containsKey("-fd")) {
							f.setFrom(LocalDate.parse(cmd.get("-fd")));
						}
						if (cmd.containsKey("-td")) {
							f.setTo(LocalDate.parse(cmd.get("-td")));
						}
						if (cmd.containsKey("-f")) {
							f.setFrequent(true);
						}
						List<TransactionBase> transaction = tmsService.getTransatcions(f);
						transaction.forEach((n) -> System.out.println(n
								+ "\n---------------------------------------------------------------------------------------------------------\n"));
					} catch (NumberFormatException e) {
						System.out.println("invalid integer number");
					} catch (DateTimeParseException e) {
						System.out.println("invalid format of date,enter date in this format yyyy-mm-dd");
					}
				}
			}
		}
	}

	private HashMap<String, String> splitCmd(String command) {
		String[] cmd = command.split(" ");
		HashMap<String, String> cmmd = new HashMap();
		if (cmd.length >= 1 && !cmd[0].equals(""))
			cmmd.put("cmd", cmd[0]);
		if (cmd.length >= 2)
			cmmd.put("service", cmd[1]);

		if (cmd.length % 2 == 0) {
			for (int i = 2; i < cmd.length; i += 2) {
				cmmd.put(cmd[i], cmd[i + 1]);
			}
		} else {
			for (int i = 2; i < cmd.length - 1; i += 2) {
				cmmd.put(cmd[i], cmd[i + 1]);
			}
		}
		return cmmd;
	}

}
