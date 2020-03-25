

package orbit.excel;
import utils.Logs;
//
//import java.awt.Color;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.sql.Clob;
//import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.SpreadsheetVersion;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
//import org.apache.poi.ss.usermodel.DataConsolidateFunction;
//import org.apache.poi.ss.usermodel.DataFormat;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.FontFormatting;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Name;
//import org.apache.poi.ss.usermodel.PatternFormatting;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.ss.util.AreaReference;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.CellReference;
//import org.apache.poi.ss.util.CellUtil;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFPivotTable;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFSheetConditionalFormatting;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColFields;
//import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTItems;
//import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField;
//import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotFields;
//import org.openxmlformats.schemas.spreadsheetml.x2006.main.STAxis;
//import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.orbit.reporting.domain.metaobjects.business.types.AggregationType;
//import com.orbit.reporting.domain.metaobjects.business.types.Alignment;
//import com.orbit.reporting.domain.metaobjects.business.types.DataType;
//import com.orbit.reporting.domain.metaobjects.report.Template;
//import com.orbit.reporting.domain.security.User;
//import com.orbit.reporting.dto.query.ClientConstraint;
//import com.orbit.reporting.dto.query.ClientQueryModel;
//import com.orbit.reporting.dto.query.ClientReport;
//import com.orbit.reporting.dto.query.ClientSelection;
//import com.orbit.reporting.dto.query.ConditionalFormat;
//import com.orbit.reporting.services.OrbitQueryResult;
///**
// * The Class ExcelUtils.
// */
public class ExcelUtils {
//
//	/** The Constant logger. */
//	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
//
//	/** The operators. */
//	private static Map<String, Integer> operators = new HashMap<String, Integer>();
//
//	/**
//	 * Generate default yarg template.
//	 *
//	 * @param defaultTemplateStream the default template stream
//	 * @param report                the report
//	 * @return the byte array output stream
//	 */
//	public static ByteArrayOutputStream generateDefaultYargTemplate(InputStream defaultTemplateStream,
//			ClientReport report) {
//		return generateDefaultYargTemplate(defaultTemplateStream, report, 2);
//	}
//
//	/**
//	 * Generate default yarg template.
//	 *
//	 * @param defaultTemplateStream the default template stream
//	 * @param report                the report
//	 * @param dataPosition          the data position
//	 * @return the byte array output stream
//	 */
//	public static ByteArrayOutputStream generateDefaultYargTemplate(InputStream defaultTemplateStream,
//			ClientReport report, int dataPosition) {
//
//		ClientQueryModel clientQueryModel = report.getClientQueryModel();
//
//		try {
//			// CHECKING DEFAULT TEMPLATE EXCEL FILE EXISTS OR NOT.
//			if (defaultTemplateStream == null) {
//				throw new RuntimeException("default template for excel doesn't exist");
//			}
//			
//			String KeyReportName = ReportHeader.getValue();
//			String KeyHeaderName = TableHeader.getValue();
//			String KeyDataName = ReportData.getValue();
//			String sheetOutput = SheetOutput.getValue();
//
//			int rowCount = 0;
//			Row row = null;
//			Cell cell = null;
//			// getting no of columns from clientquerymodel
//			int noOfColumns = clientQueryModel.getSelections().size();
//
//			// getting the cellreference details using noofcolumns
//			String lastColumnsName = CellReference.convertNumToColString(noOfColumns - 1);
//
//			XSSFWorkbook sWorkbook = new XSSFWorkbook(defaultTemplateStream);
//			XSSFSheet sheet = sWorkbook.getSheet(sheetOutput);
//
//			// get Row Positon , Cell Position and Cell Reference letter from sheet using
//			// default keys in templates
//			// get Reportname details.
//			int ReportNameRow = findRow(sheet, getKeyname(KeyReportName));
//			int ReportNameCellposition = findCell(sheet, getKeyname(KeyReportName));
//			String ReportNameCellName = CellReference.convertNumToColString(findCell(sheet, getKeyname(KeyReportName)));
//
//			// get Header details.
//			int HeaderRow = findRow(sheet, getKeyname(KeyHeaderName));
//			int HeaderCellposition = findCell(sheet, getKeyname(KeyHeaderName));
//			String HeaderCellName = CellReference.convertNumToColString(findCell(sheet, getKeyname(KeyHeaderName)));
//
//			// get Header details.
//			int DataRow = findRow(sheet, getKeyname(KeyDataName));
//			int DataCellposition = findCell(sheet, getKeyname(KeyDataName));
//			String DataCellName = CellReference.convertNumToColString(findCell(sheet, getKeyname(KeyDataName)));
//
//			// Setting Reportname according to the template position.
//			row = sheet.getRow(ReportNameRow);
//			cell = row.getCell(ReportNameCellposition);
//			cell.setCellValue(report.getName());
//
//			// Now merging the cell when columns are more than 3
//			if (noOfColumns > 3) {
//				// Merges the cells
//				CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, ReportNameCellposition,
//						noOfColumns - 1);
//				sheet.addMergedRegion(cellRangeAddress);
//				CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
//			}
//
//			// Setting name range formulae for Report Name row
//			Name reportname = sWorkbook.createName();
//			reportname.setNameName("_1." + KeyReportName);
//			reportname.setRefersToFormula(sheetOutput + "!$" + ReportNameCellName + "$" + (ReportNameRow + 1) + ":$"
//					+ lastColumnsName + "$" + (ReportNameRow + 1));
//
//			// setting header name in the row
//			boolean[] hideColumns = new boolean[noOfColumns];
//			String[] formats = new String[noOfColumns];
//
//			// getting template cell style and cloning it
//			CellStyle CellStyle = sWorkbook.createCellStyle();
//			row = sheet.getRow(HeaderRow);
//			cell = row.getCell(HeaderCellposition);
//			CellStyle.cloneStyleFrom(cell.getCellStyle());
//
//			// Setting header names here
//			int j = 0;
//			for (int i = 0; i < noOfColumns; i++) {
//				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
//				if (clientSelection != null) {
//					if (clientSelection.getProperties() != null && clientSelection.getProperties().get("hdn") != null) {
//						hideColumns[i] = (Boolean) clientSelection.getProperties().get("hdn");
//					}
//					if (hideColumns[i] == true) {
//						continue;
//					} else
//						hideColumns[i] = false;
//					cell = row.createCell(j);
//					cell.setCellStyle(CellStyle);
//					cell.setCellValue((String) clientSelection.getName());
//					j++;
//				}
//			}
//
//			// Setting name range formulae for header row
//			Name header = sWorkbook.createName();
//			header.setNameName("_2." + KeyHeaderName);
//			header.setRefersToFormula(sheetOutput + "!$" + HeaderCellName + "$" + (HeaderRow + 1) + ":$" + lastColumnsName
//					+ "$" + (HeaderRow + 1));
//
//			// Assigning Conditonal Format and DateFormat rules for columns
//			List<Object> cfrules = (List<Object>) report.getProperties().get("cfrules");
//			SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
//			ObjectMapper mapper = new ObjectMapper();
//			List<ArrayList<ConditionalFormattingRule>> condFormatRules = new ArrayList();
//			// DATA ROW
//			// getting template cell style and cloning it
//			CellStyle = sWorkbook.createCellStyle();
//			row = sheet.getRow(DataRow);
//			cell = row.getCell(DataCellposition);
//			CellStyle.cloneStyleFrom(cell.getCellStyle());
//
//			j = 0;
//			for (int i = 0; i < noOfColumns; i++) {
//				ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
//				Object datatype = clientSelection.getProperties() != null
//						? clientSelection.getProperties().get("datatype")
//						: "STRING";
//				Object alignmenttype = clientSelection.getProperties().get("aln");
//				ArrayList<ConditionalFormattingRule> condFormats = new ArrayList<ConditionalFormattingRule>();
//				if (hideColumns[i]) {
//					condFormatRules.add(condFormats);
//					continue;
//				}
//
//				cell = row.createCell(j);
//				cell.setCellStyle(CellStyle);
//				cell.setCellValue("${COL" + i + "}");
//				j++;
//
//				// SETTING WIDTH FOR COLUMNS ACCORDING TO THE DATATYPES, STRING : 33 ,ELSE 18
//				String datatypeName = "";
//				if (datatype instanceof DataType) {
//					DataType dt = (DataType) datatype;
//					datatypeName = dt.getName();
//				} else {
//					datatypeName = (String) datatype;
//				}
//
//				if (CollectionUtils.isNotEmpty(cfrules)) {
//					for (Object obj : cfrules) {
//						ConditionalFormat conditionalFormat = mapper.convertValue(obj, ConditionalFormat.class);
//						ClientConstraint cond = conditionalFormat.getCondition();
//						ClientSelection formatColumn = conditionalFormat.getFormatColumn();
//						if (clientSelection != null) {
//							if ((clientSelection.getLogicalColumnId() != null
//									&& clientSelection.getCategoryId().equals(formatColumn.getCategoryId())
//									&& clientSelection.getLogicalColumnId().equals(formatColumn.getLogicalColumnId()))
//									|| (clientSelection.getFormula() != null
//											&& clientSelection.getFormula().equals(formatColumn.getFormula()))) {
//
//								// POI allows only 3 conditional formats in single column.
//								if (condFormats.size() >= 3) {
//									break;
//								}
//
//								ConditionalFormattingRule rule = applyConditionalFormat(sheetCF, cond,
//										conditionalFormat.getProperties(), datatypeName, i, dataPosition);
//
//								if (rule != null) {
//									condFormats.add(rule);
//								}
//							}
//						}
//					}
//				}
//				condFormatRules.add(condFormats);
//
//				DataFormat dataformat = sWorkbook.createDataFormat();
//
//				String format = (String) clientSelection.getProperties().get("fmt");
//				if (format != null) {
//					format = format.replaceAll("%", "\\\\%");
//				}
//
//				if ("0".equals(format)) {
//					// this is used when format has 0 , 0 not a correct format for excel ;
//					formats[i] = null;
//				} else if ("DATE".equals(datatypeName) || "DATETIME".equals(datatypeName)) {
//					formats[i] = getExelDateFormat(format);
//				} else if ("DECIMAL".equals(datatypeName) || "INTEGER".equals(datatypeName)) {
//
//					// Adding brackets for negative values
//					if (clientSelection.getProperties().get("nnbr") != null) {
//						if ((boolean) clientSelection.getProperties().get("nnbr")) {
//							format = format + ";(" + format + ")";
//						}
//					}
//					formats[i] = format;
//				}
//
//				// SETTING ALIGNMENT FOR CELL
//				String alignment = "LEFT";
//				if (alignmenttype != null) {
//					if (alignmenttype instanceof Alignment) {
//						Alignment aln = (Alignment) alignmenttype;
//						alignment = aln.getCode();
//					} else {
//						alignment = (String) alignmenttype;
//					}
//				}
//
//				CellStyle dataCellStyle = null;
//				if (formats[i] != null || alignment != null) {
//					dataCellStyle = sWorkbook.createCellStyle();
//					if (alignment != null)
//						dataCellStyle.setAlignment(getAlignmentStyle(alignment));
//
//					if (formats[i] != null)
//						dataCellStyle.setDataFormat(dataformat.getFormat(formats[i]));
//				}
//
//				// Column Style with data format applying now
//				if (dataCellStyle != null)
//					cell.setCellStyle(dataCellStyle);
//
//				// Column Conditional Format applying now
//				if (CollectionUtils.isNotEmpty(condFormatRules.get(i))) {
//					try {
//						String colStringRange = CellReference.convertNumToColString(i);
//						colStringRange = "$" + colStringRange + "$" + (DataRow + 1) + ":$" + colStringRange + "$"
//								+ (DataRow + 1);
//						CellRangeAddress[] regions = { CellRangeAddress.valueOf(colStringRange) };
//						sheetCF.addConditionalFormatting(regions, condFormatRules.get(i)
//								.toArray(new ConditionalFormattingRule[condFormatRules.get(i).size()]));
//					} catch (Exception e) {
//					}
//				}
//
//				if ("STRING".equals(datatypeName)) {
//					sheet.setColumnWidth(i, 8500);
//				} else {
//					sheet.setColumnWidth(i, 4500);
//				}
//			}
//			Name data = sWorkbook.createName();
//			data.setNameName("_3." + KeyDataName);
//			data.setRefersToFormula(sheetOutput + "!$" + DataCellName + "$" + (DataRow + 1) + ":$" + lastColumnsName + "$"
//					+ (DataRow + 1));
//
//			// Autosize columns
//			// sheet.trackAllColumnsForAutoSizing();
//			// for (int i = 0; i < noOfColumns; i++) {
//			// sheet.autoSizeColumn(i);
//			// }
//
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			sWorkbook.write(outputStream);
//			return outputStream;
//
//		} catch (Exception e) {
//			throw new RuntimeException(
//					"Error when generating default yarg excel template for report. Error=> {}" ,e);
//		}
//	}
//
//	/**
//	 * Generate analysis template.
//	 *
//	 * @param defaultTemplateStream the default template stream
//	 * @param report                the report
//	 * @param isPivotMode           the is pivot mode
//	 * @return the byte array output stream
//	 */
//	public static ByteArrayOutputStream generateAnalysisTemplate(InputStream defaultTemplateStream, ClientReport report,
//			boolean isPivotMode) {
//		try {
//
//			// CHECKING DEFAULT TEMPLATE EXCEL FILE EXISTS OR NOT.
//			if (defaultTemplateStream == null) {
//				throw new RuntimeException("default template for excel doesn't exist");
//			}
//
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//			XSSFWorkbook template = buildRawDataSheet(defaultTemplateStream, report, 3, isPivotMode);
//
//			template.write(outputStream);
//			return outputStream;
//		} catch (Exception e) {
//			throw new RuntimeException(
//					"Error when generating default  excel template for report. Error=>  {}" ,e) ;
//		}
//	}
//
//	/**
//	 * Builds the raw data sheet.
//	 *
//	 * @param defaultTemplateStream the default template stream
//	 * @param report                the report
//	 * @param dataPosition          the data position
//	 * @param isPivotMode           the is pivot mode
//	 * @return the XSSF workbook
//	 */
//	private static XSSFWorkbook buildRawDataSheet(InputStream defaultTemplateStream, ClientReport report,
//			int dataPosition, boolean isPivotMode) {
//		try {
//			ClientQueryModel clientQueryModel = report.getClientQueryModel();
//
//			boolean isTabularAnalysis = false;
//			// CHEKING IF REPORT TYPE IS ANALYSIS TABULAR
//			if ("grid".equals(report.getReportType())) {
//				isTabularAnalysis = true;
//			}
//			
//			String KeyReportName = ReportHeader.getValue();
//			String KeyHeaderName = TableHeader.getValue();
//			String KeyDataName = ReportData.getValue();
//			String sheetRaw = SheetRawData.getValue();
//
//
//			Row row = null;
//			Cell cell = null;
//
//			// checking for clientquerymodel of pivot in datareport
//
//			// getting no of columns from clientquerymodel
//			int noOfColumns = clientQueryModel.getSelections().size();
//
//			// getting the cellreference details using noofcolumns
//			String lastColumnsName = CellReference.convertNumToColString(noOfColumns - 1);
//
//			XSSFWorkbook sWorkbook = new XSSFWorkbook(defaultTemplateStream);
//			XSSFSheet rawDataSheet = sWorkbook.getSheet((sheetRaw));
//
//			// Assigning Conditonal Format and DateFormat rules for columns
//			List<Object> cfrules = (List<Object>) report.getProperties().get("cfrules");
//			XSSFSheetConditionalFormatting sheetCF = rawDataSheet.getSheetConditionalFormatting();
//			ObjectMapper mapper = new ObjectMapper();
//			List<ArrayList<ConditionalFormattingRule>> condFormatRules = new ArrayList();
//
//			int rowCount = 0;
//			// get Row Positon , Cell Position and Cell Reference letter from sheet using
//			// default keys in templates
//			// get Reportname details.
//			int ReportNameRow = findRow(rawDataSheet, getKeyname(KeyReportName));
//			int ReportNameCellposition = findCell(rawDataSheet, getKeyname(KeyReportName));
//			String ReportNameCellName = CellReference
//					.convertNumToColString(findCell(rawDataSheet, getKeyname(KeyReportName)));
//
//			// get Header details.
//			int HeaderRow = findRow(rawDataSheet, getKeyname(KeyHeaderName));
//			int HeaderCellposition = findCell(rawDataSheet, getKeyname(KeyHeaderName));
//			String HeaderCellName = CellReference
//					.convertNumToColString(findCell(rawDataSheet, getKeyname(KeyHeaderName)));
//
//			// get Data details.
//			int DataRow = findRow(rawDataSheet, getKeyname(KeyDataName));
//			int DataCellposition = findCell(rawDataSheet, getKeyname(KeyDataName));
//			String DataCellName = CellReference.convertNumToColString(findCell(rawDataSheet, getKeyname(KeyDataName)));
//
////			CellStyle headerStyle = ExportExcel.getHeaderCellStyle(sWorkbook);
//
//			// Setting Reportname according to the template position.
//			row = rawDataSheet.getRow(ReportNameRow);
//			cell = row.getCell(ReportNameCellposition);
//			cell.setCellValue(report.getName());
////			cell.setCellStyle(headerStyle);
//			CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
//			CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
//
//			// Now merging the cell when columns are more than 3
//			if (noOfColumns > 1) {
//				// Merges the cells
//				CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, ReportNameCellposition,
//						noOfColumns - 1);
//				rawDataSheet.addMergedRegion(cellRangeAddress);
//				CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
//			}
//
//			// Setting name range formulae for Report Name row
//			Name reportname = sWorkbook.createName();
//			reportname.setNameName("_1." + KeyReportName);
//			reportname.setRefersToFormula(SheetRawData.getValue() + "!$" + ReportNameCellName + "$" + (ReportNameRow + 1) + ":$"
//					+ lastColumnsName + "$" + (ReportNameRow + 1));
//
//			// setting header name in the row
//			boolean[] hideColumns = new boolean[noOfColumns];
//			String[] formats = new String[noOfColumns];
//
//			// getting template cell style and cloning it
//			CellStyle CellStyle = sWorkbook.createCellStyle();
//			row = rawDataSheet.getRow(HeaderRow);
//			cell = row.getCell(HeaderCellposition);
//			CellStyle.cloneStyleFrom(cell.getCellStyle());
//
//			// Setting header names here
//			List<String> colNames = new ArrayList<>();
//			int i = 0;
//			int j = 0;
//			for (ClientSelection clientSelection : clientQueryModel.getSelections()) {
//				if (clientSelection != null) {
//					if (clientSelection.getProperties() != null && clientSelection.getProperties().get("hdn") != null) {
//						hideColumns[i] = (Boolean) clientSelection.getProperties().get("hdn");
//
//					}
//					if (hideColumns[i] == true) {
//						i++;
//						continue;
//					} else
//						hideColumns[i] = false;
//					cell = row.createCell(j);
//					cell.setCellStyle(CellStyle);
//					String columnHeader = colNames.contains((String) clientSelection.getName())
//							? (String) clientSelection.getName() + " "
//							: (String) clientSelection.getName();
//					cell.setCellValue(columnHeader);
//					colNames.add((String) clientSelection.getName());
//					i++;
//					j++;
//				}
//			}
//
//			// Setting name range formulae for header row
//			Name header = sWorkbook.createName();
//			header.setNameName("_2." + KeyHeaderName);
//			header.setRefersToFormula(SheetRawData.getValue() + "!$" + HeaderCellName + "$" + (HeaderRow + 1) + ":$"
//					+ lastColumnsName + "$" + (HeaderRow + 1));
//
//			// DATA ROW
//			// getting template cell style and cloning it
//			CellStyle = sWorkbook.createCellStyle();
//			row = rawDataSheet.getRow(DataRow);
//			cell = row.getCell(DataCellposition);
//			CellStyle.cloneStyleFrom(cell.getCellStyle());
//
//			i = 0;
//			j = 0;
//			for (ClientSelection clientSelection : clientQueryModel.getSelections()) {
//				Object datatype = clientSelection.getProperties() != null
//						? clientSelection.getProperties().get("datatype")
//						: "STRING";
//				Object alignmenttype = clientSelection.getProperties().get("aln");
//				// Object tableColumn = clientSelection.getProperties().get("tc");
//				ArrayList<ConditionalFormattingRule> condFormats = new ArrayList<ConditionalFormattingRule>();
//
//				if (hideColumns[i]) {
//					condFormatRules.add(null);
//					i++;
//					continue;
//				}
//
//				cell = row.createCell(j);
//				cell.setCellStyle(CellStyle);
//				cell.setCellValue("${COL" + i + "}");// "${COL" + i + "}");
//				j++;
//				// SETTING WIDTH FOR COLUMNS ACCORDING TO THE DATATYPES, STRING : 33 ,ELSE 18
//				String datatypeName = "";
//				if (datatype instanceof DataType) {
//					DataType dt = (DataType) datatype;
//					datatypeName = dt.getName();
//				} else {
//					datatypeName = (String) datatype;
//				}
//
//				// HERE CONDITIONAL FORMATS ARE APPLIED ONLY WHEN REPORT TYPE IS ANALYSIS
//				// TABULAR
//				if (isTabularAnalysis) {
//					// CONDITINAL FORMATS
//
//					if (CollectionUtils.isNotEmpty(cfrules)) {
//						for (Object obj : cfrules) {
//							ConditionalFormat conditionalFormat = mapper.convertValue(obj, ConditionalFormat.class);
//							ClientConstraint cond = conditionalFormat.getCondition();
//							ClientSelection formatColumn = conditionalFormat.getFormatColumn();
//							if (clientSelection != null) {
//								if ((clientSelection.getLogicalColumnId() != null
//										&& clientSelection.getCategoryId().equals(formatColumn.getCategoryId())
//										&& clientSelection.getLogicalColumnId()
//												.equals(formatColumn.getLogicalColumnId()))
//										|| (clientSelection.getFormula() != null
//												&& clientSelection.getFormula().equals(formatColumn.getFormula()))) {
//
//									// POI allows only 3 conditional formats in single column.
//									if (condFormats.size() >= 3) {
//										break;
//									}
//
//									ConditionalFormattingRule rule = applyConditionalFormat(sheetCF, cond,
//											conditionalFormat.getProperties(), datatypeName, i, dataPosition);
//
//									if (rule != null) {
//										condFormats.add(rule);
//									}
//								}
//							}
//						}
//					}
//					condFormatRules.add(condFormats);
//				}
//
//				DataFormat dataformat = sWorkbook.createDataFormat();
//
//				String format = (String) clientSelection.getProperties().get("fmt");
//				if (format != null) {
//					format = format.replaceAll("%", "\\\\%");
//				}
//				if ("0".equals(format)) {
//					// this is used when format has 0 , 0 not a correct format for excel ;
//					formats[i] = null;
//				} else if ("DATE".equals(datatypeName) || "DATETIME".equals(datatypeName)) {
//					formats[i] = getExelDateFormat(format);
//				} else if ("DECIMAL".equals(datatypeName) || "INTEGER".equals(datatypeName)) {
//
//					// Adding brackets for negative values
//					if (clientSelection.getProperties().get("nnbr") != null) {
//						if ((boolean) clientSelection.getProperties().get("nnbr")) {
//							format = format + ";(" + format + ")";
//						}
//					}
//					formats[i] = format;
//				}
//
//				// SETTING ALIGNMENT FOR CELL
//				String alignment = "LEFT";
//				if (alignmenttype != null) {
//					if (alignmenttype instanceof Alignment) {
//						Alignment aln = (Alignment) alignmenttype;
//						alignment = aln.getCode();
//					} else {
//						alignment = (String) alignmenttype;
//					}
//				}
//				CellStyle dataCellStyle = null;
//				if (formats[i] != null || alignment != null) {
//					dataCellStyle = sWorkbook.createCellStyle();
//					if (alignment != null)
//						dataCellStyle.setAlignment(getAlignmentStyle(alignment));
//
//					if (formats[i] != null)
//						dataCellStyle.setDataFormat(dataformat.getFormat(formats[i]));
//				}
//
//				// Column Style with data format applying now
//				if (dataCellStyle != null)
//					cell.setCellStyle(dataCellStyle);
//
//				// HERE CONDITIONAL FORMATS ARE APPLIED ONLY WHEN REPORT TYPE IS ANALYSIS
//				// TABULAR
//				if (isTabularAnalysis) {
//					// Column Conditional Format applying now
//					if (CollectionUtils.isNotEmpty(condFormatRules.get(i))) {
//						try {
//							String colStringRange = CellReference.convertNumToColString(i);
//							colStringRange = "$" + colStringRange + "$" + (DataRow + 1) + ":$" + colStringRange + "$"
//									+ (DataRow + 1);
//							CellRangeAddress[] regions = { CellRangeAddress.valueOf(colStringRange) };
//							sheetCF.addConditionalFormatting(regions, condFormatRules.get(i)
//									.toArray(new ConditionalFormattingRule[condFormatRules.get(i).size()]));
//						} catch (Exception e) {
//						}
//					}
//				}
//
//				i++;
//			}
//			Name data = sWorkbook.createName();
//			data.setNameName("_3." + KeyDataName);
//			data.setRefersToFormula(SheetRawData.getValue() + "!$" + DataCellName + "$" + (DataRow + 1) + ":$" + lastColumnsName
//					+ "$" + (DataRow + 1));
//
//			// Autosize columns
//			// sheet.trackAllColumnsForAutoSizing();
//			// for (int i = 0; i < noOfColumns; i++) {
//			// sheet.autoSizeColumn(i);
//			// }
//
//			// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			// sWorkbook.write(outputStream);
//
//			if ("grid".equals(report.getReportType())) {
//
//				// removing Default ReportOutput Sheet
//				sWorkbook.removeSheetAt(sWorkbook.getSheetIndex(SheetOutput.getValue()));
//				// renamed RawData Sheet as ReportOutput sheet
//				sWorkbook.setSheetName(sWorkbook.getSheetIndex(rawDataSheet), SheetOutput.getValue());
//
//			} else {
//				sWorkbook = buildPivotSheet(sWorkbook, report, lastColumnsName, isPivotMode);
//			}
//
//			return sWorkbook;
//		} catch (Exception e) {
//			throw new RuntimeException(
//					"Error when generating default  excel template for report. Error=> {}" ,e);
//		}
//	}
//
//	/**
//	 * Builds the pivot sheet.
//	 *
//	 * @param template    the template
//	 * @param report      the report
//	 * @param ColumnName  the column name
//	 * @param isPivotMode the is pivot mode
//	 * @return the XSSF workbook
//	 */
//	private static XSSFWorkbook buildPivotSheet(XSSFWorkbook template, ClientReport report, String ColumnName,
//			boolean isPivotMode) {
//		try {
//			if (template == null) {
//				return null;
//			}
//			XSSFSheet rawdata = template.getSheet(SheetRawData.getValue());
//			XSSFSheet sheet = template.getSheet(SheetOutput.getValue());
//			ClientQueryModel clientQueryModel = report.getClientQueryModel();
//
//			// getting the cellreference details using noofcolumns
//			/* Get the reference for Pivot Data */
//			AreaReference areaRefer = new AreaReference("$A$2:$" + ColumnName + "$3", SpreadsheetVersion.EXCEL2007);
//			/* Find out where the Pivot Table needs to be placed */
//			CellReference pivotPlace = new CellReference("A1");
//			int lastColIndex = areaRefer.getLastCell().getCol() - areaRefer.getFirstCell().getCol();
//
//			XSSFPivotTable pivotTable = sheet.createPivotTable(areaRefer, pivotPlace, rawdata);
//
//			boolean isOper = report.getReportType().equals("oper");
//			boolean isFreezePanelEnabled=false;
//			
//			if (clientQueryModel.getProperties() != null
//					&& clientQueryModel.getProperties().containsKey("widgetProperties")) {
//				Map<String, Object> wProp = (Map<String, Object>) clientQueryModel.getProperties()
//						.get("widgetProperties");
//				if (wProp != null && wProp.containsKey("freezRows") && wProp.get("freezRows")!=null) {
//					isFreezePanelEnabled = (boolean) wProp.get("freezRows");
//				}
//			}
//			
//			List<ClientSelection> rows = new ArrayList<>();
//			List<ClientSelection> cols = new ArrayList<>();
//			List<ClientSelection> vals = new ArrayList<>();
//			
//
//			HashMap<Long,Integer> rowIndxes = new HashMap<>();
//			HashMap<Long,Integer> colIndxes = new HashMap<>();
//			HashMap<Long,Integer> valIndxes = new HashMap<>();
//
//
//			int index = 0;
//			for (ClientSelection clientSelection : clientQueryModel.getSelections()) {
//				Map<String, Object> csProp = (Map<String, Object>) clientSelection.getProperties();
//				// Note : ORB-2815 -pivot configuration .
//				String dimension = null;
//				if (isOper) {
//					dimension = (String) csProp.get("pivot_dimension");
//				} else {
//					dimension = clientSelection.getDimension();
//				}
//				
//				if (dimension != null) {
//					if ("ROW".equals(dimension)) {
//						rows.add(clientSelection);
//						rowIndxes.put(clientSelection.getLogicalColumnId(),index);
//					}  else if ("COL".equals(dimension)) {
//						cols.add(clientSelection);
//						colIndxes.put(clientSelection.getLogicalColumnId(),index);
//					}else if ("VAL".equals(dimension) || "VALUE".equals(dimension)) {
//						vals.add(clientSelection);
//						valIndxes.put(clientSelection.getLogicalColumnId(),index);
//					}
//				}
//				index++;
//				
//			}
//
//			if(isOper) {
//				Collections.sort(rows, new SortingPivotlabels());
//				Collections.sort(cols, new SortingPivotlabels());
//				Collections.sort(vals, new SortingPivotlabels());
//			}
//
//			insertPivotLabels(pivotTable,rows,lastColIndex,isOper,rowIndxes);
//			insertPivotLabels(pivotTable,cols,lastColIndex,isOper,colIndxes);
//			insertPivotLabels(pivotTable,vals,lastColIndex,isOper,valIndxes);
//			
//			
//			if(isFreezePanelEnabled) {
//				sheet.createFreezePane(rows.size(), cols.size()+2);
//			}
//
//		} catch (Exception e) {
//			throw new RuntimeException(
//					"Error when generating default  excel pivot template for report. Error=> {}" ,e);
//		}
//
//		return template;
//	}
//	
//	private static void insertPivotLabels(XSSFPivotTable pivotTable, List<ClientSelection> clientSelections, int lastColIndex,
//			boolean isOper, HashMap<Long,Integer> indexRef) {
//		for (int i = 0; i < clientSelections.size(); i++) {
//			ClientSelection clientSelection = clientSelections.get(i);
//			int position = indexRef.get(clientSelection.getLogicalColumnId());
//			Map<String, Object> csProp = (Map<String, Object>) clientSelection.getProperties();
//			// Note : ORB-2815 -pivot configuration .
//			String aggr = null;
//			String dimension = null;
//			if (isOper) {
//				aggr = (String) csProp.get("pivot_aggregation");
//				dimension = (String) csProp.get("pivot_dimension");
//			} else {
//				aggr = clientSelection.getAggregation();
//				if (aggr==null || "NONE".equals(aggr)) {
//					Object aggregation =csProp.get("aggregation");
//					if (aggregation instanceof AggregationType) {
//						aggr = ((AggregationType) aggregation).getCode();
//					} else {
//						aggr = (String) aggregation;
//					}
//				}
//				dimension = clientSelection.getDimension();
//			}
//
//			if(aggr==null) {
//				aggr="SUM";
//				logger.error("Getting aggregation as NULL , so Setting default SUM for Column {}",clientSelection.getName());
//			}
//			if (dimension != null) {
//				if ("ROW".equals(dimension)) {
//					pivotTable.addRowLabel(position);
//				} else if ("VAL".equals(dimension) || "VALUE".equals(dimension)) {
//					String format = (String) clientSelection.getProperties().get("fmt");
//					if (format == null) {
//						pivotTable.addColumnLabel(getDimensions(aggr), position, clientSelection.getName());
//					} else {
//						format = format.replaceAll("%", "\\\\%");
//						pivotTable.addColumnLabel(getDimensions(aggr), position, clientSelection.getName(), format);
//					}
//				} else if ("COL".equals(dimension)) {
//					addColumLabels(pivotTable, position, lastColIndex);
//				}
//			}
//		}
//		
//		
//	}
//	private static class SortingPivotlabels implements Comparator<ClientSelection> {
//		@Override
//		  public int compare(ClientSelection s1, ClientSelection s2) {
//			Map<String, Object> csProp1 = (Map<String, Object>) s1.getProperties();
//			Map<String, Object> csProp2 = (Map<String, Object>) s2.getProperties();
//
//			int sequence1 = (int) csProp1.get("pivot_sequence");
//			int	sequence2 =  (int) csProp2.get("pivot_sequence");
//			if (sequence1 == sequence2)
//				return 0;
//			else if (sequence1> sequence2)
//				return 1;
//			else
//				return -1;
//		  }
//		}
//		
//
//	/**
//	 * Adds the colum labels.
//	 *
//	 * @param pivotTable   the pivot table
//	 * @param columnIndex  the column index
//	 * @param lastColIndex the last col index
//	 */
//	public static void addColumLabels(XSSFPivotTable pivotTable, int columnIndex, int lastColIndex) {
//		if (columnIndex > lastColIndex && columnIndex < 0) {
//			throw new IndexOutOfBoundsException();
//		}
//
//		CTPivotFields pivotFields = pivotTable.getCTPivotTableDefinition().getPivotFields();
//
//		CTPivotField pivotField = CTPivotField.Factory.newInstance();
//		CTItems items = pivotField.addNewItems();
//
//		pivotField.setAxis(STAxis.AXIS_COL);
//		pivotField.setShowAll(false);
//		for (int i = 0; i <= lastColIndex; i++) {
//			items.addNewItem().setT(STItemType.DEFAULT);
//		}
//		items.setCount(items.sizeOfItemArray());
//		pivotFields.setPivotFieldArray(columnIndex, pivotField);
//
//		// colfield should be added for the second one.
//		CTColFields colFields;
//		if (pivotTable.getCTPivotTableDefinition().getColFields() != null) {
//			colFields = pivotTable.getCTPivotTableDefinition().getColFields();
//		} else {
//			colFields = pivotTable.getCTPivotTableDefinition().addNewColFields();
//		}
//		colFields.addNewField().setX(columnIndex);
//		colFields.setCount(colFields.sizeOfFieldArray());
//	}
//
//	/**
//	 * Gets the alignment style.
//	 *
//	 * @param alignment the alignment
//	 * @return the alignment style
//	 */
//	static HorizontalAlignment getAlignmentStyle(String alignment) {
//
//		if ("LEFT".equals(alignment.toUpperCase())) {
//			return HorizontalAlignment.LEFT;
//		} else if ("CENTER".equals(alignment.toUpperCase())) {
//			return HorizontalAlignment.CENTER;
//		} else if ("RIGHT".equals(alignment.toUpperCase())) {
//			return HorizontalAlignment.RIGHT;
//		}
//		return HorizontalAlignment.LEFT;
//	}
//
//	/**
//	 * Find row.
//	 *
//	 * @param sheet       the sheet
//	 * @param cellContent the cell content
//	 * @return the int
//	 */
//	private static int findRow(XSSFSheet sheet, String cellContent) {
//		for (Row row : sheet) {
//			for (Cell cell : row) {
//				if (cell.getCellType() == CellType.STRING) {
//					if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
//						return row.getRowNum();
//					}
//				}
//			}
//		}
//		return 0;
//	}
//
//	/**
//	 * Find cell.
//	 *
//	 * @param sheet       the sheet
//	 * @param cellContent the cell content
//	 * @return the int
//	 */
//	private static int findCell(XSSFSheet sheet, String cellContent) {
//		for (Row row : sheet) {
//			for (Cell cell : row) {
//				if (cell.getCellType() == CellType.STRING) {
//					if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
//						return cell.getColumnIndex();
//					}
//				}
//			}
//		}
//		return 0;
//	}
//
//	/**
//	 * Gets the keyname.
//	 *
//	 * @param name the name
//	 * @return the keyname
//	 */
//	private static String getKeyname(String name) {
//		return "${" + name + "}";
//	}
//
//	/**
//	 * Gets the param object value.
//	 *
//	 * @param value        the value
//	 * @param datatypeName the datatype name
//	 * @param userInfo     the user info
//	 * @return the param object value
//	 */
	public static String getParamObjectValue(Object value, String datatypeName, String userDateFormat) {
		if ("DATE".equals(datatypeName) || "DATETIME".equals(datatypeName)) {
			try {
				if (userDateFormat != null) {
					Logs.debug("User Format for Date : {}"+ userDateFormat);
				}

				if (userDateFormat == null || StringUtils.isEmpty(userDateFormat)) {
					userDateFormat = "MM/dd/yyyy";
					Logs.debug("Default Format for Date : {}"+ userDateFormat);
				}
				DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
				return new SimpleDateFormat(userDateFormat).format(serverDateFormat.parse(String.valueOf(value)));
			} catch (ParseException e) {
				return String.valueOf(value);
			}
		} else {
			return String.valueOf(value);
		}
	}
//
//	/**
//	 * Populate operators.
//	 */
//	private static void populateOperators() {
//		operators.put("between", 1);
//		operators.put("not between", 2);
//		operators.put("=", 3);
//		operators.put("==", 3);
//		operators.put("<>", 4);
//		operators.put(">", 5);
//		operators.put("<", 6);
//		operators.put(">=", 7);
//		operators.put("<=", 8);
//		operators.put("!=", 4);
//	}
//
//	/**
//	 * Gets the date formats.
//	 *
//	 * @return the date formats
//	 */
//	public static List<String> getDateFormats() {
//		List<String> customdateformat = new ArrayList<>();
//		customdateformat.add("MM/dd/yyyy");
//		customdateformat.add("dd/MM/yyyy");
//		customdateformat.add("MM-dd-yyyy");
//		customdateformat.add("yyyy/MM/dd");
//		customdateformat.add("yyyy-MM-dd");
//		customdateformat.add("yyyy-MMM-dd");
//		customdateformat.add("dd-MM-yyyy");
//		customdateformat.add("dd-MMM-YYYY");
//		customdateformat.add("dd-MMM-yyyy");
//		customdateformat.add("MMM-dd-YYYY");
//		customdateformat.add("MMM-dd-yyyy");
//		customdateformat.add("dd-MMMMM-YYYY");
//		customdateformat.add("dd-MMMMM-yyyy");
//		customdateformat.add("dd/MM/yyyy hh:mm:ss");
//		customdateformat.add("yyyy-MM-dd hh:mm:ss");
//		customdateformat.add("yyyy-MM-dd hh:mm:ssZ");
//		customdateformat.add("yyyy-MM-dd HH:mm:ssZ");
//		customdateformat.add("yyyy-MM-dd HH:mm:ss.SSS");
//		customdateformat.add("yyyy-MM-dd hh:mm:ss.SSS");
//		customdateformat.add("yyyy-MM-dd HH:mm:ss.SSSZ");
//		customdateformat.add("yyyy-MM-dd hh:mm:ss a");
//		customdateformat.add("yyyy-MM-dd HH:mm:ss.SSS a");
//		customdateformat.add("yyyy-MM-dd HH:mm:ss");
//		customdateformat.add("MMMMM yyyy HH:mm:ss.SSS");
//		customdateformat.add("MMMMM dd yyyy HH:mm:ss.SSS");
//		customdateformat.add("EEEEE MMMMM yyyy HH:mm:ss.SSS");
//		customdateformat.add("EEEEE MMMMM dd yyyy HH:mm:ss.SSS");
//		customdateformat.add("EEEEE MMMMM dd yyyy HH:mm:ss.SSSZ");
//		customdateformat.add("MMMMM yyyy hh:mm:ss.SSS a");
//		customdateformat.add("MMMMM dd yyyy hh:mm:ss.SSS a");
//		customdateformat.add("EEEEE MMMMM yyyy hh:mm:ss.SSS a");
//		customdateformat.add("EEEEE MMMMM dd yyyy hh:mm:ss.SSS a");
//		customdateformat.add("EEE MMM dd yyyy HH:mm:ss.SSS");
//
//		return customdateformat;
//	}
//
//	/**
//	 * Checks if is valid format.
//	 *
//	 * @param format the format
//	 * @param value  the value
//	 * @return true, if is valid format
//	 */
//	public static boolean isValidFormat(String format, String value) {
//		Date date = null;
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		try {
//			date = sdf.parse(value);
//			if (!value.equals(sdf.format(date))) {
//				date = null;
//			}
//		} catch (java.text.ParseException e) {
////			
//		}
//		return date != null;
//	}
//
//	/**
//	 * Gets the exel date format.
//	 *
//	 * @param format the format
//	 * @return the exel date format
//	 */
//	public static String getExelDateFormat(String format) {
//		if (StringUtils.isBlank(format))
//			return "mm/dd/yyyy";
//
//		format = format.replaceAll("M", "m");
//		format = format.replaceAll("H", "h");
//		format = format.replaceAll("E", "d");
//		format = format.replaceAll("S", "0");
//		format = format.replaceAll("a", "AM/PM");
//		format = format.replaceAll("Z", "");
//		return format;
//	}
//
//	/**
//	 * Apply conditional format.
//	 *
//	 * @param sheetCF        the sheet CF
//	 * @param cond           the cond
//	 * @param properties     the properties
//	 * @param dataType       the data type
//	 * @param ColumnPosition the column position
//	 * @param dataPosition   the data position
//	 * @return the conditional formatting rule
//	 */
//	static ConditionalFormattingRule applyConditionalFormat(SheetConditionalFormatting sheetCF, ClientConstraint cond,
//			Map<String, Object> properties, String dataType, int ColumnPosition, int dataPosition) {
//		// logger.debug("Creating conditional formula=>Operator{},dataType=>{}",
//		// cond.getOperator(), dataType);
//		if (operators.size() == 0)
//			populateOperators();
//		ConditionalFormattingRule rule = null;
//		String cellName = CellReference.convertNumToColString(ColumnPosition);
//		DateFormat paramDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//		if (cond.getOperator().indexOf("between") >= 0) {
//			// Added Conditional Format rules to check In Between Dates
//			if ("DATE".equals(dataType) || "DATETIME".equals(dataType)) {
//				try {
//					// logger.debug("Date value=>{}", cond.getValues());
//					if (cond.getValues() != null && cond.getValues().size() == 2) {
//						Date dd1 = paramDateFormat.parse((String) cond.getValues().get(0));
//						Date dd2 = paramDateFormat.parse((String) cond.getValues().get(1));
//
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(dd1);
//
//						if (cond.getOperator().indexOf("not ") != -1) {
//							String formula = "=AND(" + cellName + "" + dataPosition + "<=DATE(" + cal.get(Calendar.YEAR)
//									+ "," + (cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DAY_OF_MONTH) + "),";
//
//							cal.setTime(dd2);
//							formula = formula + cellName + "" + dataPosition + ">=" + "DATE(" + cal.get(Calendar.YEAR)
//									+ "," + (cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DAY_OF_MONTH) + "))";
//							rule = sheetCF.createConditionalFormattingRule(formula);
//						} else {
//							String formula = "=AND(" + cellName + "" + dataPosition + ">=DATE(" + cal.get(Calendar.YEAR)
//									+ "," + (cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DAY_OF_MONTH) + "),";
//
//							cal.setTime(dd2);
//							formula = formula + cellName + "" + dataPosition + "<=" + "DATE(" + cal.get(Calendar.YEAR)
//									+ "," + (cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DAY_OF_MONTH) + "))";
//							rule = sheetCF.createConditionalFormattingRule(formula);
//						}
//					}
//				} catch (ParseException e) {
//					ErrorThrow("Error when formatting date in conditional format" + e);
//				}
//			} else
//				rule = sheetCF.createConditionalFormattingRule(
//						((Integer) operators.get(cond.getOperator())).byteValue(), "" + cond.getValues().get(0),
//						"" + cond.getValues().get(1));
//		} else if (cond.getOperator().indexOf("beginswith") != -1 || cond.getOperator().indexOf("endswith") != -1
//				|| cond.getOperator().indexOf("contains") != -1) {
//			String valueString = ">";
//			if (cond.getOperator().indexOf("not ") != -1)
//				valueString = "=";
//			String formulaString = "";
//			if (cond.getOperator().indexOf("beginswith") != -1) {
//				formulaString = "\"" + cond.getValue() + "*\")" + valueString + "0";
//			} else if (cond.getOperator().indexOf("endswith") != -1) {
//				formulaString = "\"*" + cond.getValue() + "\")" + valueString + "0";
//			} else if (cond.getOperator().indexOf("contains") != -1) {
//				if (cond.getIgnoreCase())
//					formulaString = "=IF(IFERROR(SEARCH(\"" + cond.getValue() + "\"," + cellName + "" + dataPosition
//							+ "),0)" + valueString + "0,TRUE,FALSE)";
//				else
//					formulaString = "=IF(IFERROR(FIND(\"" + cond.getValue() + "\"," + cellName + "" + dataPosition
//							+ "),0)" + valueString + "0,TRUE,FALSE)";
//			}
//			rule = sheetCF.createConditionalFormattingRule(formulaString.startsWith("=IF") ? formulaString
//					: "=COUNTIF(" + cellName + "" + dataPosition + "," + formulaString);
//		} else if ("is empty".equals(cond.getOperator())) {
//			rule = sheetCF.createConditionalFormattingRule("=LEN(" + cellName + "" + dataPosition + ")=0");
//		} else if ("is not empty".equals(cond.getOperator())) {
//			rule = sheetCF.createConditionalFormattingRule("=LEN(" + cellName + "" + dataPosition + ")<>0");
//		} else {
//			if (!operators.containsKey(cond.getOperator()))
//				return null;
//
//			if ("STRING".equals(dataType))
//				rule = sheetCF.createConditionalFormattingRule(
//						((Integer) operators.get(cond.getOperator())).byteValue(), "\"" + cond.getValue() + "\"");
//			else if ("DATE".equals(dataType) || "DATETIME".equals(dataType)) {
//				try {
//					// logger.debug("Date value=>{}", cond.getValue());
//					if (cond.getValue() != null) {
//						Date dd = paramDateFormat.parse((String) cond.getValue());
//						// logger.debug("Date=>{}", dd);
//						String oper = cond.getOperator();
//						if ("==".equals(oper))
//							oper = "=";
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(dd);
//						rule = sheetCF.createConditionalFormattingRule(
//								"=" + cellName + "" + dataPosition + "" + oper + "DATE(" + cal.get(Calendar.YEAR) + ","
//										+ (cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")");
//					}
//				} catch (ParseException e) {
//					ErrorThrow("Error when formatting date in conditional format" + e);
//				}
//			} else
//				rule = sheetCF.createConditionalFormattingRule(
//						((Integer) operators.get(cond.getOperator())).byteValue(), "" + cond.getValue());
//		}
//
//		PatternFormatting fill = rule.createPatternFormatting();
//		FontFormatting font = rule.createFontFormatting();
//		try {
//			if (properties != null && properties.get("bgclr") != null)
//				fill.setFillBackgroundColor(new XSSFColor(Color.decode("#" + properties.get("bgclr"))));
//		} catch (NumberFormatException e4) {
//			ErrorThrow("Error wheng creating conditional format for bgcolor" + e4);
//		}
//
//		try {
//			if (properties != null && properties.get("font") != null
//					&& ((HashMap) properties.get("font")).get("colorHexcode") != null) {
//				String fontColor = "#" + ((HashMap) properties.get("font")).get("colorHexcode");
//				String bgColor = "#" + ((HashMap) properties.get("font")).get("colorHexcode");
//				// logger.debug("hexColor=>{}", hexColor);
//				// int[] rgb = getRGB(hexColor);
//				// XSSFColor color = new XSSFColor(new java.awt.Color(rgb[0], rgb[1], rgb[2]));
//				// //accepts a short value
//				// fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
//				if (!"#ffffff".equals(bgColor.toLowerCase()))
//					// fill.setFillForegroundColor(new XSSFColor(HexToColor(bgColor)));
//					font.setFontColorIndex((short) 1);
//				font.setFontColor(HexToRGB(fontColor));
//				// font.setFontColor(new XSSFColor(HexToColor(hexColor)));
//				// font.setFontColorIndex(new XSSFColor(HexToColor(hexColor)).getIndexed());
//			}
//		} catch (NumberFormatException e3) {
//			ErrorThrow("Error wheng creating conditional format for font color" + e3);
//		}
//		try {
//			if (properties != null && properties.get("font") != null
//					&& ((HashMap) properties.get("font")).get("bold") != null) {
//				Boolean italic = (Boolean) ((HashMap) properties.get("font")).get("italic");
//				// logger.debug("italic=>{}", italic);
//				Boolean bold = (Boolean) ((HashMap) properties.get("font")).get("bold");
//				// logger.debug("bold=>{}", bold);
//				font.setFontStyle(italic != null ? italic : false, bold != null ? bold : false);
//			}
//		} catch (Exception e2) {
//			ErrorThrow("Error wheng creating conditional format for font style" + e2);
//		}
//		try {
//			if (properties != null && properties.get("font") != null
//					&& ((HashMap) properties.get("font")).get("italic") != null) {
//				Boolean italic = (Boolean) ((HashMap) properties.get("font")).get("italic");
//				// logger.debug("italic=>{}", italic);
//				Boolean bold = (Boolean) ((HashMap) properties.get("font")).get("bold");
//				// logger.debug("bold=>{}", bold);
//				font.setFontStyle(italic != null ? italic : false, bold != null ? bold : false);
//			}
//		} catch (Exception e2) {
//			ErrorThrow("Error wheng creating conditional format for font style" + e2);
//		}
//		try {
//			if (properties != null && properties.get("font") != null
//					&& ((HashMap) properties.get("font")).get("underline") != null) {
//				Boolean underline = (Boolean) ((HashMap) properties.get("font")).get("underline");
//				// logger.debug("underline=>{}", underline);
//				if (underline != null && underline == true)
//					font.setUnderlineType(Font.U_SINGLE);
//			}
//		} catch (Exception e1) {
//			ErrorThrow("Error wheng creating conditional format for font underline style" + e1);
//		}
//
//		try {
//			if (properties != null && properties.get("font") != null
//					&& StringUtils.isNotBlank((String) ((HashMap) properties.get("font")).get("size"))) {
//				int size = Integer.parseInt((String) ((HashMap) properties.get("font")).get("size"));
//				// logger.debug("size=>{}", size);
//				font.setFontHeight(size * 20);
//			}
//		} catch (NumberFormatException e) {
//			ErrorThrow("Error wheng creating conditional format font size" + e);
//		}
//
//		return rule;
//	}
//
//	/**
//	 * Error throw.
//	 *
//	 * @param e the e
//	 */
//	private static void ErrorThrow(String e) {
//		throw new RuntimeException(e);
//
//	}
//
//	/**
//	 * Converts a hex string to a color. If it can't be converted null is returned.
//	 *
//	 * @param colorStr the color str
//	 * @return Color
//	 */
//	public static XSSFColor HexToRGB(String colorStr) {
//		int red = Integer.valueOf(colorStr.substring(1, 3), 16);
//		int blue = Integer.valueOf(colorStr.substring(3, 5), 16);
//		int green = Integer.valueOf(colorStr.substring(5, 7), 16);
//		return new XSSFColor(new java.awt.Color(red, blue, green));
//	}
//
//	/**
//	 * Hex to color.
//	 *
//	 * @param hex the hex
//	 * @return the color
//	 */
//	private static Color HexToColor(String hex) {
//		hex = hex.replace("#", "");
//		switch (hex.length()) {
//		case 6:
//			return new Color(Integer.valueOf(hex.substring(0, 2), 16), Integer.valueOf(hex.substring(2, 4), 16),
//					Integer.valueOf(hex.substring(4, 6), 16));
//		case 8:
//			return new Color(Integer.valueOf(hex.substring(0, 2), 16), Integer.valueOf(hex.substring(2, 4), 16),
//					Integer.valueOf(hex.substring(4, 6), 16), Integer.valueOf(hex.substring(6, 8), 16));
//		}
//		return null;
//	}
//
//	/**
//	 * Detect templateis yarg.
//	 *
//	 * @param xlsmTemp the xlsm temp
//	 * @return true, if successful
//	 * @throws EncryptedDocumentException the encrypted document exception
//	 * @throws InvalidFormatException     the invalid format exception
//	 * @throws IOException                Signals that an I/O exception has
//	 *                                    occurred.
//	 */
//	public static boolean DetectTemplateisYarg(Template xlsmTemp)
//			throws EncryptedDocumentException, InvalidFormatException, IOException {
//		try (InputStream template = new ByteArrayInputStream(xlsmTemp.getFileContents());
//				Workbook wb = WorkbookFactory.create(template);){
//			for (int i = 0; i < wb.getNumberOfNames(); i++) {
//				Name name = wb.getNameAt(i);
//				AreaReference aref = new AreaReference(name.getRefersToFormula(), SpreadsheetVersion.EXCEL2007);
//				CellReference[] crefs = aref.getAllReferencedCells();
//				for (int j = 0; j < crefs.length; j++) {
//					Sheet s = wb.getSheet(crefs[j].getSheetName());
//					Row r = s.getRow(crefs[j].getRow());
//					Cell c = r.getCell(crefs[j].getCol());
//					// extract the cell contents based on cell type etc.
//					String bandName = String.valueOf(getCellValueAsString(c));
//					Matcher defaultMatcher = DEFAULT_NAME_RANGE_PATTERN.matcher(bandName);
//					Matcher customMatcher = ALIAS_NAME_RANGE_PATTERN.matcher(bandName);
//					if (defaultMatcher.find()) {
//						return true;
//					} else if (customMatcher.find()) {
//						return true;
//					}
//				}
//
//			}
//		} catch(Exception ex) {
//			logger.error(ex.toString());
//		}
//		return false;
//
//	}
//
//	/** The Constant DEFAULT_NAME_RANGE_PATTERN. */
//	public static final Pattern DEFAULT_NAME_RANGE_PATTERN = Pattern.compile("\\$\\{([A-z0-9_]+?)\\}");
//
//	/** The Constant ALIAS_NAME_RANGE_PATTERN. */
//	public static final Pattern ALIAS_NAME_RANGE_PATTERN = Pattern.compile("(\\_[0-9]+\\.[A-z]{3,})");
//
//	/**
//	 * Gets the listof range names.
//	 *
//	 * @param xlsmTemp the xlsm temp
//	 * @return the listof range names
//	 * @throws Exception the exception
//	 */
//	public static List<String> getListofRangeNames(byte[] xlsmTemp) throws Exception {
//		List<String> result = new ArrayList<>();
//		try(
//			InputStream template = new ByteArrayInputStream(xlsmTemp);
//			Workbook wb = WorkbookFactory.create(template);){
//			for (int i = 0; i < wb.getNumberOfNames(); i++) {
//				String rangename = wb.getNameAt(i).getNameName();
//				Matcher matcher = ALIAS_NAME_RANGE_PATTERN.matcher(rangename);
//				if (matcher.find()) {
//					String[] bandname = rangename.split("\\.");
//					result.add(bandname[1]);
//				} else {
//					result.add(rangename);
//				}
//			}
//		} catch(Exception ex) {
//			logger.error(ex.toString());
//		}
//		return result;
//	}
//
//	/**
//	 * This method for the type of data in the cell, extracts the data and returns
//	 * it as a string.
//	 *
//	 * @param cell the cell
//	 * @return the cell value as string
//	 */
//	private static String getCellValueAsString(Cell cell) {
//		String strCellValue = null;
//		if (cell != null) {
//			switch (cell.getCellType()) {
//			case STRING:
//				strCellValue = cell.toString();
//				break;
//			case NUMERIC:
//				strCellValue = "";
//				break;
//			case BOOLEAN:
//				strCellValue = new String(new Boolean(cell.getBooleanCellValue()).toString());
//				break;
//			case BLANK:
//				strCellValue = "";
//				break;
//			}
//		}
//		return strCellValue;
//	}
//
//	/**
//	 * Gets the zero time date.
//	 *
//	 * @param date the date
//	 * @return the zero time date
//	 */
//	public static Date getZeroTimeDate(Date date) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		date = calendar.getTime();
//		return date;
//	}
//
//	/**
//	 * Gets the dimensions.
//	 *
//	 * @param aggregation the aggregation
//	 * @return the dimensions
//	 */
//	/*
//	 * AVERAGE(1,"Average"), COUNT(2, "Count"), COUNT_NUMS(3, "Count"), MAX(4,
//	 * "Max"), MIN(5, "Min"), PRODUCT(6, "Product"), STD_DEV(7, "StdDev"),
//	 * STD_DEVP(8, "StdDevp"), SUM(9, "Sum"),
//	 */
//	private static DataConsolidateFunction getDimensions(String aggregation) {
//		// below condition due to AVG getting from server but expecting Average in Excel
//		if (aggregation.equalsIgnoreCase("avg") || aggregation.equalsIgnoreCase("Average")) {
//			return DataConsolidateFunction.AVERAGE;
//		} else if (aggregation.equalsIgnoreCase("Count") || aggregation.equalsIgnoreCase("COUNT_DISTINCT")) {
//			return DataConsolidateFunction.COUNT;
//		} else if (aggregation.equalsIgnoreCase("Max")) {
//			return DataConsolidateFunction.MAX;
//		} else if (aggregation.equalsIgnoreCase("Min")) {
//			return DataConsolidateFunction.MIN;
//		} else if (aggregation.equalsIgnoreCase("Sum")) {
//			return DataConsolidateFunction.SUM;
//		} else if (aggregation.equalsIgnoreCase("Custom")) {
//			return DataConsolidateFunction.VAR;
//		} else if ("STDDEV".equals(aggregation)) {
//			return DataConsolidateFunction.STD_DEV;
//		} else if ("STDDEVP".equals(aggregation)) {
//			return DataConsolidateFunction.STD_DEVP;
//		} else if ("VARIANCE".equals(aggregation)) {
//			return DataConsolidateFunction.VAR;
//		} else if ("VARIANCEP".equals(aggregation)) {
//			return DataConsolidateFunction.VARP;
//		} else
//			return DataConsolidateFunction.valueOf(capitalizeFirstLetter(aggregation));
//	}
//
//	/**
//	 * Capitalize first letter.
//	 *
//	 * @param original the original
//	 * @return the string
//	 */
//	private static String capitalizeFirstLetter(String original) {
//		if (original == null || original.length() == 0) {
//			return original;
//		}
//		return original.substring(0, 1).toUpperCase() + original.substring(1);
//	}
//
//	/**
//	 * Read.
//	 *
//	 * @param c the c
//	 * @return the string
//	 * @throws SQLException the SQL exception
//	 * @throws IOException  Signals that an I/O exception has occurred.
//	 */
//	public static String read(Clob c) throws SQLException, IOException {
//		StringBuilder sb = new StringBuilder((int) c.length());
//		Reader r = c.getCharacterStream();
//		char[] cbuf = new char[OrbitQueryResult.CLOBBUFFERSIZE];
//		int n;
//		while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
//			sb.append(cbuf, 0, n);
//		}
//		return sb.toString();
//	}
}
