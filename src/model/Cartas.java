package model;

class Cartas {
	
	private int id;
	private TipoCarta tipo; 
	private String nome;
	
	protected Cartas(TipoCarta tipo, String nome, int id) {
		super();
		this.tipo = tipo;
		this.nome  = nome;
		this.id = id;
	}
	
	public void setTipo(TipoCarta t) {
		this.tipo = t;
	}
	
	public void setNome(String s) {
		this.nome = s;
	}
	
	public TipoCarta getTipo() {
		return this.tipo;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cartas other = (Cartas) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo != other.tipo)
			return false;
		
		return true;
	}
	
	public String toString() {
		return this.nome;
	}
}

 enum TipoCarta{
		JOGADOR,ARMA,COMODO
	};
