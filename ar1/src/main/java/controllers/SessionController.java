package controllers;

import common.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import users.Scrutatore;

public class SessionController extends Controller{

	private int counter;
	private Scrutatore s;
	private int n_elettori;
		 
    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelSession;
    
	@Override
    public void setParameters(Object scrutatore, int elettori) {
    	s = (Scrutatore) scrutatore;
    	n_elettori = elettori;
    }
	 
	@Override
    public void setParameters(Object scrutatore) {
    	s = (Scrutatore) scrutatore;
    }

    @Override
    public void init(){
    	if(!s.activeSession()){
			labelSession.setText("Non esiste alcuna sessione attiva.\nSi desiderare procedere alla \ncreazione di una nuova sessione di \nVotazione e Scrutinio?");    
    	}
    	else
    		labelSession.setText("Esiste una sessione di Votazione\ne Scrutinio attiva.\nSi desiderare accedere ad essa?");
    }
    
    @FXML
    public void cancelChoice(ActionEvent event) {
			changeView("loginView", null);
    }

    @FXML
    public void confirmChoice(ActionEvent event) {
		if(!s.activeSession()) {
			if(counter == 0) {
				labelSession.setText("Ciò non ne comporterà \nl'inizializzazione, che dovrà essere \neseguita successivamente.\nSi desidera procedere?");
				counter++;
			}
			else {
				changeView("phaseView", s, n_elettori);
			}
		}
		else {
			if(counter == 0) {
				labelSession.setText("Prego confermare la richiesta\ndi accesso");
				counter++;
			}
			else {
				changeView("phaseView", s, n_elettori);
			}
		}
    }
}