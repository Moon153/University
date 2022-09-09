package phases;

import commons.SessionPhase;

public class BallotPhase extends SessionPhase{
	
	private int inizializzato = 0;
	private boolean assoluta;
	private int ordinale;
		
	public void setEsit(String mode) {
		if (!(getEsitMode() == null || getEsitMode().equals(mode))) {
			data.setEsito(null);
		}
		setEsitMode(mode);
	}

	public void performBallot(String votingMode) {
		switch(getEsitMode()){
			case "quorum":
				calcolaQuorum();
				break;
			case "noQuorum":
				calcolaEsitoReferendum();
				break;
			case "assoluta":
				assoluta = true;
				if(!votingMode.equals("voto ordinale"))
					calcolaEsitoCategorico();
				else
					calcolaEsitoOrdinale();
				break;
			case "maggioranza":
				assoluta=false;
				if(!votingMode.equals("voto ordinale"))
					calcolaEsitoCategorico();
				else
					calcolaEsitoOrdinale();
				break;
		}
	}

	private void calcolaEsitoCategorico(){			
		int n_vincitori = 0;
		int voti_vincitore = 0;
		int[][]listaVoti;
		if(inizializzato==0) {
			for(int i=0; i<getNumeroVoti();i++) {
				if(!getGruppoPartito(i).equals("astenuto"))
					data.setPartitoCandidato(getCandidati(i), getGruppoPartito(i));
			}
			inizializzato=1;
		}
		listaVoti = getListaVotiCategorico();
		if(listaVoti == null) {
			data.setEsito("Non è stata raggiunta una maggioranza.");
			return;
		}
		for(int i = 0; i< listaVoti.length; i++) {
			if(listaVoti[i][0]> voti_vincitore) {
				voti_vincitore = listaVoti[i][0];
				n_vincitori = 1;
			}
			else
				if(listaVoti[i][0] == voti_vincitore)
					n_vincitori++;
		}
		if(n_vincitori> 1) {
			data.setDimensioneEsito(n_vincitori);
			for(int i = 0; i< listaVoti.length; i++)
				if(listaVoti[i][0] == voti_vincitore)
					data.aggiungiVincitore(data.getPartiesList()[i]);
			data.setEsito("Non è stata raggiunta una maggioranza.\nI gruppi / partiti che hanno raggiunto il\nmaggior numero di voti sono:");
			return;
		}
		else{
			for(int i = 0; i< listaVoti.length; i++) {
				if(listaVoti[i][0] == voti_vincitore){
					if(assoluta) {
						if(!(voti_vincitore >= data.getNumeroVoti()/2+1)) {
							data.setEsito("Il partito / gruppo che ha ottenuto il maggior numero\ndi voti è: " + data.getPartiesList()[i] + "\nma non è stata raggiunta una maggioranza assoluta");
							return;
						}
						data.setDimensioneEsito(0);
					}
					voti_vincitore = 0;
					n_vincitori = 0;
					for(int j=1; j<listaVoti[i].length;j++){
						if(listaVoti[i][j]> voti_vincitore) {
							voti_vincitore = listaVoti[i][j];
							n_vincitori = 1;						
						}
						else
							if(listaVoti[i][j] == voti_vincitore)
								n_vincitori++;
					}
					if(n_vincitori> 1) {
						data.setDimensioneEsito(n_vincitori);
						for(int j = 1; j< listaVoti[i].length; j++) {
							if(listaVoti[i][j] == voti_vincitore) {
								data.aggiungiVincitore(data.getCandidatesList(data.getPartiesList()[i])[j-1]);
							}
						}
						data.setEsito("Il partito / gruppo vincitore è : " + data.getPartiesList()[i] + ".\nNon è stata raggiunta una maggioranza tra i\ncandidati; coloro che hanno ricevuto il maggior\nnumero di voti sono:");
						return;
					}
					else {
						for(int j = 1; j<listaVoti[i].length; j++) {
							if(listaVoti[i][j] == voti_vincitore) {
								if(assoluta) {
									if(voti_vincitore >= data.getNumeroVoti()/2+1) {
										data.setEsito("La maggioranza degli elettori ha votato\nil candidato: " + data.getCandidatesList(data.getPartiesList()[i])[j-1] + "\ndel partito / gruppo: " + data.getPartiesList()[i] + "\npermettendo di raggiungere la maggioranza assoluta");
										return;
									}
									else {
											data.setEsito("La maggioranza degli elettori ha votato\nil candidato: " + data.getCandidatesList(data.getPartiesList()[i])[j-1] + "\ndel partito / gruppo: " + data.getPartiesList()[i] + ",\nma non ha raggiunto la maggioranza assoluta dei voti");
										return;
									}
								}
								else {
										data.setEsito("La maggioranza degli elettori ha votato\nil candidato: " + data.getCandidatesList(data.getPartiesList()[i])[j-1] + "\ndel partito / gruppo: " + data.getPartiesList()[i]);
								}
								return;
							}
						}
					}
				}
			}
		}
	}

	private int[][] getListaVotiCategorico() {
		return data.getVotesCategoricoList();
	}
	
	private int[] getListaVotiOrdinale() {
		return data.getVotesOrdinaleList();
	}

	public String getGruppoPartito(int numeroScheda) {
		return data.getGruppoPartito(numeroScheda);
	}

	public String[] getCandidati(int numeroScheda) {
		return data.getCandidati(numeroScheda);
	}

	private void calcolaEsitoOrdinale() {
		int n_vincitori = 0;
		int voti_vincitore = 0;
		int []listaVoti;
		while(inizializzato<data.getPartiesNumber()) {
			setOrdinale();
			listaVoti = getListaVotiOrdinale();
			for(int i=0;i<listaVoti.length;i++) {
				if(listaVoti[i]>voti_vincitore) {
					voti_vincitore = listaVoti[i];
					n_vincitori = 1;
				}
				else
					if(listaVoti[i] == voti_vincitore)
						n_vincitori++;
			}
			if(n_vincitori>1) {
				ordinale++;
			}
			else {
				for(int i = 0; i<listaVoti.length; i++) {
					if(listaVoti[i] == voti_vincitore) {
						if(assoluta) {
							if(voti_vincitore >= data.getNumeroVoti()/2+1) {
								data.setEsito("La maggioranza degli elettori ha preferito\nil candidato/gruppo/partito: " + data.getPartiesList()[i] +"\npermettendo di raggiungere la maggioranza assoluta");
								return;
							}
							else {
								data.setEsito("La maggioranza degli elettori ha preferito\nil candidato/gruppo/partito: " + data.getPartiesList()[i] + ",\nma non ha raggiunto la maggioranza assoluta dei voti");
								return;
							}
						}
						else
							data.setEsito("La maggioranza degli elettori ha preferito\nil candidato/gruppo/partito: " + data.getPartiesList()[i]);
						return;
					}
				}
			}
		}
		data.setEsito("Nessun candidato/gruppo/partito ha raggiunto la\nmaggioranza dei voti");
	}

	private void setOrdinale() {		
		if(ordinale==inizializzato) {
			for(int i=0; i<getNumeroVoti();i++)
				if(getCandidati(i)!=null)
					data.setOrdinale(getCandidati(i)[ordinale]);
			inizializzato++;
		}
	}
	public void calcolaQuorum() {
		if(getNumeroVoti() < getElettori() / 2 + 1){
			data.setEsito("Quorum non raggiunto");
			return;
		}
		else
			calcolaEsitoReferendum();
	}

	private void calcolaEsitoReferendum() {
		if(inizializzato==0) {
			for(int i=0; i<getNumeroVoti();i++)
				if(!getSceltaReferendum(i).equals("astenuto"))
					data.setReferendum(getSceltaReferendum(i));
			inizializzato=1;
		}
		if(data.getFavorevoli() > data.getContrari())
			data.setEsito("La maggioranza degli elettori è favorevole\nal quesito:\n" + getReferendumData());
		else{
			if(data.getFavorevoli() < data.getContrari())
				data.setEsito("La maggioranza degli elettori è contraria\nal quesito:\n" + getReferendumData());
			else
				data.setEsito("Nessuna opinione ha raggiunto la\nmaggioranza per il quesito:\n" + getReferendumData());
		}
	}

	public String getSceltaReferendum(int numeroScheda) {
		return data.getSceltaReferendum(numeroScheda);
	}

	public int getNumeroVoti() {
		return data.getNumeroVoti();
	}

	public int getElettori() {
		return data.getElettori();
	}

}
