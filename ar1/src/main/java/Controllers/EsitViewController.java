package Controllers;

import ar1.Controller;
import ar1.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EsitViewController extends Controller{

	private ObservableList<String> vincitori;
	
    @FXML
    private Button back;

    @FXML
    private HBox box_esit;

    @FXML
    private ChoiceBox<String> esit;

    @FXML
    private Label labelInfo;

    @Override
    public void init() {
    	if(Session.getInstance().getElector().getEsitoCandidati()!= null) {
    		vincitori = FXCollections.observableArrayList(Session.getInstance().getElector().getEsitoCandidati());
			esit.setItems(vincitori);
			box_esit.setVisible(true);
    	}
    	labelInfo.setText(Session.getInstance().getElector().viewEsit());
    }
    
    @FXML
    void goBack(ActionEvent event) {
    	changeView("loginView", null);
    }

}
