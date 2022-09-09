package commons;

import java.sql.*;

import interfaces.UtenteDao;

public class UtenteDaoImpl implements UtenteDao{
	
	private Abilitato abilitati[] = {new Abilitato("RSSMRA43B07L345T", "1234", 15000, "Elettore"), new Abilitato("RFBMRA55L09T634P", "1234", 25000, "Scrutatore"), new Abilitato("GLLPLA94A18D712S", "1234", 25000, "Elettore"), new Abilitato("VRDMNC83D42V283Q", "1234", 15000, "Scrutatore"), new Abilitato("VRDMNC83D42V283Q", "1234", 15000, "Elettore"), new Abilitato("BNCLGU62E25K486R", "1234", 25000, "Elettore")};
	
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
	
	/*			questo codice commentato viene spiegato nel paragrafo Gestione dei dati persistenti a pag.29 della documentazione relativa al progetto
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
