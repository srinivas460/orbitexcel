package orbit.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grapecity.documents.excel.BorderLineStyle;
import com.grapecity.documents.excel.BordersIndex;
import com.grapecity.documents.excel.CellInfo;
import com.grapecity.documents.excel.Color;
import com.grapecity.documents.excel.FormatConditionOperator;
import com.grapecity.documents.excel.FormatConditionType;
import com.grapecity.documents.excel.HorizontalAlignment;
import com.grapecity.documents.excel.IFormatCondition;
import com.grapecity.documents.excel.IRange;
import com.grapecity.documents.excel.IStyle;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.Workbook;

import models.Alignment;
import models.ClientConstraint;
import models.ClientQueryModel;
import models.ClientSelection;
import models.ConditionalFormat;
import models.DataType;
import models.TemplateVariableType;
import utils.Logs;

public class UtilsExcel {
	private static Map<String, Integer> operators = new HashMap<String, Integer>();
	private static final int rowHeight = 25;


//	@Autowired
//	private Workbook sWorkbook;

	public Workbook getWorkbook(InputStream inputExcel) {
		Workbook sWorkbook = new Workbook();
//		
		if (inputExcel != null) {
			sWorkbook.open(inputExcel);
		}

		sWorkbook.SetLicenseKey(
				"polarisassociates.com,747938682294816#A0ZQ6NiojIklkI1pjIEJCLi4TPRxUZGlXYrMzLrNTZZpWV52Sc594KNdHTMNHb9JWQURkZy86VMFEMJpGajB7ZwkXaoljdBNlY4dlM5IFdrc5NnpmMqp5Rp96axc6Y4QWbXNVSB5GR7ElI0IyUiwiN7kDMxITO6ATM0IicfJye35XX3JyVXFTRiojIDJCLiEjdgEmdhpEIsV6Y8VEIy3mZgQnbl5Wdj3GRgM4RiojIOJyebpjIkJHUiwiI6AjM4gDMgYDM6ATOxAjMiojI4J7QiwiIt36YuMXZ4FWaj36czF6cpJXYs3GciojIh94QiwiI6EDO4kjMygjNtM7O");
//				

		return sWorkbook;
	}

//	public ByteArrayOutputStream generateDefaultYargTemplate(InputStream templateStream, ClientReport report,
//			int position) {
//		return createYargtemplate.generateDDRYargTemplate(templateStream, report, position);
//	}

	public static void createDetailsSheet(Workbook workbook, ClientQueryModel clientQueryModel, IStyle headerStyle) {
		
		String SheetDetails = TemplateVariableType.SheetDetails.getValue();
		IWorksheet worksheet = workbook.getWorksheets().get(SheetDetails);

		boolean isDetailsExist = true;

		if (worksheet == null) {
			workbook.getWorksheets().add().setName(SheetDetails);
			worksheet = workbook.getWorksheets().get(SheetDetails);
			isDetailsExist = false;
		}

		IStyle paramStyle = worksheet.getWorkbook().getStyles().get(TemplateVariableType.ParameterStyle.getValue());

		if (paramStyle == null && headerStyle != null) {
			paramStyle = headerStyle;
		}
		
		if (paramStyle != null) {
			paramStyle.getBorders().get(BordersIndex.EdgeLeft).setLineStyle(BorderLineStyle.Thin);
			paramStyle.getBorders().get(BordersIndex.EdgeRight).setLineStyle(BorderLineStyle.Thin);
			paramStyle.getBorders().get(BordersIndex.EdgeTop).setLineStyle(BorderLineStyle.Thin);
			paramStyle.getBorders().get(BordersIndex.EdgeBottom).setLineStyle(BorderLineStyle.Thin);
		}
		// If details page does not exist, below we are creating the label cells
		if (!isDetailsExist) {
			IRange labelRange = worksheet.getRange("A1");
			labelRange.setValue("Generated Date");
			labelRange.getEntireRow().setRowHeight(rowHeight);
			labelRange.getEntireRow().setColumnWidth(40);

			labelRange = worksheet.getRange("A2");
			labelRange.setValue("Report Name");
			labelRange.getEntireRow().setRowHeight(rowHeight);

			IRange paramHeader = worksheet.getRange("A3");
			paramHeader.setValue("PARAMETERS INFO");
			worksheet.getRange("A3:B3").merge(true);
			paramHeader.getEntireRow().setRowHeight(25);

			IRange pL = worksheet.getRange("A4");
			pL.setValue("NAME");
			pL.getEntireRow().setRowHeight(rowHeight);

			IRange pV = worksheet.getRange("B4");
			pV.setValue("VALUE");
		}

//		if(isDetailsExist) {
//			INames names = worksheet.getNames();
//			String Range = names.get("_4."+UtilsExcel.KeyGeneratedDate).getRefersTo();
//			worksheet.getRange(Range).setValue("" + generatedDateFormat.format(new Date()));
//			
//			Range = names.get("_4."+UtilsExcel.KeyGeneratedDate).getRefersTo();
//			worksheet.getRange(Range).setValue("" + generatedDateFormat.format(new Date()));
//			
//		} else {
		DateFormat generatedDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss a");
		IRange date = worksheet.getRange("B1");
		date.setValue("" + generatedDateFormat.format(new Date()));

		IRange name = worksheet.getRange("B2");
		name.setValue(clientQueryModel.getReportName());

		// Applying style for cells
		worksheet.getRange("A1:B4").setStyle(paramStyle);

		IRange pL = worksheet.getRange("A5");
		pL.setValue("");
		pL.getEntireRow().setRowHeight(rowHeight);

		IRange pV = worksheet.getRange("B5");
		pV.setValue("");
//		}
		insertParameterData(worksheet, clientQueryModel);

	}

	public static void insertParameterData(IWorksheet worksheet, ClientQueryModel clientQueryModel) {
		// now inserting parameters and values
		int colCell = 0;
		int rowPos = 4;
		
		String userDateFormat = null;
		if(clientQueryModel.getProperties().containsKey(TemplateVariableType.USER_INFO_SETTINGS.getValue())) {
			Map<String, Object> userSettings = (Map<String, Object>) clientQueryModel.getProperties().get(TemplateVariableType.USER_INFO_SETTINGS.getValue());
			userDateFormat = ""+userSettings.get("DATE_FORMAT");
			
		}
		
		for (ClientConstraint constraint : clientQueryModel.getConstraints()) {
			if (constraint.getValueType() != 3)
				continue;
			ClientSelection clientSelection = constraint.getClientSelection();
			Object datatype = clientSelection.getProperties() != null ? clientSelection.getProperties().get("datatype")
					: "STRING";
			String datatypeName = "";
			if (datatype instanceof DataType) {
				DataType dt = (DataType) datatype;
				datatypeName = dt.getName();
			} else {
				datatypeName = (String) datatype;
			}
			colCell = 0;
			if (constraint.getDisplay() != null) {

				Logs.debug("Report parameters Shows Display Values: =>"+constraint.getDisplay());
				String startCellPos = CellInfo.CellIndexToName(rowPos, 0);
				worksheet.getRange(startCellPos)
						.setValue(clientQueryModel.findParameter(constraint.getParameterName()));
				worksheet.getRange(startCellPos).getEntireRow().setRowHeight(rowHeight);

				// inserting parameter value here
				List<String> listValues = new ArrayList<>();
				for (Object data : constraint.getDisplay()) {
					listValues.add(ExcelUtils.getParamObjectValue(data, datatypeName, userDateFormat));
				}
				startCellPos = CellInfo.CellIndexToName(rowPos, 1);
				worksheet.getRange(startCellPos)
						.setValue(WriteExcel.getParamValueWithOperators(constraint.getOperator(), listValues, ""));

			} else {
				if (constraint.getValue() != null) {
					// inserting parameter name here//
					String startCellPos = CellInfo.CellIndexToName(rowPos, 0);
					worksheet.getRange(startCellPos)
							.setValue(clientQueryModel.findParameter(constraint.getParameterName()));
					worksheet.getRange(startCellPos).getEntireRow().setRowHeight(rowHeight);

					startCellPos = CellInfo.CellIndexToName(rowPos, 1);
					worksheet.getRange(startCellPos).setValue(WriteExcel.getParamValueWithOperators(constraint.getOperator(), null,
							ExcelUtils.getParamObjectValue(constraint.getValue(), datatypeName, userDateFormat)));
				}

				if (constraint.getValues() != null) {
					// inserting parameter name here//
					String startCellPos = CellInfo.CellIndexToName(rowPos, 0);
					worksheet.getRange(startCellPos)
							.setValue(clientQueryModel.findParameter(constraint.getParameterName()));
					worksheet.getRange(startCellPos).getEntireRow().setRowHeight(rowHeight);

					// inserting parameter value here
					List<String> listValues = new ArrayList<>();
					for (Object data : constraint.getValues()) {
						listValues.add(ExcelUtils.getParamObjectValue(data, datatypeName, userDateFormat));
					}

					startCellPos = CellInfo.CellIndexToName(rowPos, 1);
					worksheet.getRange(startCellPos)
							.setValue(WriteExcel.getParamValueWithOperators(constraint.getOperator(), listValues, ""));
				}

				if (constraint.getValue() == null && constraint.getValues() == null) {
					String startCellPos = CellInfo.CellIndexToName(rowPos, 0);
					worksheet.getRange(startCellPos)
							.setValue(clientQueryModel.findParameter(constraint.getParameterName()));
					worksheet.getRange(startCellPos).getEntireRow().setRowHeight(rowHeight);
					startCellPos = CellInfo.CellIndexToName(rowPos, 1);
					worksheet.getRange(startCellPos)
							.setValue(WriteExcel.getParamValueWithOperators(constraint.getOperator(), null, ""));
				}

			}
			rowPos++;
		}

	}

//	public  void createConditionalFormat(IWorksheet worksheet, ClientConstraint cond,
//			Map<String, Object> properties, String dataType, String cellRange) {
//
//		applyFormat(worksheet, cond, properties, dataType, cellRange, cellRange);
//		
//	}

	/*
	 * public void createConditionalFormat(IWorksheet worksheet,ClientConstraint
	 * cond,Map<String, Object> properties, String dataType, int dataStartRow,int
	 * dataEndRow, String ColumnName) { String range=null;
	 * if(dataStartRow==dataEndRow) range=ColumnName+""+dataStartRow; else range =
	 * ColumnName+""+dataStartRow+":"+ColumnName+""+dataEndRow;
	 * 
	 * String cellRange=ColumnName+""+dataStartRow;
	 * 
	 * applyFormat(worksheet, cond, properties, dataType, range, cellRange); }
	 */

	public static void applyAllFormats(IWorksheet worksheet, ClientQueryModel clientQueryModel, int dataStartRow,
			int dataEndRow, boolean[] hideColumns) {
		ObjectMapper mapper = new ObjectMapper();
		int colPos = 0;
		List<Object> cfrules = null;
		if (clientQueryModel.getProperties().containsKey("cfrules")) {
			cfrules = (List<Object>) clientQueryModel.getProperties().get("cfrules");
			Logs.debug("Conditional Formats Found {}"+ cfrules.size());
		}
		for (int i = 0; i < clientQueryModel.getSelections().size(); i++) {
			ClientSelection clientSelection = clientQueryModel.getSelections().get(i);
			if (hideColumns[i])
				continue;

			Object datatype = clientSelection.getProperties() != null ? clientSelection.getProperties().get("datatype")
					: "STRING";
			String datatypeName = "";
			if (datatype instanceof DataType) {
				DataType dt = (DataType) datatype;
				datatypeName = dt.getName();
			} else {
				datatypeName = (String) datatype;
			}

			String cellColumnName = CellInfo.ColumnIndexToName(colPos);

			if (CollectionUtils.isNotEmpty(cfrules)) {
				for (Object obj : cfrules) {
					ConditionalFormat conditionalFormat = mapper.convertValue(obj, ConditionalFormat.class);
					ClientConstraint cond = conditionalFormat.getCondition();
					ClientSelection formatColumn = conditionalFormat.getFormatColumn();
					Logs.debug(clientSelection.getName() + "=>" + formatColumn.getName());
					if (clientSelection != null) {
						if ((clientSelection.getLogicalColumnId() != null
								&& clientSelection.getCategoryId().equals(formatColumn.getCategoryId())
								&& clientSelection.getLogicalColumnId().equals(formatColumn.getLogicalColumnId()))
								|| (clientSelection.getFormula() != null
										&& clientSelection.getFormula().equals(formatColumn.getFormula()))) {

							createConditionalFormat(worksheet, cond, conditionalFormat.getProperties(), datatypeName,
									dataStartRow, dataEndRow, cellColumnName);

						}
					}
				}
			} else {
				Logs.debug("No Conditional Formats found for {}"+ clientSelection.getName());
			}

			String cellRange = cellColumnName + "" + dataStartRow + ":" + cellColumnName + "" + dataEndRow;

			// Applying Aligment below
			Object alignmenttype = clientSelection.getProperties().get("aln");
			String alignment = "LEFT";
			if (alignmenttype != null) {
				if (alignmenttype instanceof Alignment) {
					Alignment aln = (Alignment) alignmenttype;
					alignment = aln.getCode();
				} else {
					alignment = (String) alignmenttype;
				}
			}

			if ("LEFT".equals(alignment.toUpperCase())) {
				worksheet.getRange(cellRange).setHorizontalAlignment(HorizontalAlignment.Left);
			} else if ("CENTER".equals(alignment.toUpperCase())) {
				worksheet.getRange(cellRange).setHorizontalAlignment(HorizontalAlignment.Center);
			} else if ("RIGHT".equals(alignment.toUpperCase())) {
				worksheet.getRange(cellRange).setHorizontalAlignment(HorizontalAlignment.Right);
			}

			// Applying cell format
			String format = (String) clientSelection.getProperties().get("fmt");
			if (StringUtils.isNotEmpty(format) && clientSelection.getProperties().get("nnbr") != null) {
				if ((boolean) clientSelection.getProperties().get("nnbr")) {
					format = format + ";(" + format + ")";
				}
			}
			
			if (StringUtils.isNotEmpty(format) && !"0".equals(format) && !"null".equals(format)) {
				worksheet.getRange(cellRange).setNumberFormat(format.replaceAll("%", "\\\\%"));
			}
			colPos++;

		}
	}

	public static void createConditionalFormat(IWorksheet worksheet, ClientConstraint cond, Map<String, Object> properties,
			String dataType, int dataStartRow, int dataEndRow, String ColumnName) {

		String range = null;
		if (dataStartRow == dataEndRow)
			range = ColumnName + "" + dataStartRow;
		else
			range = ColumnName + "" + dataStartRow + ":" + ColumnName + "" + dataEndRow;

		String cellRange = ColumnName + "" + dataStartRow;

		createConditionalFormat(worksheet, cond, properties, dataType, range, cellRange);
	}

	public static void createConditionalFormat(IWorksheet worksheet, ClientConstraint cond, Map<String, Object> properties,
			String dataType, String range, String cellRange) {

		if (operators.size() == 0)
			populateOperators();
		
		IFormatCondition condition = null;
		DateFormat paramDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String expressionFormulae = "";
		
		String operator= cond.getOperator();
		if (operator.equals("&lt;")) {
			operator ="<";
		} else if (operator.equals("&gt;")) {
			operator =">";
		} 
		
//		String cellRange=ColumnName+""+dataStartRow;
		// Condition for Values between 2
		if (operator.indexOf("between") >= 0) {
			if ("DATE".equals(dataType) || "DATETIME".equals(dataType)) {

				try {
					if (cond.getValues() != null && cond.getValues().size() == 2) {
						Date dd1 = paramDateFormat.parse((String) cond.getValues().get(0));
						Date dd2 = paramDateFormat.parse((String) cond.getValues().get(1));

						Calendar cal = Calendar.getInstance();
						cal.setTime(dd1);
						condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions().add(
								FormatConditionType.CellValue,
								(operator.indexOf("not ") != -1) ? FormatConditionOperator.NotBetween
										: FormatConditionOperator.Between,
								dd1, dd2);
					}

				} catch (ParseException e) {
					ErrorThrow("Error when formatting date in conditional format" + e);
				}

			} else {
				condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions()
						.add(FormatConditionType.CellValue,
								(operator.indexOf("not ") != -1) ? FormatConditionOperator.NotBetween
										: FormatConditionOperator.Between,
								cond.getValues().get(0), cond.getValues().get(1));
			}

		} else if (operator.indexOf("beginswith") != -1 || operator.indexOf("endswith") != -1
				|| operator.indexOf("contains") != -1) {

			String operatorExp = ">";
			if (operator.indexOf("not ") != -1)
				operatorExp = "=";

			if (operator.indexOf("beginswith") != -1) {
				expressionFormulae = "=COUNTIF(" + cellRange + ",\"" + cond.getValue() + "*\")" + operatorExp + "0";
			} else if (operator.indexOf("endswith") != -1) {
				expressionFormulae = "=COUNTIF(" + cellRange + ",\"*" + cond.getValue() + "\")" + operatorExp + "0";
			} else if (operator.indexOf("contains") != -1) {
				expressionFormulae = "=COUNTIF(" + cellRange + ",\"*" + cond.getValue() + "*\")" + operatorExp + "0";
			}

			condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions()
					.add(FormatConditionType.Expression, FormatConditionOperator.None, expressionFormulae, null);

		} else if ("is empty".equals(operator)) {
			expressionFormulae = "=LEN(TRIM(" + cellRange + "))=0";
			condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions()
					.add(FormatConditionType.Expression, FormatConditionOperator.None, expressionFormulae, null);
		} else if ("is not empty".equals(operator)) {
			expressionFormulae = "=LEN(TRIM(" + cellRange + "))<>0";
			condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions()
					.add(FormatConditionType.Expression, FormatConditionOperator.None, expressionFormulae, null);
		} else {
			if (operators.containsKey(operator)) {
				if ("STRING".equals(dataType) && !"==".equals(operator)) {
					expressionFormulae = "=LEN(TRIM(" + cellRange + "))<>0";
					condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions().add(
							FormatConditionType.Expression, FormatConditionOperator.None, expressionFormulae, null);

				} else if ("DATE".equals(dataType) || "DATETIME".equals(dataType)) {
					try {
						// logger.debug("Date value=>{}", cond.getValue());
						if (cond.getValue() != null) {
							Date dd = paramDateFormat.parse((String) cond.getValue());
							// logger.debug("Date=>{}", dd);
							String oper = operator;
							if ("==".equals(oper))
								oper = "=";
							Calendar cal = Calendar.getInstance();
							cal.setTime(dd);
							expressionFormulae = ("=" + cellRange + "" + oper + "DATE(" + cal.get(Calendar.YEAR) + ","
									+ (cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")");

							condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions().add(
									FormatConditionType.Expression, FormatConditionOperator.None, expressionFormulae,
									null);
						}
					} catch (ParseException e) {
						ErrorThrow("Error when formatting date in conditional format" + e);
					}
				} else {
					
					if(isStringOnly(""+cond.getValue())) {
						//expression for String value only
						expressionFormulae = "=UPPER(TRIM(" + cellRange + ":" + cellRange + ")) "
								+ getExcelFormulae(operator) +"\"" + cond.getValue() + "\"";
					}else {
						//expression for numeric value only
						expressionFormulae = "=" + cellRange +  ""
								+ getExcelFormulae(operator) + cond.getValue();
					}
					condition = (IFormatCondition) worksheet.getRange(range).getFormatConditions()
							.add(FormatConditionType.Expression, FormatConditionOperator.None, expressionFormulae, null);
				}
			}
		}

		// Applying background Color below
		try {
			if ((condition != null)&&(properties != null && properties.get("bgclr") != null))
				condition.getInterior().setColor(hex2Rgb("#" + properties.get("bgclr")));
		} catch (NumberFormatException e4) {
			ErrorThrow("Error wheng creating conditional format for bgcolor" + e4);
		}

		// Applying font below
		try {
			if ((condition != null)&&(properties != null && properties.get("font") != null)) {
				// font Color
				if (((HashMap) properties.get("font")).get("colorHexcode") != null) {
					String fontColor = "#" + ((HashMap) properties.get("font")).get("colorHexcode");
					condition.getFont().setColor(hex2Rgb(fontColor));
				}

				// Font style Bold
				if (((HashMap) properties.get("font")).get("bold") != null) {
					Boolean bold = (Boolean) ((HashMap) properties.get("font")).get("bold");
					condition.getFont().setBold(bold);
				}
				// Font style Italic
				if (((HashMap) properties.get("font")).get("italic") != null) {
					Boolean italic = (Boolean) ((HashMap) properties.get("font")).get("italic");
					condition.getFont().setItalic(italic);
				}
				// Font style Underline
				if (((HashMap) properties.get("font")).get("underline") != null) {
					Boolean underline = (Boolean) ((HashMap) properties.get("font")).get("underline");
					condition.getFont().setItalic(underline);
				}

				// Font Size
				if (((HashMap) properties.get("font")).get("size") != null) {
					if (StringUtils.isNotEmpty((String) ((HashMap) properties.get("font")).get("size"))) {
						int size = Integer.parseInt((String) ((HashMap) properties.get("font")).get("size"));
						condition.getFont().setSize(Double.valueOf(size));
					}
				}

			}
		} catch (NumberFormatException e3) {
			ErrorThrow("Error wheng creating conditional format for font color" + e3);
		}

	}
	
	public static boolean isStringOnly(String str) 
    { 
        return (StringUtils.isNotBlank(str) && !NumberUtils.isNumber(str)); 
    }

	public static String getExcelFormulae(String operator) {

		if ("!=".equals(operator)) {
			return "<>";
		} else if ("==".equals(operator)) {
			return "=";
		}
		return operator;
	}

	public static void populateOperators() {
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

	public static void ErrorThrow(String e) {
		throw new RuntimeException(e);

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

	public String convertDatetoString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

}
