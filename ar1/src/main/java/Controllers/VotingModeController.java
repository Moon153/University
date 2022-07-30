package Controllers;

import Users.Scrutatore;
import ar1.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VotingModeController extends Controller{

	private Scrutatore s;
	private String mode;
	private int counter;

    @FXML
    private Button back;

    @FXML
    private Button cancel;

    @FXML
    private Button categorical;

    @FXML
    private Button categoricalPreference;

    @FXML
    private Button confirm;

    @FXML
    private Label labelInfo;

    @FXML
    private Button ordinal;

    @FXML
    private Button referendum;

    @Override
    public void setParameters(Object parameter) {
    	s = (Scrutatore) parameter;
    }
	
    @Override
    public void init() {
    	mode = null;
    	if(s.getSession().getVoting().getVotingMode() == null)
    		labelInfo.setText("Selezionare la modalità di votazione");
    	else {
    		updateButton();
    		labelInfo.setText("Risulta selezionata la modalità di \nvotazione: " + s.getSession().getVoting().getVotingMode() +"\nsi desidera modificare tale opzione?");
    	}
    }
    
    @FXML
	public void ordinalVote(ActionEvent event) {
    	mode = "voto ordinale";
    	labelInfo.setText("E' stata selezionata come modalità di \nvotazione: " + mode + "\nsi desidera confermare tale opzione?");
    	updateButton();
    }

    @FXML
    void categoricalVote(ActionEvent event) {
    	mode = "voto categorico";
    	labelInfo.setText("E' stata selezionata come modalità di \nvotazione: " + mode + "\nsi desidera confermare tale opzione?");
    	updateButton();
    }
    
    @FXML
    void categoricalPreferenceVote(ActionEvent event) {
    	mode = "voto categorico con preferenze";
    	labelInfo.setText("E' stata selezionata come modalità di \nvotazione: " + mode + "\nsi desidera confermare tale opzione?");
    	updateButton();
    }

    @FXML
    void referendumVote(ActionEvent event) {
    	mode = "referendum";
    	labelInfo.setText("E' stata selezionata come modalità di \nvotazione: " + mode + "\nsi desidera confermare tale opzione?");
    	updateButton();
    }
    
    @FXML
    void updateButton(){
    	ordinal.setVisible(false);
		categorical.setVisible(false);
		categoricalPreference.setVisible(false);
		referendum.setVisible(false);
		confirm.setVisible(true);
		cancel.setVisible(true);
    } 

    @FXML
	public void confirmChoice(ActionEvent event) {
    	if(s.getSession().getVoting().getVotingMode() == null) {
    		s.getSession().getVoting().setMode(mode);
    		if(mode.equals("referendum"))
    			changeView ("referendumDataView", s);
    		else
    			changeView ("votingDataView", s);
    	}
    	else {
    		if(counter == 0) {
    			labelInfo.setText("Ciò porterà alla perdita definitiva\ndei dati precedentemente inseriti;\nconfermare?");
    			counter++;
    		}
    		else {
    			s.getSession().getVoting().setMode(null);
    			s.getSession().getVoting().deleteVotingData();
    			changeView("votingModeView", s);
    		}
    	}
    }
    
    @FXML
    void cancelChoice(ActionEvent event) {
    	if(counter==0)
    		changeView ("votingInitView", s);
    	else
    		changeView ("votingModeView", s);    	
    }

    @FXML
    void goBack(ActionEvent event) {
    	changeView ("votingInitView", s);
    }

}
