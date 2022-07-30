package Controllers;

import Phases.ElectorPhase;
import ar1.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InfoController extends Controller{

	private ElectorPhase phase;
	private int population;
	private String mode;
	private int page = 1;
	private int lastPage;
	
    @FXML
    private Button back;

    @FXML
    private Label labelInfo;

    @FXML
    private Button next;

    @FXML
    private Button previous;


    @Override
	public void setParameters(Object phase, int population) {
	    	this.phase = (ElectorPhase) phase;
	    	this.population = population;
 	}
    
    @Override
    public void init() {
    	mode = phase.getVotingMode();
    	if(mode == "referendum")
    		lastPage = 3;
    	else {
	    	if(mode == "voto ordinale")
	    		lastPage = 3;
	    	else {
	    		if(population >= 25.000)
	    			lastPage = 6;
	    		else
	    			lastPage = 5;
	    	}
    	}
    	changePage();
    }
    
    @FXML
    void goBack(ActionEvent event) {
    	changeView("votingPaperView", phase, population);
    }

    @FXML
    void nextPage(ActionEvent event) {
    	page++;
    	if(page == 2)
    		previous.setVisible(true);
    	if(page == lastPage){
    		next.setVisible(false);
    	}
    	changePage();
    }

	@FXML
    void previousPage(ActionEvent event) {
		page--;
    	if(page == 1)
    		previous.setVisible(false);
    	if(page == lastPage - 1)
    		next.setVisible(true);
    	changePage();
    }
	
	 private void changePage() {
			switch (page){
				case 1:
					labelInfo.setText("Nella scheda è possibile selezionare la\ncasella \"Mi astengo dal voto\": in tal  caso il\nvoto sarà equivalente a \"scheda bianca o nulla\"");
					break;
				case 2:
					if(mode == "referendum")
						labelInfo.setText("Sarà possibile indicare se si è favorevoli o\ncontrari al quesito del referendum tramite\ngli appositi tasti");
					else{
						if(mode == "voto ordinale")
							labelInfo.setText("Sarà possibile indicare uno per volta i\ngruppi / partiti / candidati \nsecondo l'ordine di preferenza");							
						else
							labelInfo.setText("Sarà possibile indicare il gruppo / partito\ndi preferenza");
					}
					break;
				case 3:
					if(mode == "referendum" || mode == "voto ordinale")
						labelInfo.setText("Una volta indicato il proprio voto sarà\nnecessario confermarlo o riformularlo\ntramite gli appositi tasti");
					else{
							labelInfo.setText("Sarà possibile procedere ad indicare un\ncandidato facente parte del gruppo /  partito\nselezionato o lasciare un voto generico");
					}
					break;
				case 4:
					labelInfo.setText("Nel caso si sia scelto di non indicare alcun\ncandidato sarà necessario confermare o\nriformulare il voto tramite gli appositi tasti");
					break;
				case 5:
					if(mode == "voto categorico") {
						if(population < 25000)
							labelInfo.setText("Una volta indicato il proprio voto sarà\nnecessario confermarlo o riformularlo\ntramite gli appositi tasti");
						else
							labelInfo.setText("Sarà possibile scegliere se indicare un\nsecondo candidato del gruppo / partito\nselezionato, purchè di sesso opposto al\ncandidato precedentemente selezionato");
					}
					else
						labelInfo.setText("Sarà possibile indicare ulteriori candidati tra\n quelli facenti parte del gruppo / partito\nselezionato");
					break;
				case 6:
					labelInfo.setText("Una volta indicato il proprio voto sarà\nnecessario confermarlo o riformularlo\ntramite gli appositi tasti");
			}
		}
}
