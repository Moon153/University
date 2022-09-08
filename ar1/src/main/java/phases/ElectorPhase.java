package phases;

import java.util.ArrayList;

import commons.Session;
import commons.SessionPhase;
import commons.Voto;
import interfaces.Observer;
import users.Elettore;

public class ElectorPhase extends SessionPhase{
	
	private ArrayList<Observer> elettoriDaNotificare = new ArrayList<>();
	
	public Elettore getElector(String user, int population) {
		return data.getElector(user, population);
	}
	
	public void addVotoControllo(Voto votoControllo) {
		data.addVotoControllo(votoControllo);
	}
	
	public void addVoto(Voto voto) {
		data.addVoto(voto);
	}
	
	public void addElectorObserver(Observer electorObserver){
		elettoriDaNotificare.add(electorObserver);
	}
	
	public boolean hasChanged(){
		return (Session.getInstance().getEsit()!=null && Session.getInstance().getEsit().getState().equals("open"));
		
	}
	
	public void notifyObservers(){
		for(int i=0; i<elettoriDaNotificare.size();i++)
			elettoriDaNotificare.get(i).update();
	}

}