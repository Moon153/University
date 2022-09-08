package testing;
import static org.junit.Assert.*;
import org.junit.Test;

import common.Voto;
import phases.BallotPhase;
import phases.ElectorPhase;
import phases.VotingPhase;

public class BallotingTest {

	ElectorPhase elector = new ElectorPhase();
	VotingPhase voting = new VotingPhase();
	BallotPhase ballot = new BallotPhase();
	
	@Test
	public void quorumTest(){
		ballot.setEsit("quorum");
		assertEquals(ballot.getEsitMode(), "quorum");
		voting.setNumeroElettori(5);
		Voto v1 = new Voto();
		v1.astenuto();
		elector.addVotoControllo(v1);
		System.out.println(ballot.getNumeroVoti());
		elector.addVoto(v1);
		System.out.println(ballot.getNumeroVoti());
		Voto v2 = new Voto();
		v2.setReferendum("favorevole");
		elector.addVotoControllo(v2);
		System.out.println(ballot.getNumeroVoti());

		//elector.addVoto(v2);
		//ballot.calcolaQuorum();
		//assertEquals(ballot.viewEsit(), "Quorum non raggiunto");
		//elector.addVoto(v1);
		System.out.println(ballot.getElettori());
		ballot.calcolaQuorum();
		String esit = "Nessuna opinione ha raggiunto la\nmaggioranza per il quesito:\n" + ballot.getReferendumData();
		assertEquals(ballot.viewEsit(), esit);
		
	}
}
