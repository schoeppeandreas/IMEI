package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteTxtFile {

	AppProperties ap = AppProperties.getInstance();
	private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss");
	private final String fileName = "Verdachtsfall_" + dateFormat.format(new Timestamp(new Date().getTime())) + ".txt";
	
	private String imei;
	private String kartenNummer;
	private String firma;
	
	public WriteTxtFile(String imei,String kartenNummer, String firma){
		this.imei = imei;
		this.kartenNummer = kartenNummer;
		this.firma = firma;
	}
	
	public void run() throws IOException{
			BufferedWriter output = new BufferedWriter(new FileWriter(fileName, true));
			output.write("IMEI: " + imei);
			output.newLine();
			output.write("Kartennummer: " + kartenNummer);
			output.newLine();
			output.write("Firma: " + firma);
			output.newLine();
			output.write("Datum-Uhrzeit: " + dateFormat.format(new Timestamp(new Date().getTime())));
			output.close();
	}
	
}
