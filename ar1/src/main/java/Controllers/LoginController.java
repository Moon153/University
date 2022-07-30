package Controllers;

import Interfaces.UtenteDao;
import Users.Scrutatore;
import ar1.Controller;
import ar1.Session;
import ar1.UtenteDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller{
	
	private UtenteDao utente = new UtenteDaoImpl();

    @FXML
    private Button buttonLogin;

    @FXML
    private Label okLogin;
    
    @FXML
    private TextField user;
    
    @FXML
    private PasswordField pass;

    @FXML
    private ChoiceBox<String> sceltaRuoli;
   
    private ObservableList<String> ruoli = FXCollections.observableArrayList("Elettore", "Scrutatore");
    
    @FXML
    public void initialize() {
    	sceltaRuoli.setValue("Elettore");
    	sceltaRuoli.setItems(ruoli);
    }
    
    @FXML
    public void checkLogin(ActionEvent event) {
    	boolean check = utente.verificaUtente(sceltaRuoli.getValue(), user.getText(), pass.getText());
       	if(check) {
      		if(sceltaRuoli.getValue()=="Scrutatore") {
      			changeView("sessionView", new Scrutatore(), utente.getNumeroElettori());
      		}
      		else {
      			if(Session.getInstance().getElector().getState().equals("closed"))
					okLogin.setText("Prego attendere l'apertura delle elezioni");
      			else {
      				changeView("waitingElectorView", Session.getInstance().getElector().getElector(user.getText(), utente.getPopulation(user.getText())));
      			}
      		}
      	}
    	else
    		okLogin.setText("Non e' possibile accedere con tali credenziali al ruolo \nselezionato; ricontrollare i dati inseriti");
    }    
}