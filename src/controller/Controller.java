package controller;

import java.beans.*;
import model.*;
import view.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

public class Controller {
	
	private ModelFacade jogo;
	private boolean jogoAtivo = true;
	private ViewFacade framejogo;
	private int vez;
	private int count;
	private int dados[] = new int[]{1,1};
	private int valorDados;
	private PropertyChangeSupport support;
	 
	private static Controller controllerSingleton;
	private Controller() {
		
	}
	//Acesso singleton
	public static Controller getController() {
		Controller c;
		if(controllerSingleton == null) {
			c = new Controller();
			return c;
		}
		else {
			return controllerSingleton;
		}
	}
	
	
	//Métodos do fluxo da partida
	public void montaJogadores(ArrayList<String> j){
		jogo = new ModelFacade(j); 
		count = 0;
		start();
	}
	
	public void restartSetup(String path) {
		jogo = new ModelFacade(path);
		count = jogo.getVez();
		start();
	}
	
	public void start() {
		support = new PropertyChangeSupport(this);
		framejogo = new ViewFacade(this);
		fluxoInicial();
	}
	
	public void fluxoInicial(){
		int aux = vez;
		do {
			if(count == jogo.getNumJogadores()) {
				count = 0;
			}
			vez = count;
			count++;
		}
		while(!jogo.getAtivo(vez));
		jogo.setVez(vez);
		
		framejogo.setJogadorVez(jogo.getNome(vez));
		support.firePropertyChange("vez",aux,this.vez);
		
		framejogo.setDado("Jogue os dados");
	}	
	
	public void definirDados() {
		Random random = new Random();
		int aux[] = new int[2];
		
		aux[0] = dados[0];
		dados[0] = random.nextInt(6)+1;
		aux[1] = dados[1];
		dados[1] =random.nextInt(6)+1;
		
		
		
		jogouDado(dados[0]+dados[1]);
		support.firePropertyChange("dados",aux,this.dados);
		
	}
		
	public void jogouDado(int n){
		valorDados = n; 
		framejogo.setDado(n);
		framejogo.enablePassagem(false);
		framejogo.setClicavel(true);
	}
		
	public void doProximo() {
		framejogo.doProximo();
	}	
	
	
	public void move(int clique[]){

		clique = jogo.defineClique(clique[0],clique[1]);
		if(jogo.getCelId(vez) == 'R') {
			
			jogo.setComodoNumPlayers(vez,-1);
		}
		
		int valida = jogo.validaMovimento(valorDados, clique, vez);
		if(valida == 0) {
			framejogo.setDado("Sucesso");
			
			
			
			if(jogo.getCelId(clique[0],clique[1]) == 'R') {
				int destRoom[] = jogo.ajeitaDestino(clique);
				clique[0] = destRoom[0];
				clique[1] = destRoom[1];
				jogo.setComodoNumPlayers(clique[0], clique[1], 1);
			}

			jogo.setPosicao(jogo.getCelula(clique[0],clique[1]),vez);
			
			possivelPalpite();
		}
		else if(valida == -1){
			framejogo.setDado("Não foi possível mover");
			return;
		}
		else {
			framejogo.setDado("Aguarde a próxima rodada para mover");
			jogo.setComodoNumPlayers(vez,1);
		}
		framejogo.setClicavel(false);
		framejogo.enableProximo(true);
	}
	
	
	
	public boolean passagemSecreta(String nome){
		if(nome == "Sala de estar") {
			jogo.setPosicao(jogo.getCelula(2, 22),vez);
		}
		else if(nome == "Jardim de inverno") {
			jogo.setPosicao(jogo.getCelula(22, 2),vez);
		}
		else if(nome == "Cozinha") {
			jogo.setPosicao(jogo.getCelula(2, 2),vez);
		}
		else if(nome == "Escritorio") {
			jogo.setPosicao(jogo.getCelula(22, 22),vez);
		}
		else {
			return false;
		}
		return true;
	}
	
	public void possivelPalpite() {
		if(jogo.getCelId(vez)=='R') {
			framejogo.enablePalpite(true);
		}
		else {
			framejogo.enablePalpite(false);
		}
	}
	
	public ArrayList<String> analisaPalpite(ArrayList<Integer> vetor){
		ArrayList<String> respostas = new ArrayList<String>();
		jogo.chamouPalpite(vetor.get(2));
		for(int i = 0;i<jogo.getNumJogadores();i++) {
			if(i != vez) {
			for(int carta: jogo.getCartas(i)) {
				if(vetor.contains(carta)) {
					
					String resposta = jogo.getNome(i) + " disse que tem a carta " + jogo.getNomeCarta(carta);
					respostas.add(resposta);
					break;
				}
			}
			}
		}
		
		return respostas;
	}
	
	public int analisaAcusacao(ArrayList<Integer> vetor) {
		if(vetor.equals(jogo.getEnvelope())) {
			
			return -1;
		}
		else {
			int contador = 0;
			int index = 0;
			jogo.setInativo(vez);
			for(int i = 0;i<jogo.getNumJogadores();i++) {
				if(jogo.getAtivo(i)) {
					contador++;
					if(contador==2) {
						break;
					}
				}
				else if(contador == 0) {
					index++;
				}
			}
			if(contador==1) {
				return index;
			}
			else {
				return -2;
			}
		}
	}
	
	public void salvaJogo(String s) {
		jogo.salvaJogo(s);
	}
	
	public void comecaJogo() {
		ViewFacade.comecaView(this);
	}
	
	public void acabaJogo() {
		framejogo.acabaView();
		
	}
	
	//Acesso a propriedades do Controller
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
	      support.addPropertyChangeListener(pcl);
	}
	
	public int getVez() {
		return vez; 
	}
	public void setVez(int i) {
		vez = i; 
	}
	
	public int getDado(int i) {
		return dados[i];
	}
	
	//Acesso a propriedades do model
	public int getNumJogadores() {
		return jogo.getNumJogadores();
	}
	
	public ArrayList<Integer> getCartas(){
		return jogo.getCartas(vez);
	}
	
	
	//Acessos ao jogador da vez
	public String getNome() {
		return jogo.getNome(vez);
	}
	
	public String getNome(int i) {
		return jogo.getNome(i);
	}
	
	public ArrayList<Integer> getBloco(){
		return jogo.getBloco(vez);
	}
	
	public char getId() {
		return jogo.getCelId(vez);
	}
	
	public String getComodoName() {
		return jogo.getComodoName(vez);
	}
	
	public void addPCLJogador(PropertyChangeListener pcl,int i) {
		jogo.addPropertyChangeListener(pcl, i);
	}
	
	public int getComodoId() {
		return jogo.getComodoId(vez);
	}
	
	//Acesso para criar círculos
	public int[] getXY(int i) {
		int pos[] = new int[2];
		pos[1] = jogo.getColuna(i);
		pos[0] = jogo.getLinha(i);
		return pos;
	}
	public Color getColor(int i) {
		return jogo.getColor(i);
	}

}
