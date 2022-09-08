package commons;

public class Candidato {
	private String nome;
	private char genere;
	
	public Candidato(String nome, char genere){
		this.nome = nome;
		this.genere = genere;		
	}
	
	public String getNome(){
		return nome;
	}
	
	protected static char getGenereInversoCandidato(Candidato[] gruppo_o_partito, String candidato) {
		for(int i=1;i<gruppo_o_partito.length;i++) {
			if(gruppo_o_partito[i].getNome()==candidato)
				if (gruppo_o_partito[i].getGenere()=='m')
					return 'w';
				else
					return 'm';
		}
		return 0;
	}

	protected char getGenere() {
		return genere;
	}
}