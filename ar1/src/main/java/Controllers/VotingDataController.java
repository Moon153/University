package Controllers;

import Users.Scrutatore;
import ar1.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class VotingDataController extends Controller{
	
	private Scrutatore s;
	private char choice;

	 @Override
	 public void setParameters(Object parameter) {
	    	s = (Scrutatore) parameter;
    	}
	
    @FXML
    private Button back;

    @FXML
    private HBox boxButtonsChoice;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Button insert;

    @FXML
    private Label labelInfo;

    @FXML
    private Button remove;

    @FXML
    private Button review;

    @FXML
    void goBack(ActionEvent event) {
    	changeView("votingInitView", s);
    }
    
    public void updateButtons() {
    	boxButtonsChoice.setVisible(true);
    	insert.setVisible(false);
    	remove.setVisible(false);
    	review.setVisible(false);
    }

    @FXML
    void insertData(ActionEvent event) {
    	labelInfo.setText("Prego confermare la scelta di inserimento\ndi nuovi dati");
    	choice = 'i';
    	updateButtons();
    }

    @FXML
    void removeData(ActionEvent event) {
    	if(s.getSession().getVoting().getPartiesNumber() == 0) {
    		labelInfo.setText("Non è possibilire passare alla \nrimozione dati poichè non\nrisulta alcun inserimento dati");
    		insert.setVisible(false);
        	remove.setVisible(false);
        	review.setVisible(false);
		}
		else {
	    	labelInfo.setText("Prego confermare la scelta di rimozione dati");
	    	choice = 'e';
	    	updateButtons();
		}
    }

    @FXML
    void reviewData(ActionEvent event) {
    	if(s.getSession().getVoting().getPartiesNumber() == 0) {
    		labelInfo.setText("Non è possibilire passare alla \nrevisione dati poichè non\nrisulta alcun inserimento dati");
    		insert.setVisible(false);
        	remove.setVisible(false);
        	review.setVisible(false);
		}
		else {
	    	labelInfo.setText("Prego confermare la scelta di revisione dati");
	    	choice = 'r';
	    	updateButtons();
		}
    }

    @FXML
    void cancelChoice(ActionEvent event) {
    	changeView ("votingDataView", s);
    }

    @FXML
    void confirmChoice(ActionEvent event) {
    	if(choice == 'i')
    		changeView ("insertPartiesView", s);
    	else {
    		if(choice == 'e')
    			changeView ("removeDataView", s);
    		else
    			changeView ("reviewDataView", s);
    	}
    }

}

