package controllers;

import common.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import users.Scrutatore;

public class PapersViewController extends Controller{

	private Scrutatore s;
	private int current;
	private int total;
	private ObservableList<String> candidati;

	
	@Override
    public void setParameters(Object scrutatore) {
    	s = (Scrutatore) scrutatore;
    }
	
    @FXML
    private Button back;

    @FXML
    private HBox box_candidates;

    @FXML
    private ChoiceBox<String> candidates;

    @FXML
    private Label labelCandidates;

    @FXML
    private Label labelInfo;

    @FXML
    private Button next;

    @FXML
    private Button previous;

    @Override
    public void init() {
    	total = s.getSession().getBallot().getNumeroVoti();
    	nextPaper(null);
    }
    
    @FXML
    void goBack(ActionEvent event) {
    	changeView("ballotInitView", s);
    }
    
    @FXML
    void nextPaper(ActionEvent event) {
    	current++;
    	if(current == 2)
    		previous.setVisible(true);
    	if(current == total){
    		next.setVisible(false);
    	}
    	changePaper();
    }

	@FXML
    void previousPaper(ActionEvent event) {
		current--;
    	if(current == 1)
    		previous.setVisible(false);
    	if(current == total - 1)
    		next.setVisible(true);
    	changePaper();
    }
	
	private void changePaper() {
		if(s.getSession().getBallot().getVotingMode().equals("referendum")) {
			labelInfo.setText("Scheda: " + current + "/" + total + "\n\n\nVoto indicato:    " + s.getSession().getBallot().getSceltaReferendum(current - 1));
			box_candidates.setVisible(false);
		}
		else {
			if(!(s.getSession().getBallot().getVotingMode().equals("voto ordinale"))) {
				labelInfo.setText("Scheda: " + current + "/" + total + "\n\n\nGruppo / Partito indicato: " + s.getSession().getBallot().getGruppoPartito(current - 1));
				if(s.getSession().getBallot().getGruppoPartito(current - 1).equals("astenuto")||s.getSession().getBallot().getCandidati(current-1).length == 0) {
					box_candidates.setVisible(false);
				}
				else {
					box_candidates.setVisible(true);
					candidati = FXCollections.observableArrayList(s.getSession().getBallot().getCandidati(current-1));
					candidates.setItems(candidati);
				}
			}
			else {
				if(s.getSession().getBallot().getCandidati(current-1)==null) {
					labelInfo.setText("Scheda: " + current + "/" + total + "\n\n\nVoto indicato:    astenuto");
					box_candidates.setVisible(false);
				}
				else {
					labelInfo.setText("Scheda: " + current + "/" + total);
					box_candidates.setVisible(true);
					labelCandidates.setText("Ordinamento inserito:");
					candidati = FXCollections.observableArrayList(s.getSession().getBallot().getCandidati(current-1));
					candidates.setItems(candidati);
				}
			}
		}
	}
}