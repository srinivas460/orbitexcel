package check.db.excel;

import static utils.JDBCUtil.close;
import static utils.JDBCUtil.getDriverConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import com.grapecity.documents.excel.CellInfo;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.Workbook;

import utils.JsonModel;
import utils.Logs;
import utils.QueryModel;
import utils.Utils;

public class ReadJson {
	public void process(List<JsonModel> dbNames) {
		long startTime = System.currentTimeMillis();

		for (JsonModel db : dbNames) {
			runLogic(db);
		}

		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		Logs.debug(Utils.millisToShortDHMS(duration));

	}

	private void runLogic(JsonModel db) {
		try {
			Logs.debug("Running Database : " + db.getDbname());

			Connection conn = getDriverConnection(db.getDriver(), db.getUrl(), db.getUsername(), db.getPassword());

			Statement stmt = null;
			ResultSet rs = null;

			try {
				for (QueryModel query : db.getQueries()) {
					stmt = conn.createStatement();

					Logs.debug("\n\nQuery :"+query.getQuery()+"\n\n");

					boolean hasResults = stmt.execute(query.getQuery());
					if (hasResults) {
						rs = stmt.getResultSet();

						if ( "query".equals(query.getType())) {
							WriteExcel(rs,query.getExcel());
						}

					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				Logs.debug("Connection closed ");
				close(rs, stmt);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void WriteExcel(ResultSet resultSet, String filename) throws SQLException {
		String path = "new_excel_template.xlsx";

		Logs.debug("Started Excel writing :");

		Workbook workbook = new Workbook();
		workbook.open(path);
		workbook.getWorksheets().get("ReportDetails").delete();
		workbook.getWorksheets().get("ReportOutput").delete();

		IWorksheet worksheet = workbook.getWorksheets().get("RawData");

		ResultSetMetaData rsmd = resultSet.getMetaData();
		int noOfColumns = rsmd.getColumnCount();

		for (int i = 0; i < noOfColumns; i++) {
			String cellInfo = CellInfo.CellIndexToName(0, i);
			String colName = rsmd.getColumnName(i + 1);
			worksheet.getRange(cellInfo).setRowHeight(30);
			worksheet.getRange(cellInfo).setValue(colName);
		}

		Logs.debug("Column headers done :"+noOfColumns);
		int row = 1;
		while (resultSet.next()) {
			for (int i = 0; i < noOfColumns; i++) {
				String cellInfo = CellInfo.CellIndexToName(row, i);
				try {

					worksheet.getRange(cellInfo).setValue(resultSet.getObject(i + 1));
				} catch (Exception e) {
					Logs.debug("EATEN : ERROR at " + row +"  :  "+cellInfo + " :>>>" + e.getMessage());
//					if(row>1) {
//						return;
//					}
				}
			}

			row++;

		}

		Logs.debug("writing row cell data : done");
		String cellInfo = CellInfo.CellIndexToName(0, noOfColumns);
		worksheet.getRange(cellInfo).autoFit();
		Logs.debug("Workbook saving...");
		Random rand = new Random();
		// Generate random integers in range 0 to 999
		int rand_int = rand.nextInt(1000);
		if(filename==null) {
			filename = "file";
		}
		workbook.save("outputs/" +  filename+"_"+rand_int + ".xlsx");
	}
}
