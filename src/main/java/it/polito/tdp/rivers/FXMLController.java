package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.SimulationResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private Button btnSimula;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtStartDate;

    @FXML
    void doInsertRiver(ActionEvent event) {
    	River r = this.boxRiver.getValue();
    	this.txtStartDate.clear();
    	this.txtEndDate.clear();
    	this.txtNumMeasurements.clear();
    	this.txtFMed.clear();
    	
    	this.txtStartDate.setText(""+model.trovoPrimaData(r));
    	this.txtEndDate.setText(""+model.trovoUltimaData(r));
    	this.txtNumMeasurements.setText(""+model.trovoNumeroMisurazioni(r));
    	this.txtFMed.setText(""+model.trovoMediaFlusso(r));
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String input = this.txtK.getText();
    	Integer inputNum;
    	try {
    		inputNum = Integer.parseInt(input);
    		River r = this.boxRiver.getValue();
        	SimulationResult res = model.simula(r, inputNum);
        	this.txtResult.appendText("\nNumero giorni in cui non si e soddisfatta la richiesta : " + res.getnGiorniNonSoddisfatti());
        	this.txtResult.appendText("\nCapacita media : " + res.getCapacitaMedia());
        	
    	}catch(NumberFormatException e){
    		this.txtResult.setText("Errore: inserire un numero!");
    	}
    	
    	
    	
    	
    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
       
    }
    public void setModel(Model model) {
    	this.model = model;
    	 for(River x : model.getAllRivers()) {
         	this.boxRiver.getItems().add(x);
         }
    }

}

