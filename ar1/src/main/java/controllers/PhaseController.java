package controllers;

import common.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import users.Scrutatore;

public class PhaseController extends Controller{
	
	private Scrutatore s;
	private int counter;
	private int n_elettori;
	
	@Override
	public void setParameters(Object scrutatore, int n_elettori) {
		s = (Scrutatore) scrutatore;
		this.n_elettori = n_elettori;
	}
	
	@Override
	public void setParameters(Object scrutatore) {
		s = (Scrutatore) scrutatore;
	}
	
    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Label labelPhase;
    
    
    @Override
    public void init(){
    	if(s.getSession().phaseInfo().equals("closed")){
			labelPhase.setText("Non risulta attualmente presente\nalcuna fase di votazione.\nSi desidera procedere\nalla sua creazione?");
			return;
    	}
    	if(s.getSession().phaseInfo().equals("votazione")&&s.getSession().getVoting().getState().equals("concluded")){
			labelPhase.setText("Non risulta attualmente presente\nalcuna fase di scrutinio.\nSi desidera procedere\nalla sua creazione?");
			return;
    	}
    	if(s.getSession().phaseInfo().equals("scrutinio")&&s.getSession().getBallot().getState().equals("concluded")){
			labelPhase.setText("Non risulta attualmente presente\nalcuna fase di visualizzazione esito.\nSi desidera procedere\nalla sua creazione?");
			return;
    	}
		if(s.getSession().getVoting().getState().equals("closed")) {
			labelPhase.setText("Risulta attualmente presente una fase \ndi " + s.getSession().phaseInfo() + " chiusa .\nSi desidera procedere ad un \ninserimento / aggiornamento dati \no alla sua apertura?");
			return;
		}
		if((s.getSession().phaseInfo().equals("scrutinio")&& s.getSession().getBallot().getState().equals("closed")) || s.getSession().phaseInfo().equals("visualizzazione esito"))
			labelPhase.setText("Risulta attualmente presente una fase \ndi " + s.getSession().phaseInfo() + ".\nSi desidera accedere ad essa ?");
		else
			labelPhase.setText("Risulta attualmente attiva una fase \ndi "+ s.getSession().phaseInfo() + ".\nSi desidera procedere alla \nsua chiusura?");
    }
    
    @FXML
   public void cancelChoice(ActionEvent event) {
		changeView("sessionView", s, n_elettori);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(counter==0){
        	if(s.getSession().phaseInfo().equals("closed")||s.getSession().phaseInfo().equals("votazione")&&s.getSession().getVoting().getState().equals("concluded")|| s.getSession().phaseInfo().equals("scrutinio")&&s.getSession().getBallot().getState().equals("concluded"))
    			labelPhase.setText("Ciò non comporterà l’obbligo\ndi attivazione della stessa.\nSi desidera procedere?");
        	else {
        		if(s.getSession().phaseInfo().equals("votazione")) {
        			if(s.getSession().getVoting().getState().equals("closed") )
    					labelPhase.setText("Si prega di confermare la scelta di \ninserimento / aggiornamento  \ndati o apertura.");
        			else
						labelPhase.setText("Ciò comporterà l’impossibilità di \neffettuare altri voti da parte \ndegli utenti elettore.\nSi desidera procedere?");        				
        		}
        		else
        			labelPhase.setText("Si prega di confermare la scelta di \naccesso alla fase di " + s.getSession().phaseInfo() + ".");
        	}
			counter++;	
    	}
    	else {
    		if(s.getSession().phaseInfo().equals("closed")) {
    			s.getSession().newVoting();
    			s.getSession().getVoting().setNumeroElettori(n_elettori);
    			changeView("phaseView", s);
    		}
    		else {
    			if(s.getSession().phaseInfo().equals("votazione")) {
    				if(s.getSession().getVoting().getState().equals("closed"))
    					changeView("votingInitView", s);
    				else{
    					if(s.getSession().getVoting().getState().equals("open")) {
    						s.getSession().getVoting().setState("concluded");
    						s.getSession().setElectorPhase("waiting");
    					}
    					else {
    						s.getSession().newBallot();
    					}
    					changeView("phaseView", s);
    				}
    			}
    			else{
    				if(s.getSession().getBallot().getState().equals("closed"))
    					changeView("ballotInitView", s);
    				else{
    					if(s.getSession().phaseInfo().equals("scrutinio"))
    						s.getSession().newEsit();
    					changeView("esitInitView", s);
    				}    					
    			}
    		}
    	}
    }
}