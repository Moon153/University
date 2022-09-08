package controllers;

import common.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import users.Scrutatore;

public class VotingInitController extends Controller{

	private Scrutatore s;
	private char choice;
	
	@Override
    public void setParameters(Object scrutatore) {
    	s = (Scrutatore) scrutatore;
    }
	
    @FXML
    private Button back;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;

    @FXML
    private Button open;

    @FXML
    private Button updateData;

    @FXML
    private Button updateMode;
    
    @FXML
    private HBox boxButtonsChoice;
    
    @FXML
    void goBack(ActionEvent event) {
    	if(choice == 'e')
    		changeView("votingInitView", s);
    	else
    		changeView("phaseView", s);
    }

    @FXML
    void openPhase(ActionEvent event) {
    	if(s.getSession().getVoting().getVotingMode() == null) {
    		labelInfo.setText("Non è possibile procedere poichè non\nrisulta selezionata alcuna modalità\ndi votazione");
    		updateViewButton('e');
    	}
    	else{
	    	if((s.getSession().getVoting().getVotingMode().equals("referendum") && s.getSession().getVoting().getReferendumData().equals("")) || (!s.getSession().getVoting().getVotingMode().equals("referendum") && s.getSession().getVoting().getPartiesNumber()==0)) {
	    		labelInfo.setText("Non è possibile procedere poichè non\nrisultano inseriti i dati necessari\nalla votazione");
	    		updateViewButton('e');
	    	}
	    	else {
	    		labelInfo.setText("Ciò comporterà la possibilità agli utenti \nelettore di votare.\nSi desidera procedere?");
				choice = 'o';
				updateViewButton(choice);
    		}
    	}
    }

    @FXML
    void updateVotingMode(ActionEvent event) {
		labelInfo.setText("Prego confermare la scelta di \ninserimento / aggiornamento della\nmodalità di votazione");
		choice = 'm';
		updateViewButton(choice);
    }

    @FXML
    void updateVotingData(ActionEvent event) {
    	if(s.getSession().getVoting().getVotingMode() != null) {
	    	labelInfo.setText("Prego confermare la scelta di inserimento / \naggiornamento dati necessari alla votazione");
			choice = 'd';
			updateViewButton(choice);
    	}
    	else {
    		updateViewButton('e');
    		labelInfo.setText("Non è possibilire inserire i dati poichè non\nrisulta inserita alcuna modalità\ndi votazione");
    	}
    }
    
    @FXML
    void cancelChoice(ActionEvent event) {
		changeView("votingInitView", s);
    }
    
    @FXML
    void updateViewButton(char c){
    	if(c!='e') {
    		boxButtonsChoice.setVisible(true);
    	}
    	open.setVisible(false);
		updateData.setVisible(false);
		updateMode.setVisible(false);
    }
    
    @FXML
    void confirmChoice(ActionEvent event) {
    	if(choice=='o') {
    		s.getSession().getVoting().setState("open");
    		s.getSession().setElectorPhase("voting");
    		changeView("phaseView", s);
    	}
    	else {
    		if(choice=='m')
    			changeView("votingModeView", s);
    		else {
    			if(s.getSession().getVoting().getVotingMode().equals("referendum"))
    				changeView("referendumDataView", s);
    			else
    				changeView("votingDataView", s);
    		}
    	}
    }
}