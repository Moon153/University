package users;

import commons.Session;
import interfaces.Utente;

public class Scrutatore implements Utente{
	
	public boolean activeSession() {
		return ! Session.getInstance().phaseInfo().equals("closed");
	}	
	
	public Session getSession() {
		return Session.getInstance();
	}

}
