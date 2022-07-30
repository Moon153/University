package Controllers;

import Users.Scrutatore;
import ar1.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class InsertPartiesController extends Controller{

	private Scrutatore s;
	private boolean name_control = true;
	private int party;
	private int n_parties;
	private int n_candidates;
	private SpinnerValueFactory<Integer> parties_selector = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
	private SpinnerValueFactory<Integer> candidates_selector = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

	@FXML
    private Button back;

    @FXML
    private HBox box_spinner;

    @FXML
    private HBox box_textField;

    @FXML
    private TextField nomePartitoGruppo;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Button send;

    @Override
	public void setParameters(Object scrutatore) {
		s = (Scrutatore) scrutatore;
 	}
	
	@Override
	public void init() {
		if(s.getSession().getVoting().getVotingMode().equals("voto ordinale")) {
				labelInfo.setText("Prego indicare il numero di gruppi / partiti\n/ candidati che si desidera inserire (max 100)\nAttualmente inseriti: " + s.getSession().getVoting().getPartiesNumber() + "/100");
				nomePartitoGruppo.setPromptText("Nome gruppo / partito / candidato");
		}
		else
			labelInfo.setText("Prego indicare il numero di gruppi e/o \npartiti che si desidera inserire (max 100)\nN° gruppi/partiti attualmente inseriti: " + s.getSession().getVoting().getPartiesNumber() + "/100");
		spinner.setValueFactory(parties_selector);
	}

    @FXML
    void sendData(ActionEvent event) {
    	if(party > n_parties) {
			 if(!s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
				 insertCandidates();
			 else
				 votingData();
    	}
    	else {
	    	if (party != 0) {
	    		if(!nomePartitoGruppo.getText().equals("")) {
					 if(!s.getSession().getVoting().getVotingMode().equals("voto ordinale")) {
						 if(spinner.getValue()==1)
							 labelInfo.setText("Confermare la scelta di \ninserimento del partito / gruppo:\n" + nomePartitoGruppo.getText() + "\nformato da " + spinner.getValue() + " candidato");
						 else
							 labelInfo.setText("Confermare la scelta di \ninserimento del partito / gruppo:\n" + nomePartitoGruppo.getText() + "\nformato da " + spinner.getValue() + " candidati");
						 box_spinner.setVisible(false);
					 }
					 else
						 labelInfo.setText("Confermare la scelta di \ninserimento del gruppo / partito / candidato :\n" + nomePartitoGruppo.getText());
					 box_textField.setVisible(false);
					 updateChoices(true);
	    		}
	    	}
	    	else {
	    		if((s.getSession().getVoting().getPartiesNumber() + spinner.getValue()) <= 100) {
		    		if(spinner.getValue()==0) {
			    		labelInfo.setText("Prego confermare la scelta di non \ninserire alcun nuovo partito o gruppo");
			    		n_parties = 0;
			    	}
		    		else{
				    	if(spinner.getValue()==1) {
				    		labelInfo.setText("Prego confermare la scelta di inserimento \ndi 1 nuovo partito o gruppo");
				    		n_parties = 1;
				    	}
						else {
							labelInfo.setText("Prego confermare la scelta di " + spinner.getValue() + " nuovi \npartiti e/o gruppi");
							n_parties = spinner.getValue();
						}
		    		}
			    	box_spinner.setVisible(false);		
			    	updateChoices(true);
		    	}
	    	}
    	}
    }

	private void insertCandidates() {
		changeView("insertCandidatesView", s);
	}

	public void updateChoices(boolean control) {
		send.setVisible(!control);
		confirm.setVisible(control);
		cancel.setVisible(control);
	}

	 @FXML
    void cancelChoice(ActionEvent event) {
    	if(party!=0) {
    		labelInfo.setText("Prego inserire il nome del partito n° " + party + "\ne il numero di candidati appartenenti ad esso");
    		insertDataParty();
    	}
    	else
    		changeView("insertPartiesView", s);
    }

	 @FXML
    void confirmChoice(ActionEvent event) {
		 if (n_parties == 0) {
			 if(s.getSession().getVoting().getPartiesNumber()!=0) {
				 if(!s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
					 insertCandidates();
				 else
					 votingData();
			 }
			 else
				 votingData();
		 }
		 if(party>0) {
			if(!s.getSession().getVoting().getVotingMode().equals("voto ordinale")) {
				n_candidates = spinner.getValue();
				if (s.getSession().getVoting().setVotingCategoricoData(nomePartitoGruppo.getText(), n_candidates, null, ' ') == false) {
					name_control = false;
					party_error();
				}
				else
					name_control = true;
			}
			else {
				if (s.getSession().getVoting().setVotingOrdinaleData(nomePartitoGruppo.getText()) == false) {
					name_control = false;
					party_error();
				}
				else
					name_control = true;
			}
    		nomePartitoGruppo.setText(null);
		 }
		 if(name_control) {
			 party++;
			 if(party <= n_parties) {
				 if(s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
					 labelInfo.setText("Prego inserire il nome del gruppo / partito\n/ candidato n° " + party);
				 else
					 labelInfo.setText("Prego inserire il nome del partito n° " + party + "\ne il numero di candidati appartenenti ad esso");
				insertDataParty();
			 }
			 else {
				 if(n_parties>1) {
				 	if(s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
				 		labelInfo.setText("Sono stati inseriti n°" + n_parties + " gruppi / partiti\n/ candidati");
				 	else
					 labelInfo.setText("Sono stati inseriti n°" + n_parties + " partiti e/o gruppi");
				 }
				 else {
					if(s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
					 	labelInfo.setText("E' stato inserito 1 gruppo / partito / candidato");
				 	else
					 labelInfo.setText("E' stato inserito 1 partito o gruppo");
				 }
				 updateChoices(false);
			 }
		 }
	 }
	 
	 private void party_error() {
		 if(s.getSession().getVoting().getVotingMode().equals("voto ordinale"))
			 labelInfo.setText("Inserimento annullato: risulta già inserito\nun elemento con tale nome\nPrego inserire il nome del gruppo / partito\n/ candidato n° " + party);
		 else
			 labelInfo.setText("Inserimento annullato: risulta già inserito\nun elemento con tale nome\nPrego inserire il nome del gruppo / partito n° " + party + "\ne il numero di candidati appartenenti ad esso");
		 insertDataParty();
	 }

	public void insertDataParty() {
		updateChoices(false);
		box_textField.setVisible(true);
		if(!s.getSession().getVoting().getVotingMode().equals("voto ordinale")) {
			spinner.setValueFactory(candidates_selector);
			candidates_selector = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
	    	box_spinner.setVisible(true);
		}
	}

	@FXML
    void goBack(ActionEvent event) {
		votingData();
    }
	
	private void votingData() {
		changeView("votingDataView", s);
	}
}