package commons;

import phases.BallotPhase;
import phases.ElectorPhase;
import phases.EsitPhase;
import phases.VotingPhase;

public final class Session{

	private static Session instance;
	private String phase  = "closed";
	private VotingPhase votingPhase;
	private BallotPhase ballotPhase;
	private EsitPhase esitPhase;
	private ElectorPhase electorPhase = new ElectorPhase();
	
	public static Session getInstance() {
		if (instance==null) 
			instance= new Session();
		return instance;
	}
	
	public String phaseInfo() {
		return phase;
	}
	
	public void newVoting(){
		phase = "votazione";
		votingPhase = new VotingPhase();
	}
	
	public VotingPhase getVoting() {
		return votingPhase;
	}
	
	public void newBallot(){
		phase = "scrutinio";
		ballotPhase = new BallotPhase();
	}
	
	public BallotPhase getBallot() {
		return ballotPhase;
	}
	
	public void newEsit(){
		phase = "visualizzazione esito";
		esitPhase = new EsitPhase();
	}
	
	public EsitPhase getEsit() {
		return esitPhase;
	}

	public void setElectorPhase(String control) {
		electorPhase.setState(control);
	}
	
	public ElectorPhase getElector() {
		return electorPhase;
	}
}
