package controllers;

import commons.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import users.Scrutatore;

public class ReviewDataController extends Controller{

	private Scrutatore s;
	private ObservableList<String> gruppi_partiti;
	private ObservableList<String> candidati;
	private char choice;

    @FXML
    private Button back;

    @FXML
    private HBox box_candidate;

    @FXML
    private HBox box_party;

    @FXML
    private ChoiceBox<String> candidate;

    @FXML
    private Label labelInfo;
    
    @FXML
    private Label labelDescription;

    @FXML
    private ChoiceBox<String> party;

    @FXML
    private Button send;

	 @Override
	 public void setParameters(Object parameter) {
	    	s = (Scrutatore) parameter;
  	}
	
	 @Override
	 public void init() {
		 if(s.getSession().getVoting().getVotingMode().equals("voto ordinale")) {
			 labelInfo.setText("Sono stati inseriti i seguenti elementi:");
			 labelDescription.setText("Elementi:");
			 send.setVisible(false);
		 }
		 else
			 labelInfo.setText("Sono stati inseriti i seguenti gruppi e partiti:");
		 gruppi_partiti = FXCollections.observableArrayList(s.getSession().getVoting().getPartiesList());
		 party.setItems(gruppi_partiti);
		 choice = 'g';
	 }
	 
    @FXML
    void goBack(ActionEvent event) {
    	changeView("votingDataView", s);
    }

    @FXML
    void sendData(ActionEvent event) {
    	if (choice == 'g'&&party.getValue()!=null){
    		box_party.setVisible(false);
    		if(s.getSession().getVoting().getCandidatesNumber(party.getValue())!=0) {
        		box_candidate.setVisible(true);
    			labelInfo.setText("Nel gruppo / partito " + party.getValue() + "\nrisultano inseriti i seguenti candidati:");
    			candidati = FXCollections.observableArrayList(s.getSession().getVoting().getCandidatesList(party.getValue()));
   		 		candidate.setItems(candidati);
    		}
    		else
    			labelInfo.setText("Non risultano candidati inseriti in\ntale gruppo / partito");
   		 	send.setText("Torna alla selezione gruppo / partito");
   		 	choice = 'c';
    	}
    	else {
    		box_party.setVisible(true);
    		box_candidate.setVisible(false);
    		send.setText("Visualizza candidati");
   		 	labelInfo.setText("Sono stati inseriti i seguenti gruppi e partiti:");
   		 	choice = 'g';
    	}
    }

}