package controllers;

import commons.Controller;
import commons.Session;
import interfaces.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import users.Elettore;

public class WaitingElectorController extends Controller implements Observer{

	private boolean added;
	private Elettore e;
    
    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;
    
    @Override
    public void setParameters(Object elettore) {
    	e = (Elettore) elettore;
    }
    
    @Override
    public void init() {
    	if(e.votingAllowed()&&Session.getInstance().getElector().getState().equals("voting"))
    		labelInfo.setText("Si desidera procedere al voto?\nAttenzione! Una qualsiasi uscita forzata dalla\nvisualizzazione della tessera elettorale\nequivarrà a voto nullo / scheda bianca");
		else
			labelInfo.setText("Si desidera procedere alla visualizzazione\nesito?");
    }

    @FXML
    void cancelChoice(ActionEvent event) {
    	changeView("loginView", null);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(e.votingAllowed()&&Session.getInstance().getElector().getState().equals("voting")) {
    		changeView("votingPaperView", Session.getInstance().getElector(), e.getPopulation());
    		e.vote();
    	}
    	else {
    		if(!added) {
    			Session.getInstance().getElector().addElectorObserver(this);
    			added = true;
    		}
    		if(Session.getInstance().getElector().hasChanged())
    			Session.getInstance().getElector().notifyObservers();
    		labelInfo.setText("Prego attendere la fine degli scrutini");
    		confirm.setText("Ricarica visualizzazione");
    	}
    }

	@Override
	public void update() {
		changeView("esitView", null);
	}

}
