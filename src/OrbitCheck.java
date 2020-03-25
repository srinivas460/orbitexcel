import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import check.db.excel.ExcelRS;
import check.db.excel.ReadJson;
import utils.JsonModel;
import utils.Logs;

public class OrbitCheck {

	public static void main(String[] args) {

		Logs.debug("------------------------------------------------------------------------------------------");
		Logs.debug("		   Welcome to ORBIT SOFTWARE SOLUTIONS console application for Database	  	    	");
		Logs.debug("		   Enter below for running different methods							  	    	");
		Logs.debug("		   1 : Orbit ClientQueryModel - excel (Imp File : metadata.json & metadata_db.json ");
		Logs.debug("		   1 : Dynamic (Imp File : orbitdb.json & queries.json				  	    	");
		Logs.debug("------------------------------------------------------------------------------------------");

		Logs.debug("Enter :");
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
	
		if (i == 1) {

			List<JsonModel> dbNames = getdbName("metadata_db.json");
			Logs.debug("running method 1 now." );
			ExcelRS json = new ExcelRS();
			json.process(dbNames);

		} else if (i == 2) {

				String jsonfile = getjsonFile();
		
				List<JsonModel> dbNames = getdbName(jsonfile);
				Logs.debug("running Method 2");
				ReadJson json = new ReadJson();
				json.process(dbNames);

		}

		Logs.debug("------------------------------------------------------------------------------------------");
		Logs.debug("		  COMPLETED	  	    	");
		Logs.debug("------------------------------------------------------------------------------------------");
		exit();
	}

	private static List<JsonModel> getdbName(String jsonFile) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<JsonModel> jsonObj = new ArrayList<JsonModel>();
			try {
				jsonObj = mapper.readValue(new File(jsonFile),
						mapper.getTypeFactory().constructCollectionType(List.class, JsonModel.class));
			} catch (Exception e) {
				Logs.error("Not a Valid Json Format , Please check the json file and re-run the application");
				exit();
			}

			if (jsonObj == null || jsonObj.size() == 0) {
				Logs.error("Database details are not found in Json File , please try again");
				String jsonfile = getjsonFile();
				getdbName(jsonfile);
			}

			int count = 1;
			for (JsonModel model : jsonObj) {
				Logs.debug(count + " : " + model.getDbname());
				count++;
			}
			Logs.debug(count + " : All");

			List<JsonModel> json = new ArrayList<>();
			String[] dbnames = selectDbs();
			boolean allJson = false;
			for (String name : dbnames) {
				int position = (Integer.valueOf(name));
				if (position > 0 && position <= count) {
					if ((String.valueOf(count)).equals(name)) {
						allJson = true;
						break;
					} else
						json.add(jsonObj.get(position - 1));
				} else {
					Logs.error("Selected invalid database, please try again");
					exit();
				}
			}

			if (allJson) {
				json.addAll(jsonObj);
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String[] selectDbs() {

		Logs.debug("Select the Database from above like ex(1-for all  or 1,2,3 for multi db to run at once)");
		Scanner sc = new Scanner(System.in);
		String dbselect = sc.next();
		if (dbselect == null) {
			return selectDbs();

		}
		String regex = "[0-9, /,]+";
		if (!dbselect.matches(regex)) {
			return selectDbs();
		}

		String[] dbname = dbselect.split(",");
		if (dbname.length > 0) {

			return dbname;
		} else {
			return selectDbs();
		}
	}

	private static void exit() {
		System.exit(0);

	}

	private static String getjsonFile() {
		Logs.debug("Enter Json file name :");
		Scanner sc = new Scanner(System.in);
		String jsonfile = sc.next();
		if (!new File(jsonfile).exists()) {
			Logs.error("Sorry cant find the file with " + jsonfile);
			getjsonFile();
		}
		return jsonfile;
	}

}
