package commons;

import java.util.ArrayList;

public class Voto {

	private boolean astenuto;
	private String referendum;
	private String gruppo_partito;
	private String[] candidati;

	public void astenuto() {
		astenuto = true;
	}

	public void setReferendum(String scelta) {
		referendum = scelta;
	}

	public void setCandidati(ArrayList<String> candidati_scelti, String party) {
		gruppo_partito = party;
		candidati = candidati_scelti.toArray(new String[candidati_scelti.size()]);
	}
	
	public String getReferendum() {
		if(astenuto ==false)
			return referendum;
		return "astenuto";
	}

	public String getGruppoPartito() {
		if(astenuto == false)
			return gruppo_partito;
		return "astenuto";	
	}

	public String[] getCandidati() {
		return candidati;
	}

}
