package view;

import java.io.*;
import java.awt.Container;
import java.util.*;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.border.Border;

import controller.Controller;
import model.*;


public class FrameBdn extends JFrame{
	  private ArrayList<Integer> bloco = new ArrayList<Integer>(); 
	  private ArrayList<JCheckBox> vetorCb = new ArrayList<JCheckBox>(); 
	  
	  public FrameBdn(Controller x){
		    bloco = x.getBloco(); 
		    setLocationByPlatform(true);
		    setSize(600,500);
		    setTitle("Clue - Bloco de Notas");
		    setResizable(false);
		    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    WindowListener listener = new WindowHandler();
		    this.addWindowListener(listener);
		    
		    
		    
		    Container c = getContentPane();
		    c.setLayout(null);
		    Border etched = BorderFactory.createEtchedBorder();
		    Border titledArmas = BorderFactory.createTitledBorder(etched,"Armas");
		    
		    BufferedReader temp;
		    
		    try{
		      temp = new BufferedReader(new FileReader("src/model/cartas.txt"));
		      JPanel armas = new JPanel();
			  armas.setLayout(new BoxLayout(armas,BoxLayout.Y_AXIS));
			  armas.setBorder(titledArmas); 
			  
		      for(int i = 0;i < 6; i++){
		    	  String nome =  temp.readLine();
		    	  JCheckBox check = new JCheckBox(nome);
		          armas.add(check);
		          vetorCb.add(check);
		        
		      } 
		      armas.setBounds(20,20,180,300); 
			  c.add(armas); 
			  
			  Border titledSuspeitos = BorderFactory.createTitledBorder(etched,"Suspeitos");
			  JPanel suspeitos = new JPanel();
			  suspeitos.setLayout(new BoxLayout(suspeitos,BoxLayout.Y_AXIS));
			  suspeitos.setBorder(titledSuspeitos); 
		      for(int i = 0;i < 6; i++){
		    	  String nome =  temp.readLine();
		    	  JCheckBox check = new JCheckBox(nome);
		          suspeitos.add(check);
		          vetorCb.add(check);
		        
		      }
		      suspeitos.setBounds(220,20,180,400);
			  c.add(suspeitos); 
			  
			  Border titledComodos = BorderFactory.createTitledBorder(etched,"Cômodos");
			  JPanel comodos = new JPanel();
			  comodos.setLayout(new BoxLayout(comodos,BoxLayout.Y_AXIS));
			  comodos.setBorder(titledComodos); 
		      for(int i = 0;i < 9; i++){
		    	 
		    	  String nome =  temp.readLine();
		    	  JCheckBox check = new JCheckBox(nome);
		          comodos.add(check);
		          vetorCb.add(check);
		        
		      }
		      comodos.setBounds(420,20,180,400);
		      c.add(comodos);
		      
		    }
		    catch(IOException e){
		    	System.out.println("Erro");
		      return;
		    }
		    
		   
		    Iterator<Integer> aux = bloco.iterator();
		
		    while(aux.hasNext()) {
		    	
		    	vetorCb.get(aux.next()).setSelected(true);
		    }
		    
		    
		    setVisible(true);
		    
	  }
	  
	  private class WindowHandler extends WindowAdapter{
		  public void windowClosing(WindowEvent e) {
			  int contador = 0;
			 
			  while(contador < 21) {
				  JCheckBox atual = vetorCb.get(contador); 
				  if(atual.isSelected() && !bloco.contains(contador)) {
					  bloco.add(contador);
				  }
				  contador++;
			  }
			  
			  
		  }
	  }
}
