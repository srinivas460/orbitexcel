package orbit.excel;


import static models.TemplateVariableType.ReportData;
import static models.TemplateVariableType.ReportHeader;
import static models.TemplateVariableType.SheetOutput;
import static models.TemplateVariableType.SheetRawData;
import static models.TemplateVariableType.TableHeader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.grapecity.documents.excel.CellInfo;
import com.grapecity.documents.excel.Color;
import com.grapecity.documents.excel.ConsolidationFunction;
import com.grapecity.documents.excel.INames;
import com.grapecity.documents.excel.IPivotCache;
import com.grapecity.documents.excel.IPivotField;
import com.grapecity.documents.excel.IPivotTable;
import com.grapecity.documents.excel.IStyle;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.PivotFieldOrientation;
import com.grapecity.documents.excel.SaveFileFormat;
import com.grapecity.documents.excel.Workbook;

import models.ClientQueryModel;
import models.ClientSelection;
import models.DataType;
import utils.Logs;
public class WriteExcel {


	private static final String TableHeaderStyle = "TableHeaderStyle";
	private static Map<String, Integer> operators = new HashMap<String, Integer>();


	public void writePivotTable(Workbook workbook, ResultSet resultSet, ClientQueryModel clientQueryModel,
			OutputStream outputStream) {

		IWorksheet rawDatasheet = workbook.getWorksheets().get(SheetRawData.getValue());
		// Clearing the ReportOutput sheet for inserting Pivot Table
		IWorksheet pivotSheet = workbook.getWorksheets().get(SheetOutput.getValue());
		if (pivotSheet != null) {
			pivotSheet.delete();
		}
		pivotSheet = workbook.getWorksheets().add();
		workbook.getWorksheets().get(2).setName(SheetOutput.getValue());

		int totalRecords = writeData(workbook, rawDatasheet, resultSet, clientQueryModel, outputStream, true);
//		workbook.save(outputStream);

		createPivotTable(workbook, rawDatasheet, pivotSheet, totalRecords, clientQueryModel);

		workbook.save(outputStream);
	}

	private void createPivotTable(Workbook workbook, IWorksheet rawDatasheet, IWorksheet pivotSheet, int totalRecords,
			ClientQueryModel clientQueryModel) {
		try {
			int noOfColumns = clientQueryModel.getSelections().size();
			boolean[] hideColumns = new boolean[noOfColumns];

			int colCell = 0;
			int colPos = 0;
			for (int i = 0; i < noOfColumns; i++) {
				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
				if (clientSelection != null) {
					if (clientSelection.getProperties() != null && clientSelection.getProperties().containsKey("hdn")) {
						if (clientSelection.getProperties() != null
								&& clientSelection.getProperties().get("hdn") != null) {
							hideColumns[i] = (Boolean) clientSelection.getProperties().get("hdn");
							if (hideColumns[i] == true) {
								continue;
							}
						} else
							hideColumns[i] = false;
					}
				}
				colPos++;
			}

			String pivotDataRange = CellInfo.CellIndexToName((totalRecords + 1), colPos - 1);

			IPivotCache pivotcache = workbook.getPivotCaches().create(rawDatasheet.getRange("A1:" + pivotDataRange));
			IPivotTable pivottable = pivotSheet.getPivotTables().add(pivotcache, pivotSheet.getRange("A1"),
					"OrbitPivotTable");
			int rowPos = 0, valuePos = 0, colsPos = 0;
			for (int i = 0; i < noOfColumns; i++) {
				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
				if (!hideColumns[i]) {
					IPivotField fields = pivottable.getPivotFields().get((String) clientSelection.getName());
					fields.setName((String) clientSelection.getName());
					if ("ROW".equals(clientSelection.getDimension())) {
						fields.setOrientation(PivotFieldOrientation.RowField);
						fields.setPosition(rowPos++);
					} else if ("VALUE".equals(clientSelection.getDimension())) {
						fields.setOrientation(PivotFieldOrientation.DataField);
						fields.setPosition(valuePos++);
						ConsolidationFunction function = getValueFunction(
								((String) clientSelection.getProperties().get("aggregation")).toUpperCase());
						if (function != null)
							fields.setFunction(function);
					} else if ("COL".equals(clientSelection.getDimension())) {
						fields.setOrientation(PivotFieldOrientation.ColumnField);
						fields.setPosition(colsPos++);
					}
				}
			}

			int pivotDataBodyCount = pivottable.getDataBodyRange().getRows().getCount() - 1;
			for (int i = 0; i < noOfColumns; i++) {
				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
				if (!hideColumns[i]) {
					String format = (String) clientSelection.getProperties().get("fmt");
					if (StringUtils.isNotEmpty(format)) {

						int pivotCol = 0;
						for (IPivotField field : pivottable.getPivotFields()) {
							if (((String) clientSelection.getName()).equals(field.getName())) {
								String pivotStartRange = CellInfo.CellIndexToName(colsPos, pivotCol);
								String pivotEndRange = CellInfo.CellIndexToName(pivotDataBodyCount, pivotCol);
								String range = pivotStartRange + ":" + pivotEndRange;
								Logs.debug("Format Range : " + range);if (clientSelection.getProperties().get("nnbr") != null) {
									if ((boolean) clientSelection.getProperties().get("nnbr")) {
										format = format + ";(" + format + ")";
									}
								}
								pivotSheet.getRange(range).setNumberFormat(format);
							}

							pivotCol++;
						}

					}
				}
			}

		} catch (Exception e) {
			Logs.error("Error when generating excel", e.getMessage());
			throw new RuntimeException("Error when generating excel. Error=>" + e.getMessage());
		}

	}

	private ConsolidationFunction getValueFunction(String aggregation) {

		if ("COUNT_DISTINCT".equals(aggregation)) {
			return ConsolidationFunction.Count;
		} else if ("SUM".equals(aggregation)) {
			return ConsolidationFunction.Sum;
		} else if ("AVG".equals(aggregation)) {
			return ConsolidationFunction.Average;
		} else if ("COUNT".equals(aggregation)) {
			return ConsolidationFunction.Count;
		} else if ("MIN".equals(aggregation)) {
			return ConsolidationFunction.Min;
		} else if ("MAX".equals(aggregation)) {
			return ConsolidationFunction.Max;
		} else if ("STDDEV".equals(aggregation)) {
			return ConsolidationFunction.StdDev;
		} else if ("STDDEVP".equals(aggregation)) {
			return ConsolidationFunction.StdDevp;
		} else if ("VARIANCE".equals(aggregation)) {
			return ConsolidationFunction.Var;
		} else if ("VARIANCEP".equals(aggregation)) {
			return ConsolidationFunction.Varp;
		}
		return null;
	}

	public int writeData(Workbook workbook, IWorksheet worksheet, ResultSet resultSet,
			ClientQueryModel clientQueryModel, OutputStream outputStream, boolean forPivot) {
		return writeData(workbook, worksheet, resultSet, clientQueryModel, outputStream, forPivot,false);
	}

	public int writeData(Workbook workbook, IWorksheet worksheet, ResultSet resultSet,
			ClientQueryModel clientQueryModel, OutputStream outputStream, boolean forPivot,boolean isPdfOutput) {
		int totalRecords = 0;
		
		UtilsExcel utilsExcel =new UtilsExcel();
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int noOfColumns = rsmd.getColumnCount();

//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			writeUsingOutputStream(gson.toJson(clientQueryModel));

			boolean[] hideColumns = new boolean[noOfColumns];
			boolean[] datebefore1900 = new boolean[noOfColumns];
			String[] nullDisplay = new String[noOfColumns];
			String[] dataBgColor = new String[noOfColumns];

			int reportNameRow = -1, reportNameCol = -1;
			int tableHeaderRow = -1, tableHeaderCol = -1;
			int reportDataRow = -1, reportDataCol = -1;

			if (!forPivot) {
				IWorksheet rawData = workbook.getWorksheets().get(SheetRawData.getValue());
				if (rawData != null) {
					rawData.delete();
					Logs.debug("Removed RawSheet from workbook when direct exporting excel from datareport ..");
				}
			}

			if (forPivot) {
				reportNameCol = 0;
				reportNameRow = 0;
			}

			int finalColumns = 0;
			for (int i = 0; i < noOfColumns; i++) {
				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
				if (clientSelection != null) {
					if (clientSelection.getProperties() != null && clientSelection.getProperties().get("hdn") != null) {
						hideColumns[i] = (Boolean) clientSelection.getProperties().get("hdn");
					}
					if (hideColumns[i] == true) {
						continue;
					} else
						hideColumns[i] = false;
					finalColumns++;
				}
			}
			
			String KeyReportName = ReportHeader.getValue();
			String KeyHeaderName = TableHeader.getValue();
			String KeyDataName = ReportData.getValue();


			IStyle tableHeaderStyle = workbook.getStyles().get(TableHeaderStyle);
			String reportNameRange = null, tableHeaderRange=null, dataRange=null;
			IStyle dataStyle = null;

			// Checking for key modes style position;
			String endColumnName = CellInfo.ColumnIndexToName(noOfColumns);
			Logs.debug("Searching Band in All Column and Row within Range A:{}", endColumnName);

			if (workbook != null) {
				INames names = workbook.getNames();

				Logs.debug("Total NameRange formulae :{}", names.getCount());
				String fullRange = null, startRange, endRange;
				
				try {
				// Detecting ReportHeader cell from formulas to insert reportName.
					try {
						fullRange = names.get(KeyReportName).getRefersTo();
						reportNameRange = getActualCellRange(fullRange);
						names.get(KeyReportName).delete();
					} catch (Exception e) {
						Logs.error("Couldn't find the {} so searching in cells of used range.", KeyReportName);
						fullRange = findRange(KeyReportName, worksheet);
					}
					worksheet.getRange(reportNameRange).setValue(clientQueryModel.getReportName());
				}catch (Exception e) {
					Logs.error("Couldn't find the {} , so continuing ", KeyReportName);
				}
				
				try {

					// Detecting TableHeader cell from formulas to insert Column names.
					try {
						fullRange = names.get(KeyHeaderName).getRefersTo();
					} catch (Exception e) {
						Logs.error("Couldn't find the {} so searching in cells of used range.", KeyHeaderName);
						fullRange = findRange(KeyHeaderName, worksheet);
					}
					tableHeaderRange = getActualCellRange(fullRange);
					tableHeaderStyle = workbook.getStyles().get(KeyHeaderName + "Style");
					tableHeaderRow = worksheet.getRange(tableHeaderRange).getRow();
					tableHeaderCol = worksheet.getRange(tableHeaderRange).getColumn();
					Logs.debug("TableHeader : Row = {}, Column ={}", tableHeaderRow+" : "+ tableHeaderCol);

				}catch (Exception e) {
				Logs.error("Couldn't find the {} , so continuing ", KeyHeaderName);
			}

				try {
				// Detecting DataRecords cell from formulas to insert Records
				try {
					fullRange = names.get(KeyDataName).getRefersTo();
					names.get(KeyDataName).delete();
				} catch (Exception e) {
					Logs.error("Couldn't find the {} so searching in cells of used range.", KeyDataName);
					fullRange = findRange(KeyDataName, worksheet);
				}
				dataRange = getActualCellRange(fullRange);
				dataStyle = workbook.getStyles().get(KeyDataName + "Style");
				reportDataRow = worksheet.getRange(dataRange).getRow();
				reportDataCol = worksheet.getRange(dataRange).getColumn();
				Logs.debug("DataRange : Row = {}, Column ={}", reportDataRow+" : "+ reportDataCol);
				
			}catch (Exception e) {
				Logs.error("Couldn't find the {} , so continuing ", KeyDataName);
			}

			}

			if(StringUtils.isNotEmpty(dataRange)) {
				// Inserting ReportData below
				String reportDataInfo = CellInfo.CellIndexToName(reportDataRow, reportDataCol);
				worksheet.getRange(reportDataInfo).setValue("");
			}


			Map<Integer, String> dataFormats = new HashMap<>();
			Map<Integer, String> datatypes = new HashMap<>();

//			Inserting ReportName below
//			String reportNameInfo = CellInfo.CellIndexToName(reportNameRow, reportNameCol);
//			worksheet.getRange(reportNameInfo).setValue(clientQueryModel.getReportName());
			if(StringUtils.isNotEmpty(tableHeaderRange)) {
		
			int colPos = 0;
				for (int i = 0; i < noOfColumns; i++) {
					ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
					if (clientSelection != null) {
						if (hideColumns[i])
							continue;
						
//						getting background color of column
						if (clientSelection.getProperties() != null
								&& clientSelection.getProperties().containsKey("bgclr")) {
							dataBgColor[i]=(String) clientSelection.getProperties().get("bgclr");
						}
						
						
						//Read column if to read date before 1900 and convert to string
//						datebefore1900[i] =false;
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
							nullDisplay[i]=(String) clientSelection.getProperties().get("nd");
						

						//getting data format 
						String format = null;//"dd/MM/yyyy hh:mm:ss";
//						dataFormats.put(i,(format));
						if (clientSelection.getProperties() != null
								&& clientSelection.getProperties().containsKey("fmt")) {
							if (clientSelection.getProperties() != null
									&& clientSelection.getProperties().get("fmt") != null) {
								 format =  (String) clientSelection.getProperties().get("fmt");
								if (StringUtils.isNotBlank(format) && !"0".equals(format) && !"null".equals(format)) {
									dataFormats.put(i,(format.replaceAll("%", "\\\\%")));
								}
							}
						}


						String dataType=null;
						if (clientSelection.getProperties().containsKey("datatype")) {
							Object type = clientSelection.getProperties().get("datatype"); //
							if (type instanceof DataType)
								dataType = ((DataType) type).getName();
							else
								dataType = (String) type;
						}
						datatypes.put(i, dataType);
						
						String cellInfo = CellInfo.CellIndexToName(tableHeaderRow, colPos);
						worksheet.getRange(cellInfo).setValue(clientSelection.getName());
						if (tableHeaderStyle != null)
							worksheet.getRange(cellInfo).setStyle(tableHeaderStyle);
					}

					colPos++;

				}

			}
			

			// When the timezone is not observing Daylight savings, LocaDateTime conversion is not required
			boolean dateHandlingNeeded = false;
			if (TimeZone.getDefault() != null) {
				dateHandlingNeeded = TimeZone.getDefault().observesDaylightTime();
			}

			Logs.debug("Timezone handling needed :{}",dateHandlingNeeded);
			
			// inserting records now below
			int rowPos = reportDataRow;
			String startCellPos=null;
			String dataType=null;
			
			if (StringUtils.isNotEmpty(dataRange)) {
				while (resultSet.next()) {
					int recPos=0;
					int cellPos=0;
					for (int i = 0; i< noOfColumns; i++) {
						recPos++;
						if (hideColumns[i])
							continue;
						
						startCellPos = CellInfo.CellIndexToName(rowPos, cellPos);
						cellPos++;
						Object value =null;
						
						try {
						
						if (resultSet.getObject(recPos)==null) {
							if(StringUtils.isNotBlank(nullDisplay[i])) {
								value ="'"+nullDisplay[i];
							}else {
								continue;
							}
						}else
						
						if (dateHandlingNeeded && rsmd.getColumnType(recPos) == Types.DATE) {
							if (resultSet.getDate(recPos) == null) {
								if (StringUtils.isNotBlank(nullDisplay[i])) {
									value = "'" + nullDisplay[i];
								} else {
									continue;
								}
							} else {
								java.sql.Timestamp ts = new java.sql.Timestamp((resultSet.getDate(recPos)).getTime());

								if (datebefore1900[i - 1] && ts.getYear() < 0) {
									if (StringUtils.isNotEmpty(dataFormats.get(i)))
										value = "'" + utilsExcel.convertDatetoString(new Date(ts.getTime()),
												dataFormats.get(i));
								} else {

									java.time.LocalDateTime dateValue = (java.time.LocalDateTime) ts.toLocalDateTime();
									value = dateValue;
								}
							}

						} else if (dateHandlingNeeded && rsmd.getColumnType(recPos) == Types.TIMESTAMP) {
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
										value = "'" + utilsExcel.convertDatetoString(new Date(ts.getTime()),
												dataFormats.get(i));
								} else {
									java.time.LocalDateTime datetimeValue = (java.time.LocalDateTime) ts
											.toLocalDateTime();
									value = datetimeValue;
								}
							}

						} else if (rsmd.getColumnType(recPos) == Types.CLOB || rsmd.getColumnType(recPos) == Types.NCLOB) {
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
										value = utilsExcel.read(c);
									} catch (IOException e) {
										Logs.error("Exception : Cannot read Clob value ");
									}
								}
							}
						} else if (rsmd.getColumnType(recPos) == Types.DECIMAL || rsmd.getColumnType(recPos) == Types.DOUBLE
								|| rsmd.getColumnType(recPos) == Types.NUMERIC || rsmd.getColumnType(recPos) == Types.FLOAT
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
						} else if (rsmd.getColumnType(recPos) == Types.BLOB) {
							if (resultSet.getBlob(recPos) == null) {
								if (StringUtils.isNotBlank(nullDisplay[i])) {
									value = "'" + nullDisplay[i];
								} else {
									continue;
								}
							} else {
								try {
									java.sql.Blob ablob = resultSet.getBlob(recPos);
									value = new String(ablob.getBytes(1l, (int) ablob.length()));
								} catch (Exception e) {
									Logs.error("Exception : Cannot read Blob value ", e);
								}
							}
						} else {
							value = resultSet.getObject(recPos);
						}
						
						

						worksheet.getRange(startCellPos).setValue(value);
					} catch (IllegalArgumentException e) {
						Logs.error("Unsupported data type: Blob.  we only support data of type: Primitive Type, Number, String, Boolean, CalcError, Date, Calendar, Character.",e);
						throw new RuntimeException("Unsupported data type: Blob.  we only support data of type: Primitive Type, Number, String, Boolean, CalcError, Date, Calendar, Character.",e);
					}catch (Exception e) {
						Logs.error("Error created while writing to excel {}",e);
					}
			
					}
					rowPos++;
					
					if(rowPos%10000==0) {
						Logs.debug("till now inserted records :{}",rowPos);
					}
				}
			}
			
			Logs.debug("Completed inseting resultset from row {}",rowPos);
			
			if (dataStyle != null) {
				String startCell= CellInfo.CellIndexToName(reportDataRow, 0);
				String endCell= CellInfo.CellIndexToName(rowPos, noOfColumns);
				worksheet.getRange(startCell+":"+endCell).setStyle(dataStyle);
				Logs.debug("Applyed Data Style for Range {}",startCell+":"+endCell);
			}
			
			//setting background color
			try {
				for (int i = 0; i < noOfColumns; i++) {
					if (hideColumns[i])
						continue;
					if (dataBgColor[i] != null) {
						IStyle style = workbook.getStyles().add("orbitStyle"+i);
						style.getInterior().setColor(utilsExcel.hex2Rgb("#" + dataBgColor[i]));
						String startCell = CellInfo.CellIndexToName(reportDataRow, i);
						String endCell = CellInfo.CellIndexToName(rowPos-1, i);
						worksheet.getRange(startCell + ":" + endCell).setStyle(style);
					}
				}
			}catch(Exception e) {
				Logs.error("EATEN : Error while applying background color ",e);
			}
			
			int lockedColumnsCount =-1;
			if (clientQueryModel.getProperties() != null
					&& clientQueryModel.getProperties().containsKey("widgetProperties")) {
				Map<String, Object> wProp = (Map<String, Object>) clientQueryModel.getProperties()
						.get("widgetProperties");
				if (wProp != null && wProp.containsKey("lockedColumnsCount") && wProp.get("lockedColumnsCount")!=null) {
					lockedColumnsCount = (Integer)wProp.get("lockedColumnsCount");
				}
			}
			
			if(lockedColumnsCount!=-1) {
				worksheet.freezePanes(tableHeaderRow+1, lockedColumnsCount);
			}
			Logs.debug("Assigned if Panel freeze exists..");
			
			// AutoFit for Columns
			/*
			 * colCell = 0; for (int i = 0; i < noOfColumns; i++) { if (hideColumns[i])
			 * continue; String startCellPos = CellInfo.CellIndexToName(rowPos, colCell);
			 * worksheet.getRange(startCellPos).autoFilter(1); colCell++; }
			 */
			
			// AutoFit for Columns
			if ((rowPos - reportDataRow) <= 50001) {

				for (int i = 0; i < noOfColumns; i++) {
					Logs.debug(">> .. > Applying Autofit for column :{}", i);
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
						Logs.error("EATEN : To continue without autofit of columns, Cause is: {}", e.getMessage());
					}

				}
				Logs.debug(">> .. > Applied Autofit...");
			}else
				Logs.debug("Autofit is Skipped due to Limit crossed 50001 >{}",(rowPos - reportDataRow));



			if (rowPos > reportDataRow) {
				// Applying format for WorkSheet
				utilsExcel.applyAllFormats(worksheet, clientQueryModel, reportDataRow + 1, rowPos, hideColumns);
				Logs.debug("Applied Formats...");
			}

			// Creating Report Details Sheet
			utilsExcel.createDetailsSheet(workbook, clientQueryModel, tableHeaderStyle);
			Logs.debug("Created Details Sheet");

			IWorksheet activeSheet = workbook.getWorksheets().get(workbook.getActiveSheet().getName());
			activeSheet.activate();
			Logs.debug("Sheet {} has been set as active sheet according to the Template.",workbook.getActiveSheet().getName());
			
			Logs.debug("Workbook saving...");
			if (!forPivot) {
				if(isPdfOutput)
					workbook.save(outputStream, SaveFileFormat.Pdf);
				else
					workbook.save(outputStream);
			}

			Logs.debug("Workbook saved now...");
			totalRecords = rowPos - (reportDataRow + 1);

		} catch (SQLException e) {
			Logs.error("Error when writing report process {}", e);
			throw new RuntimeException("Error when generating report. Error=>" + e.getMessage());
		}

		return totalRecords;
	}

	private String findRange(String findValue, IWorksheet worksheet) {

		Object[][] data = (Object[][]) worksheet.getRange(getActualCellRange(worksheet.getUsedRange().toString()))
				.getValue();
		int row = 0;
		int col = 0;
		for (Object[] root : data) {
			col = 0;
			for (Object rec : root) {
				if (rec != null && ((String) rec).equals("${" + findValue + "}")) {
					Logs.debug("Found Cell for {} at {}", findValue+"   :  "+ CellInfo.CellIndexToName(row, col));
					return "=" + worksheet.getName() + "!" + CellInfo.CellIndexToName(row, col);
				}
				col++;
			}
			row++;
		}

		return null;
	}

	private String getActualCellRange(String range) {
		return range.substring(range.indexOf("!") + 1, range.length());
	}

	public static String getParamValueWithOperators(String Operator, List<String> paramValues, String paramValue) {
		// TODO Auto-generated method stub
		String result = "";
//		if (paramValues == null && "".equals(paramValue)) {
//			return "";
//		}

		switch (Operator) {
			case "=":
				result = "is equal to " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
			case "<>":
				result = "is not equal to " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
				
			case "BEGINSWITH":
				result = " begins with " + (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
			case "BEGINSWITHIGNORECASE":
				result = " begins with () " + (paramValues != null ? String.join(" , ", paramValues) : paramValue);
			break;
			case "NOT BEGINSWITH":
				result = " does not begin with " + (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
	

			case "ENDSWITH":
				result = " ends with " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;

			case "NOT ENDSWITH":
				result = " does not end with " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
				

			case "CONTAINS":
			case "LIKE":
				result = " contains " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
				

			case "NOT CONTAINS":
			case "NOT LIKE":
				result = " does not contains " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
				
			case ">":
				result = "is greater than " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
			case ">=":
				result = "is greater than or equal to " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
			case "<":
				result = "is less than " + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
			case "<=":
				result = "is less than or equal to" + (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
				
			case "BETWEEN":
				result = "" + Operator.toLowerCase() + " "
						+ (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
			case "NOT BETWEEN":
				result = "not in between "
						+ (paramValues != null ? String.join(" and ", paramValues) : paramValue);
				break;
				
			case "IN":
				result = "is in list "
						+ (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
			case "NOT IN":
				result = "is not in list "
						+ (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
			case "EQUALS":
				result = "is equal to "
						+ (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
			case "NOT EQUALS":
				result = "is not equal to "
						+ (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
			
			case "IS NULL":
			case "IS NOT NULL":
			case "TOP":
			case "BOTTOM":
				result = "" + Operator.toLowerCase().replace("null", "empty");
				break;
	
			default:
				result = (paramValues != null ? String.join(" , ", paramValues) : paramValue);
				break;
		}
		return result;
	}

	private static void populateOperators() {
		operators.put("between", 1);
		operators.put("not between", 2);
		operators.put("=", 3);
		operators.put("==", 3);
		operators.put("<>", 4);
		operators.put(">", 5);
		operators.put("<", 6);
		operators.put(">=", 7);
		operators.put("<=", 8);
		operators.put("!=", 4);
	}

	public static Color hex2Rgb(String colorStr) {
		return Color.FromArgb(Integer.valueOf(colorStr.substring(1, 3), 16),
				Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	private static void ErrorThrow(String e) {
		throw new RuntimeException(e);

	}

}
