package check.db.excel;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


import static utils.JDBCUtil.close;
import static utils.JDBCUtil.getDriverConnection;
import static models.TemplateVariableType.*;


import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grapecity.documents.excel.CellInfo;
import com.grapecity.documents.excel.INames;
import com.grapecity.documents.excel.IStyle;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.SaveFileFormat;
import com.grapecity.documents.excel.Workbook;

import models.ClientQueryModel;
import models.ClientSelection;
import models.DataType;
import models.Log;
import orbit.excel.UtilsExcel;
import utils.JsonModel;
import utils.Logs;
import utils.QueryModel;
import utils.Utils;

public class ExcelRS {

//	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/orbitdb";
//	private static final String JDBC_USER = "root";
//	private static final String JDBC_PASS = "root";

	private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	private static final String JDBC_URL = "jdbc:oracle:thin:@iorb5.orbit8.com:1521:EBSDB";
	private static final String JDBC_USER = "apps";
	private static final String JDBC_PASS = "apps";
	
	public void process(List<JsonModel> dbNames) {
		long startTime = System.currentTimeMillis();

		for (JsonModel db : dbNames) {
			runLogic(db);
		}

		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		Logs.debug(Utils.millisToShortDHMS(duration));

	}

	public static void runLogic(JsonModel db) {
		long startTime = System.currentTimeMillis();
		ObjectMapper mapper = new ObjectMapper();
		try {
			ClientQueryModel clientObj = mapper.readValue(new File("metadata.json"), ClientQueryModel.class);
			Log.debug("Report name " + clientObj.getReportName());


			Connection conn = getDriverConnection(db.getDriver(), db.getUrl(), db.getUsername(), db.getPassword());

			Statement stmt = null;
			ResultSet rs = null;

			for(QueryModel query : db.getQueries()) {
			try {
				stmt = conn.createStatement();
				boolean hasResults = stmt.execute(query.getQuery());
				if (hasResults) {
					rs = stmt.getResultSet();

					WriteExcel(clientObj, rs);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs, stmt);
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();

		long duration = (endTime - startTime);
		Log.debug(Utils.millisToShortDHMS(duration));
	}



	private static void WriteExcel(ClientQueryModel clientQueryModel, ResultSet resultSet) throws SQLException {

		String path = "new_excel_template.xlsx";

		Workbook workbook = new Workbook();
		workbook.open(path);
//		workbook = Grapecity.getWorkbook(workbook);

//		removing unrequired sheets
		workbook.getWorksheets().get("ReportDetails").delete();
		workbook.getWorksheets().get("RawData").delete();

		IWorksheet worksheet = workbook.getWorksheets().get("ReportOutput");

		ResultSetMetaData rsmd = resultSet.getMetaData();
		int noOfColumns = rsmd.getColumnCount();

		int reportNameRow = -1, reportNameCol = -1;
		int tableHeaderRow = -1, tableHeaderCol = -1;
		int reportDataRow = -1, reportDataCol = -1;

		boolean[] hideColumns = new boolean[noOfColumns];
		boolean[] datebefore1900 = new boolean[noOfColumns];
		String[] nullDisplay = new String[noOfColumns];

		Map<Integer, String> dataFormats = new HashMap<>();
		Map<Integer, String> datatypes = new HashMap<>();

		String KeyReportName = ReportHeader.getValue();
		String KeyHeaderName = TableHeader.getValue();
		String KeyDataName = ReportData.getValue();

		IStyle tableHeaderStyle = workbook.getStyles().get(TableHeaderStyle.getValue());
		String reportNameRange = null, tableHeaderRange = null, dataRange = null;
		IStyle dataStyle = null;

		// Checking for key modes style position;
		String endColumnName = CellInfo.ColumnIndexToName(noOfColumns);
		Log.debug("Searching Band in All Column and Row within Range A:{}" + endColumnName);

		if (workbook != null) {
			INames names = workbook.getNames();

			Log.debug("Total NameRange formulae :{}" + names.getCount());
			String fullRange = null, startRange, endRange;

			try {
				// Detecting ReportHeader cell from formulas to insert reportName.
				try {
					fullRange = names.get(KeyReportName).getRefersTo();
					reportNameRange = getActualCellRange(fullRange);
					names.get(KeyReportName).delete();
				} catch (Exception e) {
					Log.error("Couldn't find the {} so searching in cells of used range.", KeyReportName);
					fullRange = findRange(KeyReportName, worksheet);
				}
				worksheet.getRange(reportNameRange).setValue(clientQueryModel.getReportName());
			} catch (Exception e) {
				Log.error("Couldn't find the {} , so continuing ", KeyReportName);
			}

			try {

				// Detecting TableHeader cell from formulas to insert Column names.
				try {
					fullRange = names.get(KeyHeaderName).getRefersTo();
				} catch (Exception e) {
					Log.error("Couldn't find the {} so searching in cells of used range.", KeyHeaderName);
					fullRange = findRange(KeyHeaderName, worksheet);
				}
				tableHeaderRange = getActualCellRange(fullRange);
				tableHeaderStyle = workbook.getStyles().get(KeyHeaderName + "Style");
				tableHeaderRow = worksheet.getRange(tableHeaderRange).getRow();
				tableHeaderCol = worksheet.getRange(tableHeaderRange).getColumn();
				Log.debug("TableHeader : Row = {}, Column ={}", tableHeaderRow + " - " + tableHeaderCol);

			} catch (Exception e) {
				Log.error("Couldn't find the {} , so continuing ", KeyHeaderName);
			}

			try {
				// Detecting DataRecords cell from formulas to insert Records
				try {
					fullRange = names.get(KeyDataName).getRefersTo();
					names.get(KeyDataName).delete();
				} catch (Exception e) {
					Log.error("Couldn't find the {} so searching in cells of used range.", KeyDataName);
					fullRange = findRange(KeyDataName, worksheet);
				}
				dataRange = getActualCellRange(fullRange);
				dataStyle = workbook.getStyles().get(KeyDataName + "Style");
				reportDataRow = worksheet.getRange(dataRange).getRow();
				reportDataCol = worksheet.getRange(dataRange).getColumn();
				Log.debug("DataRange : Row = {}, Column ={}", reportDataRow + " - " + reportDataCol);

			} catch (Exception e) {
				Log.error("Couldn't find the {} , so continuing ", KeyDataName);
			}

			if (StringUtils.isNotEmpty(dataRange)) {
				// Inserting ReportData below
				String reportDataInfo = CellInfo.CellIndexToName(reportDataRow, reportDataCol);
				worksheet.getRange(reportDataInfo).setValue("");
			}

			int colPos = 0;
			for (int i = 0; i < noOfColumns; i++) {
				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);

				if (clientSelection != null) {
					if (clientSelection.getProperties() != null && clientSelection.getProperties().get("hdn") != null) {
						hideColumns[i] = (Boolean) clientSelection.getProperties().get("hdn");
					}

					if (hideColumns[i])
						continue;

					// Read column if to read date before 1900 and convert to string
//					datebefore1900[i] =false;
					if (clientSelection.getProperties() != null
							&& clientSelection.getProperties().containsKey("pryr")) {
						if (clientSelection.getProperties() != null
								&& clientSelection.getProperties().get("pryr") != null) {
							try {
								datebefore1900[i] = (Boolean) clientSelection.getProperties().get("pryr");
							} catch (ClassCastException e) {
								datebefore1900[i] = false;
							}

						}
					}

					// as per UI , show Null values as given by user
					if (clientSelection.getProperties().containsKey("nd"))
						nullDisplay[i] = (String) clientSelection.getProperties().get("nd");

					// getting data format
					String format = null;// "dd/MM/yyyy hh:mm:ss";
//					dataFormats.put(i,(format));
					if (clientSelection.getProperties() != null && clientSelection.getProperties().containsKey("fmt")) {
						if (clientSelection.getProperties() != null
								&& clientSelection.getProperties().get("fmt") != null) {
							format = (String) clientSelection.getProperties().get("fmt");
							if (StringUtils.isNotBlank(format) && !"0".equals(format) && !"null".equals(format)) {
								dataFormats.put(i, (format.replaceAll("%", "\\\\%")));
							}
						}
					}

					String dataType = null;
					if (clientSelection.getProperties().containsKey("datatype")) {
						Object type = clientSelection.getProperties().get("datatype"); //
						if (type instanceof DataType)
							dataType = ((DataType) type).getName();
						else
							dataType = (String) type;

						datatypes.put(i, dataType);
					}

					String cellInfo = CellInfo.CellIndexToName(tableHeaderRow, i);
					worksheet.getRange(cellInfo).setValue(clientSelection.getName());
					if (tableHeaderStyle != null)
						worksheet.getRange(cellInfo).setStyle(tableHeaderStyle);

					colPos++;
				}

			}

			// When the timezone is not observing Daylight savings, LocaDateTime conversion
			// is not required
			boolean dateHandlingNeeded = false;
			if (TimeZone.getDefault() != null) {
				dateHandlingNeeded = TimeZone.getDefault().observesDaylightTime();
			}

			Log.debug("Timezone handling needed :{}" + dateHandlingNeeded);

			// inserting records now below
			int rowPos = reportDataRow;
			String startCellPos = null;
			String dataType = null;

			if (StringUtils.isNotEmpty(dataRange)) {
				while (resultSet.next()) {
					int recPos = 0;
					int cellPos = 0;
					for (int i = 0; i < noOfColumns; i++) {
						recPos++;
						if (hideColumns[i])
							continue;

						startCellPos = CellInfo.CellIndexToName(rowPos, cellPos);
						cellPos++;
						Object value = null;

						try {

							if (resultSet.getObject(recPos) == null) {
								if (StringUtils.isNotBlank(nullDisplay[i])) {
									value = "'" + nullDisplay[i];
								} else {
									continue;
								}
							} else

							if (dateHandlingNeeded && rsmd.getColumnType(recPos) == Types.DATE) {
								if (resultSet.getDate(recPos) == null) {
									if (StringUtils.isNotBlank(nullDisplay[i])) {
										value = "'" + nullDisplay[i];
									} else {
										continue;
									}
								} else {
									java.sql.Timestamp ts = new java.sql.Timestamp(
											(resultSet.getDate(recPos)).getTime());

									if (datebefore1900[i - 1] && ts.getYear() < 0) {
										if (StringUtils.isNotEmpty(dataFormats.get(i)))
											value = "'"
													+ convertDatetoString(new Date(ts.getTime()), dataFormats.get(i));
									} else {

										java.time.LocalDateTime dateValue = (java.time.LocalDateTime) ts
												.toLocalDateTime();
										value = dateValue;
									}
								}

							} else if (dateHandlingNeeded && rsmd.getColumnType(i) == Types.TIMESTAMP) {
								if (resultSet.getObject(recPos) == null) {
									if (StringUtils.isNotBlank(nullDisplay[i])) {
										value = "'" + nullDisplay[i];
									} else {
										continue;
									}
								} else {
									java.sql.Timestamp ts = (java.sql.Timestamp) resultSet.getObject(recPos);

									if (datebefore1900[i] && ts.getYear() < 0) {
										if (StringUtils.isNotEmpty(dataFormats.get(i)))
											value = "'"
													+ convertDatetoString(new Date(ts.getTime()), dataFormats.get(i));
									} else {
										java.time.LocalDateTime datetimeValue = (java.time.LocalDateTime) ts
												.toLocalDateTime();
										value = datetimeValue;
									}
								}

							} else if (rsmd.getColumnType(recPos) == Types.CLOB
									|| rsmd.getColumnType(recPos) == Types.NCLOB) {
								if (resultSet.getClob(recPos) == null) {
									if (StringUtils.isNotBlank(nullDisplay[i])) {
										value = "'" + nullDisplay[i];
									} else {
										continue;
									}
								} else {
									Clob c = resultSet.getClob(recPos);
									if (c != null) {
										try {
											value = read(c);
										} catch (IOException e) {
											Log.error("Exception : Cannot read Clob value ");
										}
									}
								}
							} else if (rsmd.getColumnType(recPos) == Types.DECIMAL
									|| rsmd.getColumnType(recPos) == Types.DOUBLE
									|| rsmd.getColumnType(recPos) == Types.NUMERIC
									|| rsmd.getColumnType(recPos) == Types.FLOAT
									|| rsmd.getColumnType(recPos) == Types.REAL) {
								if (resultSet.getBigDecimal(recPos) == null) {
									if (StringUtils.isNotBlank(nullDisplay[i])) {
										value = "'" + nullDisplay[i];
									} else {
										continue;
									}
								} else {
									value = resultSet.getBigDecimal(recPos);
								}
							} else {
								value = resultSet.getObject(recPos);
							}

							worksheet.getRange(startCellPos).setValue(value);

						} catch (IllegalArgumentException e) {
							Log.error(
									"Unsupported data type: we only support data of type: Primitive Type, Number, String, Boolean, CalcError, Date, Calendar, Character.",
									e.toString());
						} catch (Exception e) {
							Log.error("Error created while writing to excel {}", e.getMessage());
						}

					}
					rowPos++;

					if (rowPos % 10000 == 0) {
						Log.debug("till now inserted records :{}" + rowPos);
					}
				}
			}

			Log.debug("Completed inseting resultset from row {}" + rowPos);

			if (dataStyle != null) {
				String startCell = CellInfo.CellIndexToName(reportDataRow, 0);
				String endCell = CellInfo.CellIndexToName(rowPos, noOfColumns);
				worksheet.getRange(startCell + ":" + endCell).setStyle(dataStyle);
//				Log.debug("Applyed Data Style for Range {}", startCell + ":" + endCell);
			}

			int lockedColumnsCount = -1;
			if (clientQueryModel.getProperties() != null
					&& clientQueryModel.getProperties().containsKey("widgetProperties")) {
				Map<String, Object> wProp = (Map<String, Object>) clientQueryModel.getProperties()
						.get("widgetProperties");
				if (wProp != null && wProp.containsKey("lockedColumnsCount")
						&& wProp.get("lockedColumnsCount") != null) {
					lockedColumnsCount = (Integer) wProp.get("lockedColumnsCount");
				}
			}

			if (lockedColumnsCount != -1) {
				worksheet.freezePanes(tableHeaderRow + 1, lockedColumnsCount);
			}
			Log.debug("Assigned if Panel freeze exists..");

			// AutoFit for Columns
			/*
			 * colCell = 0; for (int i = 0; i < noOfColumns; i++) { if (hideColumns[i])
			 * continue; String startCellPos = CellInfo.CellIndexToName(rowPos, colCell);
			 * worksheet.getRange(startCellPos).autoFilter(1); colCell++; }
			 */

			// AutoFit for Columns
			if ((rowPos - reportDataRow) <= 50001) {

				for (int i = 0; i < noOfColumns; i++) {
//					Log.debug(">> .. > Applying Autofit for column :{}" + i);
					if (hideColumns[i])
						continue;
					try {
						dataType = datatypes.get(i);
						startCellPos = CellInfo.ColumnIndexToName(i);

						if ("DATETIME".equals(dataType) || "DATE".equals(dataType)) {
							worksheet.getRange(startCellPos + ":" + startCellPos).setColumnWidth(22.0);
						} else {

							worksheet.getRange(startCellPos + ":" + startCellPos).getColumns().autoFit();
							double colWidth = worksheet.getRange(startCellPos + ":" + startCellPos).getColumnWidth();
							if (colWidth < 18) {
								worksheet.getRange(startCellPos + ":" + startCellPos).setColumnWidth(19.0);
							}
						}

					} catch (Exception e) {
						Log.error("EATEN : To continue without autofit of columns, Cause is: {}", e.getMessage());
					}

				}
//				Log.debug(">> .. > Applied Autofit...");
			} else
				Log.debug("Autofit is Skipped due to Limit crossed 50001 >{}" + (rowPos - reportDataRow));

			if (rowPos > reportDataRow) {
				// Applying format for WorkSheet
				UtilsExcel.applyAllFormats(worksheet, clientQueryModel, reportDataRow + 1, rowPos, hideColumns);
				Log.debug("Applied Formats...");
			}

			// Creating Report Details Sheet
						UtilsExcel.createDetailsSheet(workbook, clientQueryModel, tableHeaderStyle);
			Log.debug("Created Details Sheet");

			IWorksheet activeSheet = workbook.getWorksheets().get(workbook.getActiveSheet().getName());
			activeSheet.activate();
			Log.debug("Sheet {} has been set as active sheet according to the Template.",
					workbook.getActiveSheet().getName());

			Log.debug("Workbook saving...");
			Random rand = new Random();
			// Generate random integers in range 0 to 999
			int rand_int = rand.nextInt(1000);
			workbook.save("outputs/prod_excel_" + rand_int + ".xlsx");

			Log.debug("Workbook saved now with "+rowPos);
//						totalRecords = rowPos - (reportDataRow + 1);

		}

	}

	public static String read(Clob c) throws SQLException, IOException {
		StringBuilder sb = new StringBuilder((int) c.length());
		Reader r = c.getCharacterStream();
		char[] cbuf = new char[2048];
		int n;
		while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
			sb.append(cbuf, 0, n);
		}
		return sb.toString();
	}

	public static String convertDatetoString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	private static String findRange(String findValue, IWorksheet worksheet) {

		Object[][] data = (Object[][]) worksheet.getRange(getActualCellRange(worksheet.getUsedRange().toString()))
				.getValue();
		int row = 0;
		int col = 0;
		for (Object[] root : data) {
			col = 0;
			for (Object rec : root) {
				if (rec != null && ((String) rec).equals("${" + findValue + "}")) {
					Log.debug("Found Cell for {} at {}", findValue + " - " + CellInfo.CellIndexToName(row, col));
					return "=" + worksheet.getName() + "!" + CellInfo.CellIndexToName(row, col);
				}
				col++;
			}
			row++;
		}

		return null;
	}

	private static String getActualCellRange(String range) {
		return range.substring(range.indexOf("!") + 1, range.length());
	}

}
