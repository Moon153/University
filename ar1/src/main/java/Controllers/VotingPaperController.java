package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import Phases.ElectorPhase;
import ar1.Controller;
import ar1.Voto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class VotingPaperController extends Controller{

	private ElectorPhase phase;
	private int population;
	private boolean candidato;
	private String[] candidati_disponibili;
	private ArrayList <String> candidati_scelti = new ArrayList <String>();
	private boolean conferma = false;
	private Voto voto;
	private ObservableList<String> gruppi_partiti;
	private ObservableList<String> candidati;
	
	@FXML
    private CheckBox astenuto;

    @FXML
    private Button back;

    @FXML
    private Button info;
    
    @FXML
    private HBox box_candidate;

    @FXML
    private HBox box_party;

    @FXML
    private HBox box_choice;

    @FXML
    private ChoiceBox<String> candidate;

    @FXML
    private CheckBox contrario;

    @FXML
    private CheckBox favorevole;

    @FXML
    private Label labelInfo;
    
    @FXML
    private Label labelElement;

    @FXML
    private ChoiceBox<String> party;

    @FXML
    private Button send;
    
    @Override
	public void setParameters(Object phase, int population) {
	    	this.phase = (ElectorPhase) phase;
	    	this.population = population;
 	}
    
    @Override
    public void init() {
    	voto = new Voto();
    	voto.astenuto();
    	phase.addVotoControllo(voto);
    	voto = new Voto();
    	switch(phase.getVotingMode()) {
    		case "voto ordinale":
    			ordinale();
    			break;
    		case "voto categorico":
    			categorico();
    			break;
    		case "voto categorico con preferenze":
    			preferenze();
    			break;
    		case "referendum":
    			referendum();
    	}
    }
    
    private void preferenze() {
    	back.setVisible(false);
		labelInfo.setText("Prego indicare la propria preferenza\ndi gruppo / partito");
		send.setText("Seleziona gruppo/partito");
    	party();
	}

	private void categorico() {
		back.setVisible(false);
		labelInfo.setText("Prego indicare la propria preferenza\ndi gruppo / partito");
		send.setText("Seleziona gruppo/partito");
		party();
	}

	private void ordinale() {
		back.setVisible(false);
		labelInfo.setText("Prego indicare la propria preferenza");
		send.setText("Seleziona elemento");
		labelElement.setText("Elementi:");
		party();
	}

	private void referendum() {
		back.setVisible(false);
    	labelInfo.setText(phase.getReferendumData());
		box_choice.setVisible(true);
		send.setText("Invia voto");
	}

	private void party() {
		box_party.setVisible(true);	
		gruppi_partiti = FXCollections.observableArrayList(phase.getPartiesList());
		party.setItems(gruppi_partiti);
	}
	
	private void vote_candidate() {
		box_choice.setVisible(true);
		favorevole.setText("Indicare");
		contrario.setText("Non indicare");
		send.setText("Invia scelta");
	}
	
	private void candidate() {
		disponibili();
		if(phase.getVotingMode().compareToIgnoreCase("voto ordinale")!=0) {
			labelInfo.setText("Prego indicare la propria preferenza\ndi candidato");
			candidati = FXCollections.observableArrayList(candidati_disponibili);
			candidate.setItems(candidati);
			box_candidate.setVisible(true);
		}
		else {
			labelInfo.setText("Prego indicare la propria preferenza");
			gruppi_partiti = FXCollections.observableArrayList(candidati_disponibili);
			party.setItems(gruppi_partiti);
		}
		box_choice.setVisible(false);
		candidato=true;
		send.setText("Invia preferenza");
	}
	
	private void disponibili() {
		int length =0;
		if(candidati_scelti.size()==0)
			candidati_disponibili = phase.getCandidatesList(party.getValue());
		else
			if(phase.getVotingMode().compareToIgnoreCase("voto categorico")!=0) {
				length = candidati_disponibili.length;
				for(int i=0; i<length;i++)
					if(candidati_scelti.contains(candidati_disponibili[i])) {
						if(i==length-1)
							candidati_disponibili = Arrays.copyOfRange(candidati_disponibili, 0, i);
						else {
							candidati_disponibili[i]=candidati_disponibili[length-1];
							candidati_disponibili = Arrays.copyOfRange(candidati_disponibili, 0, length-1);
							length--;
						}
					}
			}
			else {
				candidati_disponibili = phase.getCandidatesGenreList(party.getValue(), candidati_scelti.get(0));
			}
	}

	@FXML
    void goBack(ActionEvent event) {
		if((phase.getVotingMode().equals("referendum") && !conferma)||((!phase.getVotingMode().equals("referendum")) && party.getValue() == null && !conferma))
			changeView("loginView", null);
		else {
			changeView("votingPaperView", phase, population);
			conferma = false;
		}
    }
	
	@FXML
    void viewInfo(ActionEvent event) {
		changeView("infoView", phase, population);
    }

    @FXML
    void sceltaContraria(ActionEvent event) {
    	favorevole.setVisible(!contrario.isSelected());
    	if(phase.getVotingMode().equals("referendum"))
    		astenuto.setVisible(!contrario.isSelected());
    }

    @FXML
    void sceltaFavorevole(ActionEvent event) {
    	contrario.setVisible(!favorevole.isSelected());
    	if(phase.getVotingMode().equals("referendum"))
    		astenuto.setVisible(!favorevole.isSelected());
    }

    @FXML
    void schedaBianca(ActionEvent event) {
    	contrario.setVisible(!astenuto.isSelected());
    	favorevole.setVisible(!astenuto.isSelected());
    	if(!phase.getVotingMode().equals("referendum"))
    		box_party.setVisible(!astenuto.isSelected());
    }

    @FXML
    void sendData(ActionEvent event) {
    	if(!conferma) {					//voto non ancora eseguito
    		if(astenuto.isSelected()) {			//voto astenuto
    			labelInfo.setText("Si desidera confermare di voler\nindicare come voto: astenuto?");
    			confirm();
    		}
    		else {
		    	if(phase.getVotingMode().equals("referendum")&&(favorevole.isSelected()||contrario.isSelected())){		//voto referendum
		    		if(favorevole.isSelected())
		    			labelInfo.setText("Si desidera confermare di essere\nfavorevoli al quesito:\n" + phase.getReferendumData() + "?");
		    		if(contrario.isSelected())
		    			labelInfo.setText("Si desidera confermare di essere\ncontrari al quesito:\n" + phase.getReferendumData() + "?");
		    		confirm();
		    	}
		    	if(!phase.getVotingMode().equals("referendum")&&party.getValue()!=null){				//voto categorico o ordinale o categorico con preferenze
		    		if(!candidato&&!(favorevole.isSelected()||(contrario.isSelected()))&&candidate.getValue()==null){			//scelta del gruppo / partito / elemento
		    			if(phase.getVotingMode().equals("voto ordinale")) {	//voto ordinale
		    				candidati_scelti.add(party.getValue());
		    				candidati_disponibili = phase.getPartiesList();
			    			candidate();
		    			}
		    			else{
			    			if((phase.getCandidatesList(party.getValue())).length!=0) {	//voto categorico o categorico con preferenze
				    			update_visibility();
				    			labelInfo.setText("Si desidera indicare una preferenza\ndi candidato?");
				    			vote_candidate();
			    			}
			    			else {
			    	   			labelInfo.setText("Si desidera confermare di voler\nindicare come voto\nil gruppo / partito: " + party.getValue() + "?");
				    	   		confirm();
			    			}
		    			}
		    		}
		    		if(!candidato) {		//voto categorico o categorico con preferenze, scelta se indicare candidati
			    	   	if(favorevole.isSelected()) {
			    	   		update_visibility();
			    	   		candidate();
			    	   	}
			    	   	if(contrario.isSelected()){
			    	   		indicaVoto();
			    	   	}
		    		}
		    		else{
		    			if(candidate.getValue()!=null || (phase.getVotingMode().equals("voto ordinale")&& party.getValue()!=null)){ //voto ordinale sviluppo
			    			if(phase.getVotingMode().equals("voto ordinale")){
			    				candidati_scelti.add(party.getValue());
	    						if((candidati_scelti.size())==(phase.getPartiesList().length)) {
	    							labelInfo.setText("Si desidera confermare il\nseguente ordinamento?");
	    							astenuto.setVisible(false);
	    							gruppi_partiti = FXCollections.observableArrayList(candidati_scelti);
	    							party.setItems(gruppi_partiti);
	    							send.setVisible(true);
	    							send.setText("Conferma voto");
	    							conferma = true;
	    							back.setVisible(true);
	    						}
	    						else
	    							candidate();
	    					}
			    			else {		//voto categorico o ordinale o categorico con preferenze, sviluppo
			    				update_visibility();
				    			candidati_scelti.add(candidate.getValue());
				    			if(phase.getVotingMode().equals("voto categorico con preferenze") && (candidati_scelti.size())==(phase.getCandidatesList(party.getValue()).length)){
				    				labelInfo.setText("Si desidera confermare di voler\nindicare come voto\nl'intero gruppo / partito: " + party.getValue() + "?");
					    	   		confirm();
				    			}
				    			else {
				    				if(phase.getVotingMode().equals("voto categorico con preferenze") || (phase.getVotingMode().equals("voto categorico") && population >= 25000 && candidati_scelti.size()<2)) {
				    					if(phase.getVotingMode().equals("voto categorico") && population >= 25000 && candidati_scelti.size()==1) {
				    						if(phase.getCandidatesGenreList(party.getValue(), candidati_scelti.get(0)).length == 0)
				    							indicaVoto();
				    						else{
								    			labelInfo.setText("Si desidera indicare ulteriori \npreferenze di candidati?");
								    			favorevole.setSelected(false);
								    			contrario.setVisible(true);
								    			candidato = false;
								    			vote_candidate();
				    						}				    						
				    					}
			    						else{
							    			labelInfo.setText("Si desidera indicare ulteriori \npreferenze di candidati?");
							    			favorevole.setSelected(false);
							    			contrario.setVisible(true);
							    			candidato = false;
							    			vote_candidate();
			    						}
				    				}
				    				else
				    					indicaVoto();
				    			}
			    			}
		    			}
		    		}
		    	}
		    }
    	}
    	else {
    		if(astenuto.isSelected())
    			voto.astenuto();
    		else {
		    	if(phase.getVotingMode().equals("referendum")) {
		    		if(favorevole.isSelected())
		    			voto.setReferendum("favorevole");
		    		else
		    			voto.setReferendum("contrario");
		    	}
		    	else{
		    		if(phase.getVotingMode().equals("voto ordinale"))
		    			voto.setCandidati(candidati_scelti, null);
		    		else
		    			voto.setCandidati(candidati_scelti, party.getValue());
		    	}
    		}
    		phase.addVoto(voto);
    		changeView("loginView", null);
    	}
    }
    
    private void indicaVoto(){
    	String selezionati = "";
   		if(candidati_scelti.size() > 0) {
   			selezionati = selezionati.concat("\nindicando i candidati:\n");
   			for(int i=0; i<candidati_scelti.size(); i++)
   				selezionati = selezionati.concat(candidati_scelti.get(i) + " ");
   		}
   		labelInfo.setText("Si desidera confermare di voler\nindicare come voto: " + party.getValue() + selezionati + "?");
   		confirm();
    }
    
    private void update_visibility(){
    	box_choice.setVisible(false);
		box_party.setVisible(false);
		box_candidate.setVisible(false);
    }

	private void confirm() {
		update_visibility();
		astenuto.setVisible(false);
		send.setText("Conferma voto");
		conferma = true;
		back.setVisible(true);
	}
}