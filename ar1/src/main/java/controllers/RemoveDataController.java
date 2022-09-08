package controllers;

import commons.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import users.Scrutatore;

public class RemoveDataController extends Controller{

	private Scrutatore s;
	private ObservableList<String> gruppi_partiti;
	private ObservableList<String> candidati;

    @FXML
    private Button back;

    @FXML
    private HBox box_party;

    @FXML
    private HBox box_candidate;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;

    @FXML
    private ChoiceBox<String> party;

    @FXML
    private ChoiceBox<String> candidate;

    @FXML
    private CheckBox removeAll;

    @FXML
    private Button send;

	 @Override
	 public void setParameters(Object parameter) {
	    	s = (Scrutatore) parameter;
   	}
	
	 @Override
	 public void init() {
		labelInfo.setText("Prego indicare cosa si desidera eliminare");
		if(s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
			removeAll.setVisible(false);
		else
			removeAll.setText("Rimuovi intero\ngruppo / partito");
		gruppi_partiti = FXCollections.observableArrayList(s.getSession().getVoting().getPartiesList());
		party.setItems(gruppi_partiti);
	 }
	 
    @FXML
    void cancelChoice(ActionEvent event) {
    	changeView("removeDataView", s);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
		if(s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
			s.getSession().getVoting().deletePartyCandidateOrdinale(party.getValue());
		else
			s.getSession().getVoting().deletePartyCandidateCategorico(party.getValue(), candidate.getValue());
		if(s.getSession().getVoting().getPartiesNumber()==0) {
	    	changeView("votingDataView", s);
		}
		else
			changeView("removeDataView", s);
    }

    @FXML
    void goBack(ActionEvent event) {
    	changeView("votingDataView", s);
    }

    @FXML
    void sendData(ActionEvent event) {
    	if(party.getValue()!=null) {
    		if(s.getSession().getVoting().getVotingMode().equals("voto ordinale")) {
    			labelInfo.setText("Confermare l'eliminazione del\ngruppo / partito / candidato: " + party.getValue());
    			box_party.setVisible(false);
	    		updateChoice();
    		}
    		else{
		    	if(removeAll.isSelected() || s.getSession().getVoting().getCandidatesNumber(party.getValue())==0) {
		    		labelInfo.setText("Confermare l'eliminazione del\ngruppo / partito " + party.getValue() + "\ne dei relativi candidati?");
		    		box_party.setVisible(false);
		    		updateChoice();
		    	}
		    	else {
			    	if(s.getSession().getVoting().getCandidatesNumber(party.getValue())!= 0) {
			    		if(box_party.isVisible()) {
				    		labelInfo.setText("Prego indicare il candidato\nche si desidera eliminare");
				    		box_party.setVisible(false);
				    		box_candidate.setVisible(true);
				    		candidati = FXCollections.observableArrayList(s.getSession().getVoting().getCandidatesList(party.getValue()));
				    		candidate.setItems(candidati);
			    		}
			    		else {
			    			if(candidate.getValue()!=null) {
				    			labelInfo.setText("Confermare l'eliminazione del candidato\n" + candidate.getValue() + "?");
				        		box_candidate.setVisible(false);
				        		updateChoice();
			    			}
			    		}
			    	}
		    	}
	    	}
    	}
    }
    
    private void updateChoice() {
		send.setVisible(false);
		confirm.setVisible(true);
		cancel.setVisible(true);
	}
}