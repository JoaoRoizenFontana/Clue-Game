package model;
import java.io.*;
import java.lang.Math;
import java.util.*;

class Comodo{ 
  private String nome;
  private int id;
  private int[][] portas;
  private int numPortas;
  private int numPlayers = 0;
  private static ArrayList<Comodo> instances = new ArrayList<Comodo>();
  
  //Singleton
  private Comodo(String s, int[][] p, int np, int id){
    nome = s;
    portas = (int[][]) p.clone();
    numPortas = np;
    this.id = id;
  } 
  
  protected static synchronized Comodo getComodoSingleton(String s, int[][] p, int np,int id){
	Comodo novo = new Comodo(s,p,np,id);
	if(instances.contains(novo)) {
		return instances.get(instances.indexOf(novo));		
	}
	else {
		instances.add(novo);
		return novo;
	}
  }
  
  protected int[] givePosicao() {
	  int l = -1;
	  int c = -1;
	  if(this.nome == "Cozinha") {
		  l = 2;
		  c = 0;
	  }
	  else if(this.nome == "Sala de musica") {
		  l = 5;
		  c = 9;
	  }
	  else if(this.nome == "Jardim de inverno") {
		  l = 3;
		  c = 18;
	  }
	  else if(this.nome == "Sala de jantar") {
		  l = 12;
		  c = 0;
	  }
	  else if(this.nome == "Salão de jogos") {
		  l = 10;
		  c = 18;
	  }
	  else if(this.nome == "Sala de estar") {
		  l = 21;
		  c = 1;
	  }
	  else if(this.nome == "Entrada") {
		  l = 21;
		  c = 9;
	  }
	  else if(this.nome == "Escritorio") {
		  l = 22;
		  c = 18;
	  }
	  else if(this.nome == "Biblioteca") {
		  l = 16;
		  c = 18;
	  }
	  int loca[] = new int[2];
	  loca[0] = l;
	  loca[1] = c + numPlayers;
	  return loca;
  }
  
  public boolean equals(Object obj) {
	  if(obj == null) {
		  return false;
	  }
	  Comodo other = (Comodo) obj;
	  if(this.nome == other.nome){
		  return true;
	  }
	  else {
		  return false;
	  }
	  
  }
  
  protected String getNome() {
	  return nome;
  }
  
  protected int[][] getPortas(){
    return portas;
  } 
  
  protected int getId(){
	    return id;
	  } 
  
  protected int getNumPortas(){
    return numPortas;  
  }  
  
  protected int getNumPlayers(){
	    return numPlayers;  
	  }  
  
  protected void setNumPlayers(int x){
	    numPlayers += x;  
	  }  

}  
