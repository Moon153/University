package phases;

import common.SessionData;
import common.SessionPhase;

public class VotingPhase extends SessionPhase{
	
	public void setMode(String mode) {
		setVotingMode(mode);
	}
	
	public void setReferendumData(String quesito) {
		data.setQuesito(quesito);
	}
	
	public boolean setVotingCategoricoData(String gruppo_o_partito,  int n_candidati_gruppo, String candidato, char genere) {
		return data.setVotingCategorico(gruppo_o_partito,  n_candidati_gruppo, candidato, genere);
	}

	public void deleteVotingData() {
		data = new SessionData();
	}

	public void resizeParty(String gruppo_o_partito, int dimensione) {
		data.resizeParty(gruppo_o_partito, dimensione);
	}

	public void deletePartyCandidateCategorico(String gruppo_o_partito, String candidato) {
		data.deletePartyCandidateCategorico(gruppo_o_partito, candidato);
		
	}

	public boolean setVotingOrdinaleData(String elemento) {
		return data.setVotingOrdinale(elemento);
	}

	public void deletePartyCandidateOrdinale(String elemento) {
		data.deletePartyCandidateOrdinale(elemento);
	}
	
	public void setNumeroElettori(int n_elettori) {
		data.setElettori (n_elettori);
	}

}
