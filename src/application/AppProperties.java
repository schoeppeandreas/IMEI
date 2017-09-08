package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class AppProperties {

	// https://de.wikibooks.org/wiki/Muster:_Java:_Singleton
	private static AppProperties instance;
	private AppProperties() {	}
	public static synchronized AppProperties getInstance() {
		if (AppProperties.instance == null) {
			AppProperties.instance = new AppProperties();
		}
		return AppProperties.instance;}

	// https://www.mkyong.com/java/java-properties-file-examples/
	Properties properties = new Properties();
	private String path;
	private String pathKlaerfaelle;

	private void read() {
		FileInputStream inputStream = null;
		String current = null;
		try {
			current = new java.io.File(".").getCanonicalPath();
			File f = new File(current + "\\einstellungen.txt");
			if (f.exists() && !f.isDirectory()) {
				//System.out.println("getResourceAsStream >> " + f.getAbsolutePath());
				inputStream = new FileInputStream(f.getAbsolutePath());
				this.properties = new Properties();
				this.properties.load(inputStream);
			} else {
				setAllDefaultProperties(current); // Save
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void save(String value, String propertie, String infotext) {
		OutputStream output;
		try {
			output = new FileOutputStream("einstellungen.txt");
			properties.setProperty(propertie, value);
			properties.store(output, infotext);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	public String getPath() {
		read();
		this.path = properties.getProperty("path");
		return this.path;
	}

	public void setPath(String path) {
		save(path, "path", "Hier wird der Netzwerkpath zu den Exceldateien eingestellt.");
		this.path = path;
	}
	
	
	
	
	public String getPathKlaerfaelle() {
		read();
		this.pathKlaerfaelle = properties.getProperty("path_Klaerfaelle");
		return this.pathKlaerfaelle;
	}
	
	public void setPathKlaerfaelle(String pathKlaerfaelle) {	
		save(path, "path_Klaerfaelle", "Hier wird der Netzwerkpath zur Ablage der Klaerfaelle eingestellt.");
		this.pathKlaerfaelle = pathKlaerfaelle;
	}
	
	
	
	
	public void setAllDefaultProperties(String path){
		setPath(path);
		setPathKlaerfaelle(path);
	}
	
}