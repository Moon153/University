package commons;

public class SessionPhase {
	protected String state = "closed";
	private static String votingMode;
	private static String esitMode;
	protected static SessionData data = new SessionData();
	
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getVotingMode() {
		return votingMode;
	}
	
	public void setVotingMode(String mode) {
		votingMode = mode;
	}
	
	public String getEsitMode() {
		return esitMode;
	}
	
	public void setEsitMode(String mode) {
		esitMode = mode;
	}
	
	public String getReferendumData() {
		return data.getQuesito();
	}
	public int getPartiesNumber() {
		return data.getPartiesNumber();
	}

	public String[] getPartiesList() {
		return data.getPartiesList();
	}
	
	public int getPlannedCandidates(String gruppo_o_partito) {
		return data.getPlannedCandidates(gruppo_o_partito);
	}

	public int getCandidatesNumber(String gruppo_o_partito) {
		return data.getCandidatesNumber(gruppo_o_partito);
	}

	public String[] getCandidatesList(String gruppo_o_partito) {
		return data.getCandidatesList(gruppo_o_partito);
	}
	
	public String[] getCandidatesGenreList(String gruppo_o_partito, String candidato) {
		return data.getCandidatesGenreList(gruppo_o_partito, candidato);
	}

	public String viewEsit() {
		return data.getEsito();
	}
	
	public String[] getEsitoCandidati() {
		return data.getEsitoCandidati();
	}
}
