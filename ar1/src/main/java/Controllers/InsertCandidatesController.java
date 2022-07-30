package Controllers;

import Users.Scrutatore;
import ar1.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;

public class InsertCandidatesController extends Controller{

	private Scrutatore s;
	private char choice;
	private ObservableList<String> gruppi_partiti;
	private SpinnerValueFactory<Integer> new_candidates = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
	private char genere;


    @FXML
    private Button back;

    @FXML
    private HBox box_party;

    @FXML
    private HBox box_textField;
    
    @FXML
    private HBox box_genre;
    
    @FXML
    private HBox box_spinner;
    
    @FXML
    private ChoiceBox<String> party;
    
    @FXML
    private Spinner<Integer> aggiuntivi;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;

    @FXML
    private TextField nomeCandidato;

    @FXML
    private Button send;
    
    @FXML
    private CheckBox man;

    @FXML
    private CheckBox woman;
    
	@Override
	public void setParameters(Object parameter) {
		s = (Scrutatore) parameter;
 	}
	
	@Override
	public void init() {
		labelInfo.setText("Prego indicare il gruppo o partito su cui \nsi desidera operare");
		gruppi_partiti = FXCollections.observableArrayList(s.getSession().getVoting().getPartiesList());
		party.setItems(gruppi_partiti);
		aggiuntivi.setValueFactory(new_candidates);
		if(s.getSession().getVoting().getVotingMode().equals("voto categorico"))
			box_genre.setVisible(true);
	}
	 
    @FXML
    void cancelChoice(ActionEvent event) {
    	if(choice == 'c') {
    		insertNew();
    	}
		else
			changeView("insertCandidatesView", s);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(choice == 'i'&& !nomeCandidato.getText().equals("") && ((!s.getSession().getVoting().getVotingMode().equals("voto categorico"))||man.isSelected()||woman.isSelected())) {
    		labelInfo.setText("Confermare la scelta di inserimento del\ncandidato: " + nomeCandidato.getText());
    		box_textField.setVisible(false);
    		choice = 'c';
    	}
    	else {
    		if(choice == 'c') { 			
				if(s.getSession().getVoting().setVotingCategoricoData(party.getValue(), 0, nomeCandidato.getText(), genere)==false) {
					labelInfo.setText("Inserimento annullato: risulta già inserito\nun candidato con tale nome\nPrego inserire il nome del candidato\n(inseriti " + s.getSession().getVoting().getCandidatesNumber(party.getValue()) + " / " + s.getSession().getVoting().getPlannedCandidates(party.getValue()) + ")");
					box_textField.setVisible(true);
					choice = 'i';
    			}
				else
					insertNew();
    		}
			else {
				if(choice == 'a')
					s.getSession().getVoting().resizeParty(party.getValue(), (aggiuntivi.getValue() + s.getSession().getVoting().getPlannedCandidates(party.getValue())));
    			insertNew();
			}
    	}    	
    }
    
    private void insertNew(){
		if((s.getSession().getVoting().getPlannedCandidates(party.getValue())) > (s.getSession().getVoting().getCandidatesNumber(party.getValue()))) {
    		labelInfo.setText("Prego inserire il nome del candidato\n(inseriti " + s.getSession().getVoting().getCandidatesNumber(party.getValue()) + " / " + s.getSession().getVoting().getPlannedCandidates(party.getValue()) + ")");
    		box_textField.setVisible(true);
    		if(s.getSession().getVoting().getVotingMode().equals("voto categorico")) {
	    		man.setSelected(false);
	    		woman.setSelected(false);
	    		man.setVisible(true);
	    		woman.setVisible(true);
    		}
    		choice = 'i';
		}
		else {
			labelInfo.setText("Si desidera incrementare il numero di\ncandidati massimi del partito?");			
			box_spinner.setVisible(true);
			confirm.setVisible(false);
			send.setVisible(true);
			choice = 'n';
		}
    }
    

    @FXML
    void selectMan(ActionEvent event) {
    	woman.setVisible(!man.isSelected());
    	if(man.isSelected())
    		genere = 'm';
    }

    @FXML
    void selectWoman(ActionEvent event) {
    	man.setVisible(!woman.isSelected());
    	if(woman.isSelected())
    		genere = 'w';
    }

    @FXML
    void goBack(ActionEvent event) {
    	changeView("insertPartiesView", s);
    }

    @FXML
    void sendData(ActionEvent event) {
    	if(choice != 'n') {
	    	if(party.getValue()!= null) {
				labelInfo.setText("Confermare la scelta di modifica del\ngruppo / partito " + party.getValue());
				box_party.setVisible(false);
				updateChoice(true);
	    	}
    	}
    	else {
			labelInfo.setText("Si desidera che il partito " + party.getValue() + "\nabbia un totale di " + (aggiuntivi.getValue() + s.getSession().getVoting().getPlannedCandidates(party.getValue())) + " candidati?");
    		box_spinner.setVisible(false);
    		confirm.setVisible(true);
			send.setVisible(false);
			choice = 'a';
    	}
    		
    }

	public void updateChoice(boolean control) {
		send.setVisible(!control);
		confirm.setVisible(control);
		cancel.setVisible(control);
	}

}
