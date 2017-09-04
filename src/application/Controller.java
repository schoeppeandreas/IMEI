package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class Controller {

	public Controller() {
		getAllData(); // init performace at the first Read is to low...
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txt1;

	@FXML
	private Button btn1;
	// new
	@FXML
	private Button btnVerdacht;

	@FXML
	private TableView<TableDataHandlerIMEI> tableView1;

	@FXML
	private TableColumn<TableDataHandlerIMEI, String> tableRow1;
	// new
	@FXML
	private VBox layerVerdacht;
	// new
	@FXML
	private TextField txtVerdachtAusweisnummer;
	// new
	@FXML
	private TextField txtVerdachtImei;
	// new
	@FXML
	private ComboBox<String> comboVerdachtFirma;
	// new
	@FXML
	private Button btnVerdachtEnde;
	// new
	@FXML
	private Button btnVerdachtSpeichern;

	ObservableList<TableDataHandlerIMEI> list = FXCollections.observableArrayList();

	Service<Integer> service = null;

	private void createNewTimer() {
		if (service != null) {
			if (service.isRunning())
				service.cancel();
		} else {
			// System.out.println("create new");
			service = new Service<Integer>() {
				@Override
				protected Task<Integer> createTask() {
					// TODO Auto-generated method stub
					return new Task<Integer>() {
						@Override
						protected Integer call() throws Exception {
							Thread.sleep(60 * 1000);
							list.clear();
							return 0;
						}
					};
				}
			};
		}
		service.restart();
	}

	// new
	@FXML
	void btnVerdachtClick(ActionEvent event) {
		comboVerdachtFirma.getSelectionModel().clearSelection(); 
		txtVerdachtImei.setText(null); // lÃ¶scht alte Eingabe
		txtVerdachtAusweisnummer.setText(null); // lÃ¶scht alte Eingabe

		layerVerdacht.setVisible(true); // macht Layer sichtbar
		txtVerdachtAusweisnummer.requestFocus();
		
		if (txt1.getLength() > 6) {
			txtVerdachtImei.setText(txt1.getText());
		} else {

			txtVerdachtAusweisnummer.setText(txt1.getText());
		}

	}

	// new
	@FXML
	void btnVerdachtEndeClick(ActionEvent event) {
		layerVerdacht.setVisible(false); // blende den Verdachtslayer aus
	}

	// new
	@FXML
	void btnVerdachtSpeichernClick(ActionEvent event) {

		if(comboVerdachtFirma.getSelectionModel().getSelectedIndex() == -1){
			box("Keine Firma ausgewählt!", "Fehlende Daten!", AlertType.ERROR );
		} else if (txtVerdachtAusweisnummer.getText() == null || txtVerdachtAusweisnummer.getText().length() <6) {
			box("Ausweisnummer unvollständig!", "Fehlende Daten!", AlertType.ERROR );
		} else if (txtVerdachtImei.getText() == null || txtVerdachtImei.getText().length() <10) {
			box("IMEI unvollständig!", "Fehlende Daten!", AlertType.ERROR );
		} else {
			layerVerdacht.setVisible(false); // blende den Verdachtslayer aus
			txt1.setText(null); // lÃ¶scht alte Eingabe
			
			try {
				new WriteTxtFile(txtVerdachtImei.getText(),txtVerdachtAusweisnummer.getText(),comboVerdachtFirma.getSelectionModel().getSelectedItem()).run();
				box("Fall gemeldet!", "OK", AlertType.INFORMATION );
			} catch (IOException e) {
				box("Fall konnte nicht gespeichert werden! Bitte melden Sie den Fall schriftlich.", "Fehler beim Datenaustausch", AlertType.ERROR );
				//e.printStackTrace();
			};
			

		}

	}
    
    public static void box(String infoMessage, String titleBar, AlertType al){
    	Alert alert = new Alert(al);
    	alert.setTitle(titleBar);
    	alert.setHeaderText(null);
    	alert.setContentText(infoMessage);
    	alert.show();
    	
    	
    }
	
	
	@FXML
	void btn1click(ActionEvent event) {
		createNewTimer();
		sucheImei(txt1.getText() == null ? "" : txt1.getText());
	}

	@FXML
	void txt1KeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.BACK_SPACE && txt1.getText() == null || txt1.getText() == "") {
			list.clear();
		}

		if (event.getCode() == KeyCode.ENTER) {
			createNewTimer();
			sucheImei(txt1.getText() == null ? "" : txt1.getText());
		}
	}

	Boolean tableViewInit = false;

	private Boolean isTableViewInit() {
		if (!tableViewInit) {
			tableView1.setItems(list);
			tableRow1.setCellValueFactory(new PropertyValueFactory<TableDataHandlerIMEI, String>("imei"));
			tableViewInit = true;
		}
		return tableViewInit;
	}

	private void sucheImei(String suchtext) {
		list.clear();
		isTableViewInit();
		List<Imei> map = new ArrayList<Imei>();

		// System.out.println(suchtext.toString() );

		if (!suchtext.isEmpty()) {
			map = getAllData();
			// http://stackoverflow.com/questions/16664808/hashmapstring-integer-search-for-part-of-an-key
			for (Imei e : map) {
				if (e.equals(new Imei(suchtext))) {
					// System.out.println(e.toString());
					list.add(new TableDataHandlerIMEI(e.getImei()));
				}
			}
		}

		if (suchtext.isEmpty() || !map.contains(new Imei(suchtext))) {
			// System.out.println("Kein Eintrag");
			list.add(new TableDataHandlerIMEI("Kein Eintrag gefunden!"));
		} else {
			txt1.setText(null);
		}
		map.clear();

	}

	@FXML
	void initialize() {
		//new																								// ist
		layerVerdacht.setVisible(false); // blende den Verdachtslayer aus
		comboVerdachtFirma.getItems().addAll("RTS", "CTDI", "Randstad");
		comboVerdachtFirma.setEditable(false);
		
		//15.05.17
		tableView1.setPlaceholder(new Label("Bitte Suche durchführen!")); //Ersetzt "kein Content..."

		assert txt1 != null : "fx:id=\"txt1\" was not injected: check your FXML file 'layout.fxml'.";
		assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'layout.fxml'.";
		assert tableView1 != null : "fx:id=\"tableView1\" was not injected: check your FXML file 'layout.fxml'.";
		assert tableRow1 != null : "fx:id=\"tableRow1\" was not injected: check your FXML file 'layout.fxml'.";

	}

	// http://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
	private List<Imei> getAllData() {
		List<Imei> map = new ArrayList<Imei>();
		OoxmlReader ooxmlReader = new OoxmlReader();
		AppProperties ap = AppProperties.getInstance();
		File[] sourceFiles = getFiles(ap.getPath());

		for (File file : sourceFiles) {
			if (file.isFile() && file.getName().endsWith(".xlsx") && !file.getName().startsWith("~$")) {
				List<Imei> readMaps = ooxmlReader.read(file);
				for (Imei e : readMaps) {
					map.add(e); // e.getKey().toString(),
								// e.getValue().toString());
				}
			}
		}
		return map;
	}

	private File[] getFiles(String path) {
		File f = new File(path);
		File[] listOfFiles = f.listFiles();
		return listOfFiles;
	}

}
