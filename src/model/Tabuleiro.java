package model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math.*;

class Tabuleiro {
  private ArrayList<ArrayList<Celula>> celulas = new ArrayList<ArrayList<Celula>>();
  private ArrayList<Celula> been;
  
  protected Tabuleiro(){
    loadCelulas();
    
    
  }  
  
  private void loadCelulas(){
    BufferedReader temp;
    char value = ' ';
    try{
      temp = new BufferedReader(new FileReader("src/model/celulastabuleiro.txt"));
      for(int i = 0;i < 24; i++){
    	  
    	celulas.add(new ArrayList<Celula>());
        for(int k = 0; k < 24; k++){
          do {
        	  value = (char) temp.read();
          }
          while(value != 'N' && value!='Z' && value!='R' && value!='B' && value!='C' && value!='E' && value!='D');
          
          
          
          celulas.get(i).add(new Celula(value,i,k));
        }
      }  
    }
    catch(IOException e){
    	System.out.println("Erro");
      return;
    }
    
  }  
  
  //define qual célula foi clicada
  protected Celula defineClique(int x, int y){
	x = (x-50)/25;
	y = (y-75)/25;
	
    return getCelula(y,x);
  }  
  
  //valida se é possível ir até a célula em questão com o número de movimentos
  protected int validaMovimento(int dado, Celula cel, Jogador j){
    char id = cel.getId();
    Celula ini = j.getPosicao();
    int passosX;
    int passosY;
    
    
    if(ini.getId() == 'R') {
    	int[] porta = getPortaProxima(ini.getComodo(),cel);
    	
    	if(porta[0] == -1 && porta[1] == -1) {
    		return -2;
    	}
    	if(ini.getComodo().equals(cel.getComodo())) {

    		return -1;
    	}
    	
    	ini = getCelula(porta[0],porta[1]);
    	dado--;
    }
    else if(ini.getId() == 'I') {
    	int desloc[] = j.getDeslocIni();
    	ini = this.getCelula(ini.getLinha()+desloc[1], ini.getColuna()+desloc[0]);
    	dado--;
    }
    
    
    if((cel.gettemJogador() == true && cel.getId() != 'R') || id == 'Z'){
    	
      return -1;
    }
    if(id != 'R'){
      passosX = Math.abs(ini.getColuna()  - cel.getColuna());
      passosY =  Math.abs(ini.getLinha()  - cel.getLinha());
      
      if(passosX + passosY > dado){
        return -1;  
      }  
    }
    else{
      dado--;
      Comodo comodo = cel.getComodo();
      ini.settemJogador(false);
      int[] porta = getPortaProxima(comodo,j.getPosicao());
      
      if(porta[0] == -1 && porta[1] == -1) {
    	
  		return -1;
  	  }
      cel = this.getCelula(porta[0],porta[1]);

      
      passosX =  Math.abs(porta[1] - ini.getColuna());
      passosY =  Math.abs(porta[0] - ini.getLinha());
      
      if(passosX+passosY > dado || cel.gettemJogador() == true){
        return -1;  
      } 
      ini.settemJogador(true);
    }
    been = new ArrayList<Celula>();
    ini.settemJogador(false);
    if(validaCaminho(ini,cel,dado))return 0;
    else {
    	
    	if(j.getPosicao().getId() != 'R')ini.settemJogador(true);
    	return -1;
    }
  }

  private int[] getPortaProxima(Comodo destino, Celula inicio){
    int distancia;
    int menor = 1000;
    int index = -1;
    
    for(int i = 0; i < destino.getNumPortas();i++){
      distancia =  Math.abs(destino.getPortas()[i][0] - inicio.getLinha());
      distancia +=  Math.abs(destino.getPortas()[i][1] - inicio.getColuna());
      if (distancia<menor && !getCelula(destino.getPortas()[i][0],destino.getPortas()[i][1]).gettemJogador()){
        index = i;  
        menor = distancia;
      }  
    }
    if(index==-1) {
    	return new int[] {-1,-1};
    }
    
    return destino.getPortas()[index];  
  }
  
  protected Celula getCelula(int l, int c){
      return celulas.get(l).get(c);
  }  
  
  private boolean validaCaminho(Celula inicio, Celula fim, int dados) {
	  int fL = fim.getLinha();
	  int fC = fim.getColuna();
	  int iL = inicio.getLinha();
	  int iC = inicio.getColuna();
	  if(been.contains(getCelula(iL,iC)))return false;
	  been.add(this.getCelula(iL, iC));
	  if( dados == -1 || inicio.gettemJogador() || inicio.getId()=='R' ||inicio.getId()=='Z' ) {
		  
		  
		  return false;
	  }	   
	  
	  
	  if(Math.abs(fL-iL) + Math.abs(fC-iC) > dados)return false;
	  
	  if(fL == iL && fC == iC)return true;
	  
	  //esquerda
	  if(iC>0)if(validaCaminho(this.getCelula(iL,iC-1),fim,dados-1))return true;
	  //cima
	  if(iL> 0) if(validaCaminho(this.getCelula(iL-1,iC),fim,dados-1))return true;
	  
	  //direita
	  if(iC<24)if(validaCaminho(this.getCelula(iL,iC+1),fim,dados-1))return true;
	  //baixo
	  if(iL < 24)if(validaCaminho(this.getCelula(iL+1,iC),fim,dados-1))return true;
	  
	  return false;
  }
  
  
}
