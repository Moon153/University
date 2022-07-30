package Users;

import Interfaces.Utente;
import ar1.Session;

public class Scrutatore implements Utente{
	
	public boolean activeSession() {
		return ! Session.getInstance().phaseInfo().equals("closed");
	}	
	
	public Session getSession() {
		return Session.getInstance();
	}

}
