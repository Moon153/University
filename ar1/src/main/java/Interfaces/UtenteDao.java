package Interfaces;

public interface UtenteDao {
	public boolean verificaUtente(String role, String user, String pass);

	public int getNumeroElettori();

	public int getPopulation(String user);
}