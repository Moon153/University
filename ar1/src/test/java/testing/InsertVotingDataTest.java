package testing;
import static org.junit.Assert.*;
import org.junit.Test;

import Phases.VotingPhase;
import ar1.Candidato;

public class InsertVotingDataTest {	
	
	VotingPhase voting = new VotingPhase();
	
	@Test
	public void testInsertReferendum() {
		voting.deleteVotingData();
		voting.setMode("referendum");
		voting.setReferendumData("quesito");
		assertEquals("referendum", voting.getVotingMode());
		assertEquals("quesito", voting.getReferendumData());
		assertTrue(voting.getPartiesList().length==0);
	}
	
	@Test
	public void testDeletingData() {
		voting.deleteVotingData();
		assertTrue(voting.getReferendumData()=="");
		assertTrue(voting.getPartiesList().length==0);
	}
	
	@Test
	public void testInsertOrdinale() {
		voting.setMode("voto ordinale");
		voting.setVotingOrdinaleData("1");
		voting.setVotingOrdinaleData("2");
		voting.setVotingOrdinaleData("3");
		assertEquals("voto ordinale", voting.getVotingMode());
		String[] ordinaleTest1 = {"1", "2", "3"};
		assertTrue(ordinaleTest1.length == voting.getPartiesList().length);
		for(int i=0; i<voting.getPartiesList().length;i++) 
			assertTrue(ordinaleTest1[i] == voting.getPartiesList()[i]);
		assertTrue(voting.getPartiesNumber() == voting.getPartiesList().length);
		String[] ordinaleTest2 = {"1", "2"};
		voting.deletePartyCandidateOrdinale("3");
		assertTrue(ordinaleTest2.length == voting.getPartiesNumber());
		for(int i=0; i<voting.getPartiesList().length;i++) 
			assertTrue(ordinaleTest2[i] == voting.getPartiesList()[i]);
		voting.deleteVotingData();
	}

	@Test
	public void testInsertCategorico() {		//test uguale per categorico con preferenze, l'unica differenza è che al posto del genere si ha ' '
		boolean check; 
		voting.setMode("voto categorico");
		check = voting.setVotingCategoricoData("g1",  2, null, ' ');
		assertTrue(check);
		check = voting.setVotingCategoricoData("g1",  5, null, ' ');
		assertTrue(!check);
		check = voting.setVotingCategoricoData("g1",  0, "c1", 'm');
		assertTrue(check);
		check = voting.setVotingCategoricoData("g1",  0, "c1", 'm');
		assertTrue(!check);
		check = voting.setVotingCategoricoData("g1",  0, "c1", 'f');
		assertTrue(!check);
		check = voting.setVotingCategoricoData("g1",  0, "c2", 'f');
		assertTrue(check);
		check = voting.setVotingCategoricoData("g2",  1, null, ' ');
		assertTrue(check);
		check = voting.setVotingCategoricoData("g2",  0, "c3", 'f');
		assertTrue(check);
		assertEquals("voto categorico", voting.getVotingMode());
		Candidato[][] categoricoTest1 = new Candidato[2][];
		categoricoTest1[0] = new Candidato[3];
		categoricoTest1[1] = new Candidato[2];
		categoricoTest1[0][0] = new Candidato("g1", ' ');
		categoricoTest1[1][0] = new Candidato("g2", ' ');
		categoricoTest1[0][1] = new Candidato("c1", 'm');
		categoricoTest1[0][2] = new Candidato("c2", 'f');
		categoricoTest1[1][1] = new Candidato("c3", 'f');
		assertTrue(categoricoTest1.length == voting.getPartiesNumber());
		assertTrue(categoricoTest1[0][0].getNome() == "g1");
		assertTrue(categoricoTest1[1][0].getNome() == "g2");
		assertTrue(categoricoTest1[0][1].getNome() == "c1");
		assertTrue(categoricoTest1[0][2].getNome() == "c2");
		assertTrue(categoricoTest1[1][1].getNome() == "c3");
	}
}