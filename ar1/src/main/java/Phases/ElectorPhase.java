package Phases;

import java.util.ArrayList;

import Interfaces.Observer;
import Users.Elettore;
import ar1.Session;
import ar1.SessionPhase;
import ar1.Voto;

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