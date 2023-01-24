package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.imageio.*;

import controller.Controller;
import model.*;

class FramePalpite extends JFrame{
	  private JLabel text;
	  private JPanel suspeitos;
	  private JPanel armas;
	  private ArrayList<JRadioButton> radios = new  ArrayList<JRadioButton>();
	  private ArrayList<Integer> palpite = new  ArrayList<Integer>();
	  
	  protected FramePalpite(Controller x){
		    Controller controller = x;
		    setLocationByPlatform(true);
		    setSize(640,410);
		    setTitle("Clue - Faça um palpite");
		    setResizable(false);
		    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    Container c = getContentPane();
		    c.setLayout(null);
		    
		    palpite.add(x.getComodoId());
		    
		    	
		    text = new JLabel("Defina seu palpite e clique no botão");
		    text.setBounds(220,20,600,30);
		    c.add(text);
		    
		    Border etched = BorderFactory.createEtchedBorder();
		    
		    Border titledComodos = BorderFactory.createTitledBorder(etched,"Cômodo Escolhido");
			JLabel comodos = new JLabel(x.getComodoName());
			comodos.setBorder(titledComodos); 
			comodos.setBounds(20,100,200,130);
			c.add(comodos);
			
			armas = new JPanel();
			Border titledArmas = BorderFactory.createTitledBorder(etched,"Arma do crime");
			armas.setBorder(titledArmas); 
			armas.setLayout(new BoxLayout(armas,BoxLayout.Y_AXIS));
			ButtonGroup gparmas = new ButtonGroup();
			try {
				
				  BufferedReader temp =  new BufferedReader(new FileReader("src/model/cartas.txt"));;
			      for(int i = 0;i < 6; i++){
			    	  String nome =  temp.readLine();
			    	  JRadioButton radio = new JRadioButton(nome,false);
			    	  gparmas.add(radio);
			          armas.add(radio);
			          radios.add(radio);			           
			      }
			      armas.setBounds(240,100,180,190);
			      c.add(armas);
			
			      suspeitos = new JPanel();
			      Border titledSuspeitos = BorderFactory.createTitledBorder(etched,"Quem matou?");
			      suspeitos.setBorder(titledSuspeitos); 
			      suspeitos.setLayout(new BoxLayout(suspeitos,BoxLayout.Y_AXIS));
			      ButtonGroup gpsuspeitos = new ButtonGroup();
			

			      for(int i = 0;i < 6; i++){
			    	  String nome =  temp.readLine();
			    	  JRadioButton radio = new JRadioButton(nome,false);
			    	  gpsuspeitos.add(radio);
			    	  suspeitos.add(radio);
			    	  radios.add(radio);	
			           
			      }
			      suspeitos.setBounds(440,100,180,190);
				  c.add(suspeitos);
			}
			catch(IOException e) {
				
			}
			
		    JButton confirma = new JButton("Confirma");
		    confirma.setBounds(440,310,180,70);
		    c.add(confirma);
		    confirma.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent event){
		    		if(confirmaCheck()) {
		    			ArrayList<String> respostas = controller.analisaPalpite(palpite);
		    			
		    			armas.setVisible(false);
		    			suspeitos.setVisible(false);
		    			comodos.setVisible(false);
		    			confirma.setVisible(false);
		    			
		    			setTexto("Essas foram as respostas dos outros participantes:");
		    			int contador = 0;
		    			for(String j : respostas) {
		    				JLabel resp = new JLabel(j);
		    				resp.setBounds(100,100+contador,600,30);
		    				contador+=80;
		    				c.add(resp);
		    				
		    			}
		    			JButton fechar = new JButton("Fechar");
		    		    fechar.setBounds(440,310,180,70);
		    		    c.add(fechar);
		    		    fechar.addActionListener(new ActionListener(){
		    		    	public void actionPerformed(ActionEvent event){
		    		    		setVisible(false);
		    		    	}
		    		    });
		    			
		    		}
		    		else {
		    			setTexto("Está faltando escolhas");
		    		}
		    	}
		    });
		    
		    setVisible(true);
		    
	  }
	  
	  private void setTexto(String s) {
		  text.setText(s);
		  text.revalidate();
	  }
	  
	  private boolean confirmaCheck() {
		  int contador = 0;
		  int index = 0;
		  for(JRadioButton b: radios) {
			  if(b.isSelected()) {
				  contador++;
				  palpite.add(index);
			  }
			  index++;
		  }
		  if(contador==2) {
			  return true;
		  }
		  else if(contador == 1){
			  palpite.remove(1);
			  
		  }
		  
		  return false;
	  }
	  
	  
	  
	 
}

