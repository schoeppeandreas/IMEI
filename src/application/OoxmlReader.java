package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OoxmlReader {

	private List<Imei> data = null;

	public List<Imei> read(File f) {
		data = new ArrayList<Imei>();
		// FileInputStream fis = null;
		RandomAccessFile raf = null;
		XSSFSheet mySheet = null;
		XSSFWorkbook myWorkBook = null;
		try {
			// fis = new FileInputStream(f);

			// STEP 1: Create random access file read-only
			raf = new RandomAccessFile(f, "r");
			// STEP 2: Use Channels to convert to InputStream
			InputStream is = Channels.newInputStream(raf.getChannel());

			// Finds the workbook instance for XLSX file
			myWorkBook = new XSSFWorkbook(is);
			// Return first sheet from the XLSX workbook
			mySheet = myWorkBook.getSheetAt(0);
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();
			// Traversing over each row of XLSX file
			int rowCounter = 0;
			while (rowIterator.hasNext()) {
				rowCounter +=1;
				Row row = rowIterator.next();
								
				String kartennummer = getCellVall(row.getCell(0));
				String imei = getCellVall(row.getCell(1));
				String name = getCellVall(row.getCell(2));
				
				if (rowCounter>1) {
					data.add(new Imei(name, imei, kartennummer));
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
				myWorkBook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	// Hat den Vorteil, dass der Datentyp im Excel, Text sowohl als auch einer
	// Zahl entsprechen kann.

	// http://stackoverflow.com/questions/39993683/alternative-to-deprecated-getcelltype
	@SuppressWarnings("deprecation")

	private String getCellVall(Cell c) {
		
		if(c==null){
			return "";
		}
		
		String s = null;
		switch (c.getCellTypeEnum()) {
		case STRING:
			s = c.getStringCellValue();
			break;
		case NUMERIC:
			// System.out.println();
			Long l = Double.valueOf(c.getNumericCellValue()).longValue(); // to
																			// Long
			s = Long.toString(l); // to String
			break;
		case BLANK:
			break;
		case BOOLEAN:
			break;
		case ERROR:
			break;
		case FORMULA:
			break;
		case _NONE:
			break;
		default:
			break;
		}
		
		//System.out.println(s + " >> Read Out Excel");		
		return (s == null) ? new String("") : s.trim();
	}
}
