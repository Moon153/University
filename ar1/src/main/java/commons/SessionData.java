package commons;

import java.util.ArrayList;
import java.util.Arrays;

import users.Elettore;

public class SessionData {

	private String quesito="";
	private int favorevoli = 0;
	private int contrari = 0;
	private static String esito;
	private static String[] esitoCandidati;
	private static int[][] risultatiSchedeCategorico;			//matrice contenente il numero di voti per ciascun gruppo / partito / candidato
	private static int[] risultatiSchedeOrdinale;			//array contenente una casella per ciascun gruppo / partito / candidato
	private Candidato[][] candidati_categorico = new Candidato [100][];		//matrice contenente i gruppi / partiti e i relativi candidati
	private String[] candidati_ordinale = new String [100];
	private int n_gruppi_partiti;
	private int n_elettori;			//n° aventi diritto al voto
	private static ArrayList <Voto> voti = new ArrayList <Voto>();
	private ArrayList <Elettore> elettori = new ArrayList<Elettore>();
	
	public void setQuesito(String quesito) {
		this.quesito = quesito;
	}

	//@ ensures n_gruppi_partiti == (old)n_gruppi_partiti + 1 <==> gruppo_o_partito != null and n_candidati_gruppo != 0 and candidato == null and (\forall int i; i < n_gruppi_partiti; candidatiCategorico[i][0] != gruppo_o_partito);
	public boolean setVotingCategorico(String gruppo_o_partito, int n_candidati_gruppo, String candidato, char genere) {		//matrice bidimensionale per gruppi / partiti e i loro candidati, restituisce se elemento inserito è già presente oppure lo crea
		if(candidato == null) {		//elemento inserito è partito / gruppo
			for(int i = 0; i<n_gruppi_partiti; i++)
				if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0)
					return false;
			candidati_categorico[n_gruppi_partiti] = new Candidato[n_candidati_gruppo + 1];
			candidati_categorico[n_gruppi_partiti][0] = new Candidato(gruppo_o_partito, ' ');
			n_gruppi_partiti ++;
		}
		else {		//elemento inserito è candidato
			for(int i = 0; i<n_gruppi_partiti; i++) {
				if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0) {
					for(int j = 1; j <= getCandidatesNumber(gruppo_o_partito); j++)
						if(candidati_categorico[i][j].getNome().compareToIgnoreCase(candidato)==0)
							return false;
					candidati_categorico[i][getCandidatesNumber(gruppo_o_partito)+1] = new Candidato(candidato, genere);
				}
			}
		}
		return true;
	}
	
	public String getQuesito() {			//quesito referendum
		return quesito;
	}

	public int getPartiesNumber() {			//numero di partiti
		return n_gruppi_partiti;		
	}

	public String[] getPartiesList() {		//lista dei partiti o degli elementi di votazione ordinale
		String [] parties = new String [n_gruppi_partiti];
		if(candidati_ordinale[0]==null)
			for(int i = 0; i<n_gruppi_partiti; i++)
				parties[i] = candidati_categorico[i][0].getNome();
		else
			for(int i = 0; i<n_gruppi_partiti; i++)
				parties[i] = candidati_ordinale[i];
		return parties;
	}
	
	public int getPlannedCandidates(String gruppo_o_partito) {		//numero di candidati previsti per determinato partito
		for(int i = 0; i<n_gruppi_partiti; i++) {
			if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0)
				return (candidati_categorico[i].length) - 1;
		}
		return 0;		
	}
	
	public int getCandidatesNumber(String gruppo_o_partito) {	//numero di candidati attualmente inseriti per determinato partito
		int j = 0;
		for(int i = 0; i<n_gruppi_partiti; i++) {
			if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0){
				for(j = 1; j <= getPlannedCandidates(gruppo_o_partito); j++) {
					if(candidati_categorico[i][j] ==null){
						break;
					}
				}
			}
		}
		return j-1;		
	}

	public void resizeParty(String gruppo_o_partito, int dimensione) {		//ridimensionamenti partito, mantiene candidati già inseriti
		int z=0;
		Candidato[] resized = new Candidato[dimensione+1];
		for(int i = 0; i<n_gruppi_partiti; i++) {
			if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0){
				for(int j = 0; j <= getCandidatesNumber(gruppo_o_partito); j++) {
					resized[z] = candidati_categorico[i][j];
					z++;
					}
				candidati_categorico[i] = resized;
				break;
			}
		}
	}

	public String[] getCandidatesList(String gruppo_o_partito) {					//lista di candidati in base al gruppo / partito
		String[] lista;
		for(int i = 0; i<n_gruppi_partiti; i++)
			if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0) {
				lista = new String[getCandidatesNumber(gruppo_o_partito)];
				for(int j=0; j<getCandidatesNumber(gruppo_o_partito);j++)
					lista[j] = candidati_categorico[i][j+1].getNome();
				return lista;
			}
		return null;
	}
	
	public String[] getCandidatesGenreList(String gruppo_o_partito, String candidato) {				//lista di candidati in base al gruppo / partito di genere differente da quello del candidato specificato
		String[] lista;
		int dimensione=0;
		char genere;
		for(int i = 0; i<n_gruppi_partiti; i++)
			if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0) {
				genere = Candidato.getGenereInversoCandidato(candidati_categorico[i], candidato);
				lista = new String[getCandidatesNumber(gruppo_o_partito)];
				for(int j=1; j<getCandidatesNumber(gruppo_o_partito)+1;j++) {
					if(candidati_categorico[i][j].getGenere()==genere) {
						lista[dimensione] = candidati_categorico[i][j].getNome();
						dimensione++;
					}
				}
				lista = Arrays.copyOfRange(lista, 0, dimensione);
				return lista;
			}
		return null;
	}

	//@ ensures n_gruppi_partiti == (old)n_gruppi_partiti - 1 <==> gruppo_o_partito != null and candidato == null;
	public void deletePartyCandidateCategorico(String gruppo_o_partito, String candidato) {		//rimozione gruppo / partito o candidato
		if(candidato== null){
			for(int i = 0; i<n_gruppi_partiti; i++) {
				if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0){
					if(i != n_gruppi_partiti-1)
						candidati_categorico[i] = candidati_categorico[n_gruppi_partiti-1];
					n_gruppi_partiti--;
					return;
				}
			}
		}
		else {
			for(int i = 0; i<n_gruppi_partiti; i++) {
				if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0){
					for(int j = 0; j<=getCandidatesNumber(gruppo_o_partito); j++) {
						if(candidati_categorico[i][j].getNome().compareToIgnoreCase(candidato)==0){
							if(j!=getCandidatesNumber(gruppo_o_partito))
								candidati_categorico[i][j] = candidati_categorico[i][getCandidatesNumber(gruppo_o_partito)];
							candidati_categorico[i][getCandidatesNumber(gruppo_o_partito)] = null;
							return;
						}
					}
					return;
				}
			}
		}
	}

	//@ ensures n_gruppi_partiti == (old)n_gruppi_partiti + 1 <==> (\forall int i; i < n_gruppi_partiti; candidatiOrdinale[i] != elemento);
	public boolean setVotingOrdinale(String elemento) { 		//array per gruppi / partiti / candidati, restituisce se elemento inserito è già presente oppure lo crea
		for(int i = 0; i<n_gruppi_partiti; i++)
			if(candidati_ordinale[i].compareToIgnoreCase(elemento)==0)
				return false;
		candidati_ordinale[n_gruppi_partiti] = elemento;
		n_gruppi_partiti ++;
		return true;
	}

	//@ ensures n_gruppi_partiti == (old)n_gruppi_partiti - 1;
	public void deletePartyCandidateOrdinale(String elemento) {		//rimozione gruppo / partito o candidato
		for(int i = 0; i<n_gruppi_partiti; i++) {
			if(candidati_ordinale[i].compareToIgnoreCase(elemento)==0){
				if(i != n_gruppi_partiti-1)
					candidati_ordinale[i] = candidati_ordinale[n_gruppi_partiti-1];
				n_gruppi_partiti--;
				return;
			}
		}
	}

	public void setEsito(String esit) {
		esito = esit;
	}
	
	public String getEsito() {
		return esito;
	}

	public void setElettori(int n_elettori) {
		this.n_elettori = n_elettori;
	}
	
	public int getElettori() {
		return n_elettori;
	}

	//@ ensures voti.size() == (old)voti.size() + 1;
	public void addVotoControllo(Voto votoControllo) {	//aggiunge un voto di controllo nel caso di chiusura forzata del sistema
		voti.add(votoControllo);
	}
	
	//@ ensures voti.size() == (old)voti.size();
	public void addVoto(Voto voto) {		//rimuove il voto di controllo e aggiunge il voto effettivo
		for(int i = 0; i<voti.size();i++) {
			if((quesito.equals(""))&&(voti.get(i).getGruppoPartito()!= null)&&(voti.get(i).getGruppoPartito().equals("astenuto"))){
				voti.remove(i);
				break;
			}
			if((!(quesito.equals("")))&&(voti.get(i).getReferendum().equals("astenuto"))){
				voti.remove(i);
				break;
			}
		}
		voti.add(voto);		
	}
	
	public int getNumeroVoti() {
		return voti.size();
	}
	
	public String getSceltaReferendum(int numeroScheda){
		return voti.get(numeroScheda).getReferendum();
	}

	public void setReferendum(String scelta) {
		if(scelta.equals("favorevole"))
			favorevoli ++;
		else
			if(scelta.equals("contrario"))
				contrari++;
	}

	public int getFavorevoli() {
		return favorevoli;
	}

	public int getContrari() {
		return contrari;
	}

	public String getGruppoPartito(int numeroScheda) {
		return voti.get(numeroScheda).getGruppoPartito();
	}

	public String[] getCandidati(int numeroScheda) {
		return voti.get(numeroScheda).getCandidati();
	}
	
	public void setPartitoCandidato(String[] candidati, String gruppo_o_partito) {			//inserisce nella matrice risultatiSchedeCategorico il voto passato per argomento
		if(risultatiSchedeCategorico == null) {
			risultatiSchedeCategorico = new int[n_gruppi_partiti][];
			for(int i=0; i< n_gruppi_partiti; i++)
				risultatiSchedeCategorico[i] = new int[getCandidatesNumber(candidati_categorico[i][0].getNome())+1];
		}
		for(int i = 0; i< n_gruppi_partiti; i++) {
			if(candidati_categorico[i][0].getNome().compareToIgnoreCase(gruppo_o_partito)==0) {
				risultatiSchedeCategorico[i][0]++;
				if(candidati != null) {
					int length = candidati.length;					
					while(length>0){
						for(int j = 1; j <= getCandidatesNumber(gruppo_o_partito); j++) {
							if((candidati_categorico[i][j].getNome().compareToIgnoreCase(candidati[length-1]))==0) {
								risultatiSchedeCategorico[i][j]++;
								break;
							}
						}
						length--;
					}
				}
				break;
			}
		}
	}
	
	public void setOrdinale(String candidato){		//incrementa in risultatiSchedeOrdinale i voti assegnati al candidato passato per argomento 
		if(risultatiSchedeOrdinale==null)
			risultatiSchedeOrdinale = new int[n_gruppi_partiti];
		for(int i=0;i<n_gruppi_partiti; i++)
			if(candidati_ordinale[i].compareToIgnoreCase(candidato)==0)
				risultatiSchedeOrdinale[i]++;
	}

	public int[][] getVotesCategoricoList() {
		return risultatiSchedeCategorico;
	}
	
	public int[] getVotesOrdinaleList() {
		return risultatiSchedeOrdinale;
	}

	public String[] getEsitoCandidati() {
		return esitoCandidati;
	}
	
	public void setDimensioneEsito(int n_vincitori) {
		if(n_vincitori == 0)
			esitoCandidati=null;
		else
			esitoCandidati = new String[n_vincitori];
	}

	public void aggiungiVincitore(String vincitore) {
		for(int i=0;i<esitoCandidati.length;i++){
			if(esitoCandidati[i]==null) {
				esitoCandidati[i] = vincitore;
				return;
			}
		}
	}

	public Elettore getElector(String user, int population) {
		for(int i=0;i<elettori.size(); i++)
			if(elettori.get(i).getUser().equals(user))
				return elettori.get(i);
		Elettore e = new Elettore(user, population);
		elettori.add(e);
		return e;
	}
}
