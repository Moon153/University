package ar1;

import java.sql.*;
import Interfaces.UtenteDao;

public class UtenteDaoImpl implements UtenteDao{
	
	private Abilitato abilitati[] = {new Abilitato("RSSMRA43B07L345T", "1234", 15000, "Elettore"), new Abilitato("mario", "1234", 25000, "Scrutatore"), new Abilitato("paolo", "1234", 25000, "Elettore"), new Abilitato("paolo", "1234", 15000, "Scrutatore"), new Abilitato("gigi", "1234", 15000, "Elettore")};
	
	public boolean verificaUtente(String role, String user, String pass){
		for(int i=0; i<abilitati.length;i++) {
			if((user.equals(abilitati[i].user()))&&(role.equals(abilitati[i].role()))&&(pass.equals(abilitati[i].pass())))
				return true;
		}
		return false;
	}

	@Override
	public int getNumeroElettori() {
		int e = 0;
		for(int i=0; i<abilitati.length;i++) {
			if(abilitati[i].role().equals("Elettore"))
				e++;
		}
		return e;
	}

	@Override
	public int getPopulation(String user) {
		for(int i=0; i<abilitati.length;i++) {
			if(user.equals(abilitati[i].user()))
				return abilitati[i].population(); 
		}
		return 0;
	}
	
	/*
	public Connection getCon() throws SQLException, ClassNotFoundException{
	    Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jbdc:mysql://localhost/progettodb?user=root&password=progetto");
		return conn;
	}
	
	@Override
	public Utente verificaUtente(String role, String user, String pass){
		try {
			System.out.println("Recupero dati dal db");
			String query = "SELECT * FROM login";// WHERE username = user AND password = pass AND ruolo = role";
			Connection con = getCon();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet ris = ps.executeQuery();
			System.out.println(ris.getString("username"));
			if(ris.next()) {
				return true;
			}
			ris.close();
			con.close();
			return false;
		}
		catch (SQLException ex){
			System.out.println("SQL Exception: " + ex.getMessage());
			System.out.println("SQL State: " + ex.getSQLState());			
			System.out.println("Connection Error: " + ex.getErrorCode());
		return false;
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	*/
}
