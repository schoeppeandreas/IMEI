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
	String path;

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
				save(current);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void save(String path) {
		OutputStream output;
		try {
			output = new FileOutputStream("einstellungen.txt");
			properties.setProperty("path", path);
			properties.store(output, "Hier wird der Netzwerkpath zu den Exceldateien eingestellt.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPath() {
		read();
		this.path = properties.getProperty("path");
		return path;
	}

	public void setPath(String path) {
		save(path);
		this.path = path;
	}
}