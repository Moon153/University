package Controllers;

import Users.Scrutatore;
import ar1.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BallotInitController extends Controller{

	private Scrutatore s;
	private char choice;
	private String mode;
	private ObservableList<String> vincitori;

    @FXML
    private Button back;

    @FXML
    private HBox box_choice;

    @FXML
    private HBox box_esit;

    @FXML
    private Button cancel;

    @FXML
    private Button close;

    @FXML
    private Button confirm;

    @FXML
    private ChoiceBox<String> esit;

    @FXML
    private Label labelInfo;

    @FXML
    private Button reviewEsit;

    @FXML
    private Button reviewPapers;

    @FXML
    private Button updateMode;

	@Override
    public void setParameters(Object scrutatore) {
    	s = (Scrutatore) scrutatore;
    }

    @FXML
    void cancelChoice(ActionEvent event) {
		changeView("BallotInitView", s);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(choice == 'e') {
    		s.getSession().getBallot().setState("concluded");
    		changeView("phaseView", s);
    	}
    	else {
	    	s.getSession().getBallot().setEsitMode(mode);
	    	changeView("BallotInitView", s);
    	}
    }

    @FXML
    void goBack(ActionEvent event) {
    	if(choice !=  0)
    		changeView("BallotInitView", s);
    	else
    		changeView("phaseView", s);
    }

    @FXML
    void reviewVotingEsit(ActionEvent event) {
    	if(choice != 'u') {
	    	if(s.getSession().getBallot().getEsitMode()!=null) {
	    		s.getSession().getBallot().performBallot(s.getSession().getBallot().getVotingMode());
	    		if(s.getSession().getBallot().getEsitoCandidati()!=null){
	    			box_esit.setVisible(true);
	    			vincitori = FXCollections.observableArrayList(s.getSession().getBallot().getEsitoCandidati());
	    			esit.setItems(vincitori);
	    		}
    			labelInfo.setText(s.getSession().getBallot().viewEsit());
	    	}
	    	else {
	    		labelInfo.setText("Non è possibile visualizzare l'esito poichè\nnon è stata inserita alcuna modalità\ndi vittoria");
	    		choice = 0;
	    	}
    	}
    	else {
    		box_choice.setVisible(true);
    		if(s.getSession().getBallot().getVotingMode().equals("referendum")) {
				labelInfo.setText("Confermare come modalità di vittoria:\nReferendum con quorum?");
    			mode = "quorum";
    		}
    		else {
    			labelInfo.setText("Confermare come modalità di vittoria:\nMaggioranza?");
    			mode = "maggioranza";
    		}
    	}
    	updateButtons();
    }

    private void updateButtons() {
		reviewEsit.setVisible(false);
		reviewPapers.setVisible(false);
		updateMode.setVisible(false);
		close.setVisible(false);
	}

	@FXML
    void reviewVotingPapers(ActionEvent event) {
		if(choice != 'u') {
			if (s.getSession().getBallot().getNumeroVoti()>0)
				changeView("papersView", s);
		}
		else {
			if(s.getSession().getBallot().getVotingMode().equals("referendum")) {
				labelInfo.setText("Confermare come modalità di vittoria:\nReferendum senza quorum?");
				mode = "noQuorum";
			}
			else{
	   			labelInfo.setText("Confermare come modalità di vittoria:\nMaggioranza assoluta?");
    			mode = "assoluta";				
			}
			box_choice.setVisible(true);
			updateButtons();
		}
    }

    @FXML
    void updateVotingMode(ActionEvent event) {
    	labelInfo.setText("Selezionare la modalità di vittoria");
    	updateMode.setVisible(false);
    	close.setVisible(false);
    	choice = 'u';
    	if(s.getSession().getBallot().getVotingMode().equals("referendum")){
    		reviewPapers.setText("Referendum senza quorum");
    		reviewEsit.setText("Referendum con quorum");
    	}
    	else {
    		reviewPapers.setText("Maggioranza assoluta");
    		reviewEsit.setText("Maggioranza");
    	}
    }

    @FXML
    void closeBallot(ActionEvent event) {
    	if(s.getSession().getBallot().viewEsit()!=null) {
			labelInfo.setText("Confermare di voler concludere\nla fase di scrutinio?");
			box_choice.setVisible(true);
			choice = 'e';
    	}
    	else {
    		labelInfo.setText("Non è possibile procedere alla chiusura poichè \nnon è stato ancora avviato il calcolo dell'esito");
    		choice = 0;
    	}
    	updateButtons();
    }

}
