package application;

import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableDataHandlerIMEI extends Observable {

	private StringProperty imei;
	
	public TableDataHandlerIMEI(String imei) {
		this.imei = new SimpleStringProperty(imei);
	}

	public StringProperty getImei() {
		return imei;
	}

	public void setImei(StringProperty imei) {
		this.imei = imei;
	}
	
	public StringProperty imeiProperty(){
		return imei;
	}
	
}
