package commons;

import java.util.ArrayList;

public class Voto {

	private boolean astenuto;
	// @ invariant gruppo_partito != null ==>  referendum == null;
	// @ invariant candidati != null ==>  referendum == null;
	private String referendum;
	// @ invariant referendum != null ==> gruppo_partito == null;
	// @ invariant candidati != null ==> gruppo_partito != null;
	private String gruppo_partito;
	// @ invariant referendum != null ==> candidati == null;
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
	
	//@ ensures \result == "astenuto" <==> astenuto == true ;
	public String getReferendum() {
		if(astenuto ==false)
			return referendum;
		return "astenuto";
	}

	//@ ensures \result == "astenuto" <==> astenuto == true ;
	public String getGruppoPartito() {
		if(astenuto == false)
			return gruppo_partito;
		return "astenuto";	
	}

	public String[] getCandidati() {
		return candidati;
	}

}
