package Controllers;

import Users.Scrutatore;
import ar1.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ReferendumDataController extends Controller{

	private Scrutatore s;
	private int counter;
	
    @FXML
    private Button back;
    
    @FXML
    private HBox box_quesito;

    @FXML
    private TextField campoQuesito;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Button send;
    
    @FXML
    private Label labelInfo;

    @Override
    public void setParameters(Object parameter) {
    	s = (Scrutatore) parameter;
    }
	
    @Override
    public void init() {
    	if(s.getSession().getVoting().getReferendumData().equals("")) {
    		labelInfo.setText("Prego inserire il quesito del referendum");
    		box_quesito.setVisible(true);
    		send.setVisible(true);
    	}
    	else{
    		labelInfo.setText("Risulta attualmente inserito il quesito:\n" + s.getSession().getVoting().getReferendumData() + "\nsi desidera effettuarne una modifica?");
    		confirm.setVisible(true);
    		cancel.setVisible(true);
    	}    		
    }
    
    @FXML
    void cancelChoice(ActionEvent event) {
    	if((!s.getSession().getVoting().getReferendumData().equals("")) && counter==0)
    		changeView("phaseView", s);
    	else
    		changeView("referendumDataView", s);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(s.getSession().getVoting().getReferendumData().equals("")) {
    		s.getSession().getVoting().setReferendumData(campoQuesito.getText());
    		changeView("phaseView", s);
    	}
    	else {
    		if(counter == 0) {
    			labelInfo.setText("Il quesito precedentemente inserito verrà \nrimosso definitivamente; continuare?");
    			counter++;
    		}
    		else {
    			s.getSession().getVoting().setReferendumData("");
    			changeView("referendumDataView", s);
    		}
    	}
    }

    @FXML
    void goBack(ActionEvent event) {
    	changeView("votingModeView", s);
    }

    @FXML
    void sendQuestion(ActionEvent event) {
    	if(!campoQuesito.getText().equals("")) {
	    	labelInfo.setText("Il quesito del referendum sarà:\n" + campoQuesito.getText() + "\nconfermare?");
	    	box_quesito.setVisible(false);
			send.setVisible(false);
			cancel.setVisible(true);
			confirm.setVisible(true);
    	}
    }
}
