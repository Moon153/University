package testing;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import commons.Voto;
import phases.BallotPhase;
import phases.ElectorPhase;
import phases.VotingPhase;

public class BallotingTest {
	
	Voto controllo = new Voto();
	Voto v1 = new Voto();
	Voto v2 = new Voto();
	Voto v3 = new Voto();
	Voto v4 = new Voto();
	Voto v5 = new Voto();
	
	public void initializeReferendumTest() {
		controllo.astenuto();
		v1.astenuto();
		v2.setReferendum("favorevole");
		v3.setReferendum("contrario");
	}
	
	public void initializeOrdinaleTest() {
		controllo.astenuto();
		v1.astenuto();
		ArrayList <String> voto2 = new ArrayList <String>();
		voto2.add("1");
		voto2.add("2");
		voto2.add("3");
		v2.setCandidati(voto2, null);
	}
	
	public void initializeCategoricoTest() {
		controllo.astenuto();
		v1.astenuto();
		ArrayList <String> voto2 = new ArrayList <String>();
		voto2.add("1");
		voto2.add("2");
		v2.setCandidati(voto2, "0");
		v3.setCandidati(voto2, "1");
		ArrayList <String> voto4 = new ArrayList <String>();
		voto4.add("1");
		v4.setCandidati(voto4, "0");
		ArrayList <String> voto5 = new ArrayList <String>();
		v5.setCandidati(voto5, "0");
	}
	
	
	@Test
	public void quorumTest(){
		initializeReferendumTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("quorum");
		assertEquals(ballot.getEsitMode(), "quorum");
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		assertEquals(ballot.getNumeroVoti(), 1);
		ballot.performBallot("referendum");
		assertEquals(ballot.viewEsit(), "Quorum non raggiunto");
	}
	
	@Test
	public void quorumFavorevoleTest(){
		initializeReferendumTest();		
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("quorum");
		assertEquals(ballot.getEsitMode(), "quorum");		
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("referendum");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori è favorevole\nal quesito:\n" + ballot.getReferendumData());
	}
	
	@Test
	public void noQuorumContrarioTest(){
		initializeReferendumTest();		
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("noQuorum");
		assertEquals(ballot.getEsitMode(), "noQuorum");		
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		elector.addVotoControllo(controllo);
		elector.addVoto(v3);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("referendum");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori è contraria\nal quesito:\n" + ballot.getReferendumData());
	}

	@Test
	public void quorumNulloTest(){
		initializeReferendumTest();		
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("quorum");
		assertEquals(ballot.getEsitMode(), "quorum");		
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		elector.addVotoControllo(controllo);
		elector.addVoto(v3);
		assertEquals(ballot.getNumeroVoti(), 3);
		ballot.performBallot("referendum");
		assertEquals(ballot.viewEsit(), "Nessuna opinione ha raggiunto la\nmaggioranza per il quesito:\n" + ballot.getReferendumData());
	}
	
	@Test
	public void ordinaleNulloTest(){
		initializeOrdinaleTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("maggioranza");
		assertEquals(ballot.getEsitMode(), "maggioranza");
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		assertEquals(ballot.getNumeroVoti(), 1);
		ballot.performBallot("voto ordinale");
		assertEquals(ballot.viewEsit(), ("Nessun candidato/gruppo/partito ha raggiunto la\nmaggioranza dei voti"));
	}
	
	@Test
	public void ordinaleFavorevoleTest(){
		initializeOrdinaleTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		voting.setVotingOrdinaleData("1");
		voting.setVotingOrdinaleData("2");
		voting.setVotingOrdinaleData("3");
		ballot.setEsit("maggioranza");
		assertEquals(ballot.getEsitMode(), "maggioranza");
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		assertEquals(ballot.getNumeroVoti(), 1);
		ballot.performBallot("voto ordinale");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori ha preferito\nil candidato/gruppo/partito: 1");
	}
	
	@Test
	public void ordinaleNonAssolutoTest(){
		initializeOrdinaleTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		voting.setVotingOrdinaleData("1");
		voting.setVotingOrdinaleData("2");
		voting.setVotingOrdinaleData("3");
		ballot.setEsit("assoluta");
		assertEquals(ballot.getEsitMode(), "assoluta");
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto ordinale");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori ha preferito\nil candidato/gruppo/partito: 1,\nma non ha raggiunto la maggioranza assoluta dei voti");
	}

	@Test
	public void ordinaleAssolutoTest(){
		initializeOrdinaleTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		voting.setVotingOrdinaleData("1");
		voting.setVotingOrdinaleData("2");
		voting.setVotingOrdinaleData("3");
		ballot.setEsit("assoluta");
		assertEquals(ballot.getEsitMode(), "assoluta");
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto ordinale");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori ha preferito\nil candidato/gruppo/partito: 1\npermettendo di raggiungere la maggioranza assoluta");
	}

	@Test
	public void CategoricoNulloTest(){			//per i test successivi notare che tra voto categorico e categorico con preferenze non ci sono differenze in BallotPhase
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("maggioranza");
		assertEquals(ballot.getEsitMode(), "maggioranza");
		voting.setNumeroElettori(3);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		assertEquals(ballot.getNumeroVoti(), 1);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), "Non è stata raggiunta una maggioranza.");
	}
	
	@Test
	public void CategoricoPartitiTest(){			
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("maggioranza");
		assertEquals(ballot.getEsitMode(), "maggioranza");
		voting.setNumeroElettori(3);
		voting.setVotingCategoricoData("0", 2, null, '0');
		voting.setVotingCategoricoData("0", 0, "1", '0');
		voting.setVotingCategoricoData("0", 0, "2", '0');
		voting.setVotingCategoricoData("1", 2, null, '0');
		voting.setVotingCategoricoData("1", 0, "1", '0');
		voting.setVotingCategoricoData("1", 0, "2", '0');
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		elector.addVotoControllo(controllo);
		elector.addVoto(v3);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), ("Non è stata raggiunta una maggioranza.\nI gruppi / partiti che hanno raggiunto il\nmaggior numero di voti sono:"));
		String[] esito = {"0", "1"};
		assertEquals(esito.length, ballot.getEsitoCandidati().length);
		for(int i = 0; i<esito.length;i++)
			assertEquals(esito[i], ballot.getEsitoCandidati()[i]);
	}

	@Test
	public void CategoricoPartitoNonAssolutoTest(){			
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("assoluta");
		assertEquals(ballot.getEsitMode(), "assoluta");
		voting.setNumeroElettori(3);
		voting.setVotingCategoricoData("0", 2, null, '0');
		voting.setVotingCategoricoData("0", 0, "1", '0');
		voting.setVotingCategoricoData("0", 0, "2", '0');
		voting.setVotingCategoricoData("1", 2, null, '0');
		voting.setVotingCategoricoData("1", 0, "1", '0');
		voting.setVotingCategoricoData("1", 0, "2", '0');
		elector.addVotoControllo(controllo);
		elector.addVoto(v4);
		elector.addVotoControllo(controllo);
		elector.addVoto(v1);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), "Il partito / gruppo che ha ottenuto il maggior numero\ndi voti è: 0\nma non è stata raggiunta una maggioranza assoluta");
	}
	
	@Test
	public void CategoricoCandidatiTest(){			
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("maggioranza");
		assertEquals(ballot.getEsitMode(), "maggioranza");
		voting.setNumeroElettori(3);
		voting.setVotingCategoricoData("0", 2, null, '0');
		voting.setVotingCategoricoData("0", 0, "1", '0');
		voting.setVotingCategoricoData("0", 0, "2", '0');
		voting.setVotingCategoricoData("1", 2, null, '0');
		voting.setVotingCategoricoData("1", 0, "1", '0');
		voting.setVotingCategoricoData("1", 0, "2", '0');
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), "Il partito / gruppo vincitore è : 0.\nNon è stata raggiunta una maggioranza tra i\ncandidati; coloro che hanno ricevuto il maggior\nnumero di voti sono:");
		String[] esito = {"1", "2"};
		assertEquals(esito.length, ballot.getEsitoCandidati().length);
		for(int i = 0; i<esito.length;i++)
			assertEquals(esito[i], ballot.getEsitoCandidati()[i]);
	}
	
	@Test
	public void CategoricoCandidatoAssolutoTest(){			
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("assoluta");
		assertEquals(ballot.getEsitMode(), "assoluta");
		voting.setNumeroElettori(3);
		voting.setVotingCategoricoData("0", 2, null, '0');
		voting.setVotingCategoricoData("0", 0, "1", '0');
		voting.setVotingCategoricoData("0", 0, "2", '0');
		voting.setVotingCategoricoData("1", 2, null, '0');
		voting.setVotingCategoricoData("1", 0, "1", '0');
		voting.setVotingCategoricoData("1", 0, "2", '0');
		elector.addVotoControllo(controllo);
		elector.addVoto(v2);
		elector.addVotoControllo(controllo);
		elector.addVoto(v4);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori ha votato\nil candidato: 1\ndel partito / gruppo: 0\npermettendo di raggiungere la maggioranza assoluta");
	}
	
	@Test
	public void CategoricoCandidatoNonAssolutoTest(){			
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("assoluta");
		assertEquals(ballot.getEsitMode(), "assoluta");
		voting.setNumeroElettori(3);
		voting.setVotingCategoricoData("0", 2, null, '0');
		voting.setVotingCategoricoData("0", 0, "1", '0');
		voting.setVotingCategoricoData("0", 0, "2", '0');
		voting.setVotingCategoricoData("1", 2, null, '0');
		voting.setVotingCategoricoData("1", 0, "1", '0');
		voting.setVotingCategoricoData("1", 0, "2", '0');
		elector.addVotoControllo(controllo);
		elector.addVoto(v4);
		elector.addVotoControllo(controllo);
		elector.addVoto(v5);
		assertEquals(ballot.getNumeroVoti(), 2);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori ha votato\nil candidato: 1\ndel partito / gruppo: 0,\nma non ha raggiunto la maggioranza assoluta dei voti");
	}
	
	@Test
	public void CategoricoPartitoMaggioranzaTest(){			
		initializeCategoricoTest();
		ElectorPhase elector = new ElectorPhase();
		VotingPhase voting = new VotingPhase();
		BallotPhase ballot = new BallotPhase();
		ballot.setEsit("maggioranza");
		assertEquals(ballot.getEsitMode(), "maggioranza");
		voting.setNumeroElettori(3);
		voting.setVotingCategoricoData("0", 2, null, '0');
		voting.setVotingCategoricoData("0", 0, "1", '0');
		voting.setVotingCategoricoData("0", 0, "2", '0');
		voting.setVotingCategoricoData("1", 2, null, '0');
		voting.setVotingCategoricoData("1", 0, "1", '0');
		voting.setVotingCategoricoData("1", 0, "2", '0');
		elector.addVotoControllo(controllo);
		elector.addVoto(v4);
		assertEquals(ballot.getNumeroVoti(), 1);
		ballot.performBallot("voto categorico");
		assertEquals(ballot.viewEsit(), "La maggioranza degli elettori ha votato\nil candidato: 1\ndel partito / gruppo: 0");
	}
}
