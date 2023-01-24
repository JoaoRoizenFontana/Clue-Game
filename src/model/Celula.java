package model;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.*;

class Celula{
  private char identificador;
  private int linha;
  private int coluna;
  private Comodo comodo;
  private boolean temJogador = false;
  
  protected Celula(char i, int l, int c){
    this.identificador = i;
    this.linha = l;
    this.coluna = c;
    if (i == 'R'){
      comodo = achaComodo(l,c);  
    }  
  }
  
  protected Celula(){
    this.identificador = 'S';  
  }   

  protected Comodo achaComodo(int l, int c){
    
    String s;
    int portas[][] = new int[4][];
    int num = 0;
    int id;
    if(l<7){
      if(c<6){
        s = "Cozinha";
        portas[0] = new int[]{6,4};
        num = 1;
        id = 12;
      }
      else if(c<16){
        s = "Sala de musica";
        portas[0] = new int[]{4,7};
        portas[1] = new int[]{7,9};
        portas[2] = new int[]{7,14};
        portas[3] = new int[]{4,16};
        num = 4;
        id = 18;
      }
      else{
        s = "Jardim de inverno";
        portas[0] = new int[]{4,18};
        num = 1;
        id = 19;
      }
    }
    else if (l<12){
       if(c<8){
        s = "Sala de jantar"; 
        portas[0] = new int[]{11,8};
        portas[1] = new int[]{15,6};
        num = 2;
        id = 17;
       }
       else{
        s = "Salão de jogos"; 
        portas[0] = new int[]{8,17};
        portas[1] = new int[]{12,22}; 
        num = 2;
        id = 20;
       }  
    }
    else if (l < 18){
      if(c < 8){
        s = "Sala de jantar";
        portas[0] = new int[]{11,8};
        portas[1] = new int[]{15,6};
        num = 2;
        id = 17;
      }
      else if(c<15){
        s = "Entrada";  
        portas[0] = new int[]{16,11};
        portas[1] = new int[]{16,12};
        portas[2] = new int[]{19,15};
        num = 3;
        id = 16;
      }
      else{
        s = "Biblioteca";
        portas[0] = new int[]{12,20};
        portas[1] = new int[]{15,16};
        num = 2;
        id = 14;
      }  
    }
    else{
      if(c < 7){
        s = "Sala de estar";  
        portas[0] = new int[]{17,6};
        num = 1;
        id = 15;
      }
      else if ( c<15){
        s = "Entrada";
        portas[0] = new int[]{16,11};
        portas[1] = new int[]{16,12};
        portas[2] = new int[]{19,15};
        num = 3;
        id = 16;
      }  
      else{
        s = "Escritorio";
        portas[0] = new int[]{19,17};
        num = 1;
        id = 13;
      }
    }
   
    return Comodo.getComodoSingleton(s,portas,num,id);
  } 
  
  @Override
  public boolean equals(Object obj) {
	  Celula other = (Celula) obj;
	  if(this.linha == other.linha && this.coluna == other.coluna){
		  return true;
	  }
	  else {
		  return false;
	  }
	  
  }
  
  public String toString() {

	  return Integer.toString(linha) +"/"+ Integer.toString(coluna);
  }
  
  protected int getLinha(){
    return linha; 
  } 
  
  protected int getColuna(){
    return coluna;  
  }  
 
  protected char getId(){
    return identificador; 
  } 
  
  protected Comodo getComodo(){
    return comodo;
  }  
  
  protected boolean gettemJogador(){
      return temJogador;
  }

  protected void settemJogador(boolean b){
      temJogador = b;
  }
} 
