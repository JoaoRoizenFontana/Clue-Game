package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.imageio.*;

import controller.Controller;

public class FrameMostrar extends JFrame{
	  ArrayList<Integer> cartas = new ArrayList<Integer>();
	  
	  public FrameMostrar(Controller x){
		    cartas = x.getCartas(); 
		    cartas.sort(Comparator.naturalOrder());
		    setLocationByPlatform(true);
		    setSize(600,600);
		    setTitle("Clue - Minhas cartas");
		    setResizable(false);
		    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    
		    
		    Container c = getContentPane();
		    c.setLayout(null);
		    
		    
		    Iterator<Integer> aux = cartas.iterator();
		    int contador = 110;
		    int ativo = 0;
		    int id;
		    
		    String nomeArq;
		    JPanel armas = new JPanel();
		    JPanel suspeitos = new JPanel();
		    JPanel comodos = new JPanel();
		    CartaComponent img;
		    while(aux.hasNext()) {
		    	id = aux.next();
		    	
		    	if(id<6) {
		    		if(contador == 110) {
		    			armas.setBounds(0, 0,600,150);
		    			armas.setLayout(null);
		    			JLabel texto = new JLabel("Arma(s)");
		    			texto.setBounds(20,50,85,65);
		    			armas.add(texto);
		    		}
		    		
		    		nomeArq = "Imagens/Armas/"+id+".jpg";
		    		
		    		img = new CartaComponent(nomeArq);
		    		img.setBounds(contador,10,110,250);
		    		armas.add(img);
		    		contador+=105;
		    		continue;
		    	}
		    	if(contador != 110 && ativo == 0) {
		    		c.add(armas);
		    		contador=110;
		    	}
		    	
		    	if(id>5 && id<12) {
		    		if(contador == 110) {
		    			ativo = 1;
		    			suspeitos.setBounds(0,170,600,150);
		    			JLabel texto = new JLabel("Suspeito(s)");
		    			suspeitos.setLayout(null);
		    			texto.setBounds(20,50,85,65);
		    			suspeitos.add(texto);
		    		}
		    		nomeArq = "Imagens/Suspeitos/"+id+".jpg";
		    		img = new CartaComponent(nomeArq);
		    		img.setBounds(contador,10,110,170);
		    		suspeitos.add(img);
		    		contador+=105;
		    		continue;
		    	}
		    	if(contador != 110 && ativo == 1) {
		    		c.add(suspeitos);
		    		contador=110;
		    	}
		    	
		    	if(id>11 && id<21) {
		    		if(contador == 110) {
		    			ativo = 2;
		    			comodos.setBounds(0,340,600,150);
		    			JLabel texto = new JLabel("Comodos(s)");
		    			comodos.setLayout(null);
		    			texto.setBounds(20,50,85,65);
		    			comodos.add(texto);
		    			
		    		}
		    		nomeArq = "Imagens/Comodos/"+id+".jpg";
		    		img = new CartaComponent(nomeArq);
		    		img.setBounds(contador,10,110,170);
		    		comodos.add(img);
		    		
		    		
		    		
		    		contador+=105;
		    		continue;
		    	}
	  		}
		    if(contador != 110 && ativo == 2) {
	    		c.add(comodos);
	    		
	    	}
		    
		  
		    setVisible(true);
		    
	  }
	  
	 
}
class CartaComponent extends JComponent{
	 private static final int DEFAULT_WIDTH = 100;
	 private static final int DEFAULT_HEIGHT = 150;

	 private Image image;

	 public CartaComponent(String path){
	   try {
		   image = ImageIO.read(new File(path));
	   }
	   catch(IOException e){
		   System.out.println(e.getMessage());
		   System.exit(1);
	   }
	 }
	 
	 

	 public void paintComponent(Graphics g){
	   if (image == null) {
		   
		   return;
		   
	   }
	 
	   g.drawImage(image, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, null);	   
	   
	 }

	  public Dimension getPreferredSize(){ 
	    return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
	  }
	}
