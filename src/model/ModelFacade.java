package model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class ModelFacade {
	Jogo jogo;
	
	public ModelFacade(ArrayList<String> temp){
		jogo = new Jogo(temp);
	}
	
	public ModelFacade(String temp){
		jogo = new Jogo(temp);
	}
	
	public Jogo getJogo() {
		return jogo;
	}
	
	public void chamouPalpite(int i) {
		String nomeJ = jogo.cartas.get(i).getNome();
		for(Jogador j:jogo.jogadores) {
			if(j.getNome().equals(nomeJ)) {
				if(j.getPosicao().getId()=='R')j.getPosicao().getComodo().setNumPlayers(-1);
				int pos[] = jogo.jogadores.get(jogo.vez).getPosicao().getComodo().givePosicao();
				j.setPosicao(jogo.tabuleiro.getCelula(pos[0], pos[1]));
				jogo.jogadores.get(jogo.vez).getPosicao().getComodo().setNumPlayers(1);
				break;
			}
		}
	}
	
	public void salvaJogo(String s) {
		BufferedWriter out = null;
		FileWriter fstream;
		try {
		    fstream = new FileWriter(s+".txt",false);
		    
		    fstream.write(Integer.toString(jogo.vez)+"\n");
		    fstream.write(Integer.toString(jogo.numJogadores)+"\n");
		    
		    for(int i =0;i<2;i++){
		    	fstream.write(Integer.toString(jogo.envelope.get(i))+", ");
		    }
		    fstream.write(Integer.toString(jogo.envelope.get(2))+"\n");
		    
		    for(Jogador j:jogo.jogadores) {
		    	fstream.write(j.getNome()+"\n");
		    	fstream.write(Integer.toString(j.getPosicao().getLinha())+", ");
		    	fstream.write(Integer.toString(j.getPosicao().getColuna())+"\n");
		    	
		    	for(int i = 0;i<j.getCartas().size()-1;i++) {
		    		fstream.write(Integer.toString(j.getCartas().get(i))+", ");
		    	}
		    	fstream.write(Integer.toString(j.getCartas().get(j.getCartas().size()-1))+"\n");
		    	
		    	for(int i = 0;i<j.getBloco().size()-1;i++) {
		    		fstream.write(Integer.toString(j.getBloco().get(i))+", ");
		    	}
		    	fstream.write(Integer.toString(j.getBloco().get(j.getBloco().size()-1))+"\n");
		    	
		    	String ativo;
		    	if(j.getAtivo())ativo = "T";
		    	else ativo = "F";
		    	fstream.write(ativo+"\n");
		    	
		    }
		    
		    
		    fstream.close();
		    
		}
		catch (IOException e) {
		    System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	//Acessos a dados do jogo
	public ArrayList<Integer> getEnvelope() {
		return jogo.envelope; 
	}
	
	public int getVez() {
		return jogo.vez;
	}
	
	public ArrayList<Cartas> getCartas() {
		return jogo.cartas; 
	}
	
	public String getNomeCarta(int i) {
		return jogo.cartas.get(i).getNome();
	}
	
	public ArrayList<Cartas> getBaralho() {
		return jogo.baralho; 
	}
	
	public ArrayList<Jogador> getJogadores() {
		return jogo.jogadores; 
	}
	
	public int getNumJogadores() {
		return jogo.numJogadores; 
	}
	
	public void setVez(int i) {
		jogo.vez = i;
	}
	
	
	//Acessos ao tabuleiro
	public Tabuleiro getTabuleiro() {
		return jogo.tabuleiro; 
	}
	
	public Celula getCelula(int l, int c){
	      return jogo.tabuleiro.getCelula(l, c);
	}
	
	public int validaMovimento(int dado, int clique[], int aux) {
		Celula cel = jogo.tabuleiro.getCelula(clique[0], clique[1]);
		Jogador j = jogo.jogadores.get(aux);
		return jogo.tabuleiro.validaMovimento(dado, cel, j);
	}
	
	public int[] defineClique(int x, int y) {
		Celula cel = jogo.tabuleiro.defineClique(x, y);
		int result[] = new int[]{cel.getLinha(),cel.getColuna()};
		return result;
	}
	
	//Acessos aos Jogadores
	public ArrayList<Integer> getBloco(int i){
		return jogo.jogadores.get(i).getBloco(); 
	}
	
	public ArrayList<Integer> getCartas(int i){
		return jogo.jogadores.get(i).getCartas(); 
	}
	
	public void setNome(String s,int i) {
		jogo.jogadores.get(i).setNome(s);
	}
	
	public String getNome(int i) {
		return jogo.jogadores.get(i).getNome();
	}
	
	
	public void setInativo(int i){
		jogo.jogadores.get(i).setInativo();
		
	}
	
	public boolean getAtivo(int i) {
		return jogo.jogadores.get(i).getAtivo();
	}
	
	public void setCelula(Celula c, int i) {
		jogo.jogadores.get(i).setCelula(c);
	}
	
	public Celula getPosicao(int i) {
		return jogo.jogadores.get(i).getPosicao();
	}
	public int getColuna(int i) {
		return jogo.jogadores.get(i).getPosicao().getColuna();
	}
	public int getLinha(int i) {
		return jogo.jogadores.get(i).getPosicao().getLinha();
	}
	
	
	
	public void setPosicao(Celula c,int i) {
		jogo.jogadores.get(i).setPosicao(c);
		
	}
	
	public Color getColor(int i) {
		return jogo.jogadores.get(i).getColor();
	}
	
	public char getCelId(int i) {
		return jogo.jogadores.get(i).getPosicao().getId();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl, int i) {
	      jogo.jogadores.get(i).addPropertyChangeListener(pcl);
	   }
	//Acessos Comodo do Jogador
	public String getComodoName(int i) {
		return jogo.jogadores.get(i).getPosicao().getComodo().getNome();
	}
	
	public int getComodoId(int i) {
		return jogo.jogadores.get(i).getPosicao().getComodo().getId();
	}
	
	public void setComodoNumPlayers(int j, int i) {
		jogo.jogadores.get(j).getPosicao().getComodo().setNumPlayers(i);
	}
	
	public void setComodoNumPlayers(int l,int c, int i) {
		jogo.tabuleiro.getCelula(l,c).getComodo().setNumPlayers(i);
	}
	
	//Acessos a célula qualquer
	public char getCelId(int l,int c) {
		return jogo.tabuleiro.getCelula(l, c).getId();
	}
	
	public int[] ajeitaDestino(int clique[]) {
		return jogo.tabuleiro.getCelula(clique[0],clique[1]).getComodo().givePosicao();
		
	}
	
	
}
