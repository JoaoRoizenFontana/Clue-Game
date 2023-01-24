package model;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import controller.*;

class Jogo{

	Random random = new Random();

	protected int numJogadores;
	protected Tabuleiro tabuleiro;
	protected int vez;
	protected ArrayList<Cartas> cartas;
	protected ArrayList<Cartas> baralho;
	protected ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	protected ArrayList<Integer> envelope  = new ArrayList<Integer>();
	
	
	protected Jogo(ArrayList<String> temp) {
		iniciaJogadores(temp); 
		iniciaCartas();
		montaEnvelope(); 
		embaralhaCartas();
		tabuleiro = new Tabuleiro();
		distribuiCartas();
		
			
	}
	
	protected Jogo(String path) {
		iniciaCartas();
		tabuleiro = new Tabuleiro();
		try {
			BufferedReader temp = new BufferedReader(new FileReader(path));
			
			vez = Integer.parseInt(temp.readLine());
			
			numJogadores =  Integer.parseInt(temp.readLine());
			
			String envelopeStr[] = temp.readLine().split(", ");
			for(String s:envelopeStr) {
				envelope.add(Integer.parseInt(s));
			}
			
			ArrayList<String> nomeJoga = new ArrayList<String>();
			for(int i = 0;i<numJogadores;i++) {
				jogadores.add(new Jogador(temp.readLine()));
				
				String posStr[] = temp.readLine().split(", ");
				Celula pos = tabuleiro.getCelula(Integer.parseInt(posStr[0]), Integer.parseInt(posStr[1]));
				if(pos.getId()=='R') {
					pos.getComodo().setNumPlayers(1);
				}

				jogadores.get(i).setPosicao(pos);
				
				String cartasStr[] = temp.readLine().split(", ");
				for(String s:cartasStr) {
					jogadores.get(i).atribuirCarta(Integer.parseInt(s));
				}
				
				String bdnStr[] = temp.readLine().split(", ");
				for(String s:bdnStr) {
					jogadores.get(i).anotaBlocoDeNotas(Integer.parseInt(s));
				}
				
				String ativo = temp.readLine();
				if(ativo=="F") {
					jogadores.get(i).setInativo();
				}
			}
		}
		catch(IOException e) {
			
		}
	}
	

	
	
	private void iniciaCartas(){
		cartas = new ArrayList<Cartas>();
		TipoCarta tipo;
		String nome;
		BufferedReader temp;
	    
	    try{
	      temp = new BufferedReader(new FileReader("src/model/cartas.txt"));
	      tipo = TipoCarta.ARMA;
	      for(int i = 0;i < 6; i++){
	    	  nome =  temp.readLine();
	          cartas.add(new Cartas(tipo,nome,i));
	        
	      }  
	      tipo = TipoCarta.JOGADOR;
	      for(int i = 0;i < 6; i++){
	    	  nome =  temp.readLine();
	          
	          cartas.add(new Cartas(tipo,nome,i+6));
	        
	      }
	      tipo = TipoCarta.COMODO;
	      for(int i = 0;i < 9; i++){
	    	  nome =  temp.readLine();
	          
	          cartas.add(new Cartas(tipo,nome,i+12));
	        
	      }
	      
	    }
	    catch(IOException e){
	    	System.out.println("Erro");
	      return;
	    }
	    baralho = (ArrayList<Cartas>)cartas.clone();	
	}
	
	private void embaralhaCartas( ){
		ArrayList<Cartas> temp = (ArrayList<Cartas>)baralho.clone();
		ArrayList<Cartas> embaralhado = new ArrayList<Cartas>();
		int sorteio;
		for(int i = 0;i<18;i++){
			sorteio = random.nextInt(18-i);
			embaralhado.add(temp.get(sorteio));
			temp.remove(sorteio);
		}
		baralho = (ArrayList<Cartas>)embaralhado.clone();
		
	}
	
	private void iniciaJogadores(ArrayList<String> temp){
		int atual = 0;
		Iterator<String> aux = temp.iterator();
		numJogadores = temp.size();
		while(numJogadores > atual) {
			
			jogadores.add(new Jogador(temp.get(atual)));
			atual++;
		}
		
	}	
	
	private void montaEnvelope(){
		int sorteio;
		
		sorteio = random.nextInt(6);
		envelope.add(sorteio);
		baralho.remove(sorteio);
		
		sorteio = random.nextInt(6) + 6;
		envelope.add(sorteio);
		baralho.remove(sorteio-1);
		
		sorteio = random.nextInt(9) + 12;
		envelope.add(sorteio);
		baralho.remove(sorteio-2);
	}	
	
	private void distribuiCartas(){
		int atual = 0;
		int contador = 0; 
		int id;
		
		
		while(contador<18){
			if(!envelope.contains(baralho.get(contador))){
				id = baralho.get(contador).getId();
				jogadores.get(atual).atribuirCarta(id);
				jogadores.get(atual).anotaBlocoDeNotas(id);
				
				
				atual++;
				
				if(atual==numJogadores){
					atual = 0;	
				}	
			}
			contador++;
			
		}	
	}
}
			
	


