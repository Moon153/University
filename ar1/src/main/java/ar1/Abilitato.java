package ar1;

public class Abilitato {
	
	private String user;
	private String pass;
	private int population;
	private String role;
	
	public Abilitato(String user, String pass, int population, String role) {
		this.user = user;
		this.pass = pass;
		this.population = population;
		this.role = role;
	}
	
	public String user(){
		return user;
	}
	
	public String pass(){
		return pass;
	}
	
	public String role(){
		return role;
	}
	
	public int population(){
		return population;
	}
}
