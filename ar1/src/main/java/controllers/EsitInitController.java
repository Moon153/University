package controllers;

import common.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import users.Scrutatore;

public class EsitInitController extends Controller{

	private Scrutatore s;
	private boolean control;
	
    @FXML
    private Button cancel;
    
    @FXML
    private Button login;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;

	@Override
    public void setParameters(Object scrutatore) {
    	s = (Scrutatore) scrutatore;
    }
	
    @Override
    public void init(){
    	if(s.getSession().getEsit().getState().equals("open")) {
    		labelInfo.setText("E' ora possibile visualizzare l'esito");
    		confirm.setVisible(false);
    		cancel.setVisible(false);
    	}
    	else
    		labelInfo.setText("Si desidera permettere agli elettori di\nvisualizzare l'esito delle elezioni?");
    }
    
    @FXML
    void cancelChoice(ActionEvent event) {
    	if(!control)
    		changeView("phaseView", s);
    	else
    		changeView("esitInitView", s);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(!control) {
    		labelInfo.setText("Prego confermare la possibilità di\nvisualizzazione dell'esito da parte degli elettori");
    		control = true;
    	}
    	else {
    		s.getSession().getEsit().setState("open");
    		changeView("phaseView", s);
    	}
    }

    @FXML
    void goToLogin(ActionEvent event) {
    	changeView("loginView", null);
    }
}