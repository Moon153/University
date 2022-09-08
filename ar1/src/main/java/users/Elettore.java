package users;

import interfaces.Utente;

public class Elettore implements Utente{
	
	private String user;
	private int population;
	private boolean votoEseguito;
	
	public Elettore(String user, int population) {
		this.user = user;
		this.population = population;
	}
	
	public String getUser() {
		return user;
	}

	public int getPopulation() {
		return population;
	}
	
	public void vote() {
		votoEseguito=true;
	}
	
	public boolean votingAllowed() {
		return !votoEseguito;
	}
}
