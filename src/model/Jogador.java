package model;
import java.util.ArrayList;
import java.awt.Color;
import java.util.*;
import java.beans.*;

class Jogador{
	private Color cor;
	private Celula posicao; 
	private int[] deslocIni = new int[2];
	private String nome;
	private ArrayList<Integer> cartasJ = new ArrayList<Integer>();
	private int nCartas = 0; 
	private ArrayList<Integer> blocoDeNotas = new ArrayList<Integer>();
	private boolean ativo = true;
	
	private PropertyChangeSupport support;
	
	public Jogador (String name) {
		support = new PropertyChangeSupport(this);
		this.nome = name;
		
		if(name.equals("Miss Scarlett")){
			posicao = new Celula('I',23,7);
			deslocIni[0] = 0;
			deslocIni[1] = -1;
			cor = Color.red;
		}
		else if(name.equals("Col. Mustard")) {
		    posicao = new Celula('I',16,0);
		    deslocIni[0] = 1;
			deslocIni[1] = 0;
		    cor = Color.yellow;
		}
		else if(name.equals("Mrs. White") ){
		    posicao = new Celula('I',-1,9);
		    deslocIni[0] = 0;
			deslocIni[1] = 1;
		    cor = Color.white;
		}
		else if(name.equals("Rev. Green") ){
		    posicao = new Celula('I',-1,14);
		    deslocIni[0] = 0;
			deslocIni[1] = 1;
		    cor = Color.green;
		}
		else if(name.equals("Mrs. Peacock")) {
		    posicao = new Celula('I',5,23);
		    deslocIni[0] = -1;
			deslocIni[1] = 0;
		    cor = Color.blue;
		    
		}
		else if(name.equals("Prof. Plum")){
		    posicao = new Celula('I',18,23);
		    deslocIni[0] = -1;
			deslocIni[1] = 0;
		    cor = new Color(221,160,221);
		}
	}

    public Jogador(String name, char id, int c, int l){
    	this.nome = name;
    	posicao = new Celula(id,c,l);
    }
  
   protected void addPropertyChangeListener(PropertyChangeListener pcl) {
      support.addPropertyChangeListener(pcl);
   }
	
	protected void atribuirCarta(int c) {
		this.cartasJ.add(c); 
		nCartas++; 
	}
	
	protected void anotaBlocoDeNotas(int c){
		blocoDeNotas.add(c);	
	}	
	
	protected ArrayList<Integer> getBloco(){
		return blocoDeNotas; 
	}
	
	protected ArrayList<Integer> getCartas(){
		return cartasJ; 
	}
	
	protected void setNome(String s) {
		this.nome = s;
	}
	
	protected String getNome() {
		return this.nome;
	}
	
	
	protected void setInativo(){
		ativo = false;
		setPosicao(new Celula('S',-2,-2));
		
	}
	
	protected boolean getAtivo() {
		return ativo;
	}
	
	protected void setCelula(Celula c) {
		this.posicao = c;
	}
	protected Celula getPosicao() {
		return posicao;
	}
	protected int[] getDeslocIni() {
		return deslocIni;
	}
	protected void setPosicao(Celula c) {
		posicao.settemJogador(false);
		int ant[] = new int[2];
		int dps[] = new int[2];
		ant[0] = posicao.getLinha();
		ant[1] = posicao.getColuna();
		dps[0] = c.getLinha();
		dps[1] = c.getColuna();
		
		support.firePropertyChange("posicao",ant,dps);
		posicao = c;
		posicao.settemJogador(true);
		
	}
	

	
	protected Color getColor() {
		return cor;
	}
}
