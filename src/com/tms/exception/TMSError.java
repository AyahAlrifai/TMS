package com.tms.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.tms.exception.*;
import com.tms.service.TMSServiceDatabaseImpl;

public class TMSError {

	public static void getError(HashMap<String, String> cmd, TMSServiceDatabaseImpl ser)
			throws ClassNotFoundException, SQLException, IOException, ServiceNotFound, CommandNotFound, InvalidDate {
		if (cmd.containsKey("cmd") && !cmd.get("cmd").equals("TMS")) {
			throw new CommandNotFound("make sure each command must start with 'TMS'\n");
		}
		if (cmd.containsKey("cmd") && cmd.get("cmd").equals("TMS") && cmd.size() == 1) {
			throw new CommandNotFound("enter service after 'TMS' command\n");
		}
		for (Entry<String, String> entry : cmd.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.equals("service")) {
				if (value.equals("--help") && cmd.size() > 2) {
					throw new CommandNotFound("enter TMS --help to help you");
				}
				if (value.equals("getTransaction")) {

				} else if (value.equals("--help")) {
				}
				else if (key.equals("-td")) {
					if (!Pattern.matches("[0-9][0-9][0-9][0-9]-[0-9]{2}-[0-9]{2}", value)) {
						throw new InvalidDate("enetr date in this format yyyy-mm-dd\n");
					}
				} 
				else if (key.equals("-fd")) {
					if (!Pattern.matches("[0-9][0-9][0-9][0-9]-[0-9]{2}-[0-9]{2}",value)) {
						throw new InvalidDate("enetr date in this format yyyy-mm-dd\n");
					}
				}
				else if (key.equals("-f")) {
					if (!value.equals("true")) {
						throw new InvalidFrequentValue("");
					}
				else
					throw new ServiceNotFound(
							"make sure you enter valid service such as getTransaction,getCategory,.....\nenter TMS --help to help you");
			}
		}

	}

	public static String getMessage(TMSException e) {
		switch (e.getErrorCode()) {
		case "TMS:1":
			return "Command not found\n";
		case "TMS:2":
			return "invalid value of type\nenetr 15 for income and 16 for expense\n";
		case "TMS:3":
			return "invalid value of category \nenetr 'TMS getCategory' to know available categories for each type\n";
		case "TMS:4":
			return " invalid value of category for this type\nenetr 'TMS getCategory -t 1' to know the categories for income\nenetr 'TMS getCategory -t 2' to know the categories for expense\n";
		case "TMS:5":
			return "invalid date\n";
		case "TMS:6":
			return "the value of frequent must be 'true'\n";
		case "TMS:7":
			return "service not found\n";
		}
		return "";
	}

}
