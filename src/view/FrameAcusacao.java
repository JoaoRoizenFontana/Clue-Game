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

class FrameAcusacao extends JFrame{
	  private JLabel text;
	  private JPanel suspeitos;
	  private JPanel armas;
	  private ArrayList<JRadioButton> radios = new  ArrayList<JRadioButton>();
	  private ArrayList<Integer> acusacao = new  ArrayList<Integer>();
	  
	  protected FrameAcusacao(Controller x){
		    Controller controller = x;
		    setLocationByPlatform(true);
		    setSize(640,410);
		    setTitle("Clue - Faça uma acusação");
		    setResizable(false);
		    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    Container c = getContentPane();
		    c.setLayout(null);
		    
 	
		    text = new JLabel("Defina sua acusação e clique no botão");
		    text.setBounds(180,20,600,30);
		    c.add(text);
		    
		    Border etched = BorderFactory.createEtchedBorder();
		    
		    JPanel comodos = new JPanel();
		    Border titledComodos = BorderFactory.createTitledBorder(etched,"Cômodo do crime");
			comodos.setBorder(titledComodos); 
			ButtonGroup gpcomodos = new ButtonGroup();
			
			
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
				  
				  for(int i = 0;i < 9; i++){
			    	  String nome =  temp.readLine();
			    	  JRadioButton radio = new JRadioButton(nome,false);
			    	  gpcomodos.add(radio);
			    	  comodos.add(radio);
			    	  radios.add(radio);	
			           
			      }
				  comodos.setBounds(20,100,200,240);
				  c.add(comodos);
			}
			catch(IOException e) {
				
			}
			
		    JButton confirma = new JButton("Confirma");
		    confirma.setBounds(440,310,180,70);
		    c.add(confirma);
		    confirma.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent event){
		    		if(confirmaCheck()) {
		    			
		    			
		    			armas.setVisible(false);
		    			suspeitos.setVisible(false);
		    			comodos.setVisible(false);
		    			confirma.setVisible(false);
		    			
		    			
		    			JButton fechar = new JButton("Fechar");
		    		    fechar.setBounds(440,310,180,70);
		    		    c.add(fechar);
		    		    
		    		    int result = controller.analisaAcusacao(acusacao);
		    			if(result >= -1){
		    				JButton novo = new JButton("Novo jogo");
			    		    novo.setBounds(20,310,180,70);
			    		    c.add(novo);
			    		    
			    		    if(result == -1) {
			    		    	setTexto("Você acertou! Parabéns, você ganhou o jogo.");
			    		    }
			    		    else {
			    		    	String s = "Você errou! " + controller.getNome(result) + " ganhou o jogo.";
			    		    	setTexto(s);
			    		    }
			    		    	
		    				fechar.addActionListener(new ActionListener(){
			    		    	public void actionPerformed(ActionEvent event){
			    		    		
			    		    		setVisible(false);
			    		    		controller.acabaJogo();
			    		    		System.exit(0);
			    		    	}
			    		    });
		    				novo.addActionListener(new ActionListener(){
			    		    	public void actionPerformed(ActionEvent event){
			    		    		controller.acabaJogo();
			    		    		setVisible(false);
			    		    		controller.comecaJogo();
			    		    	}
			    		    });
		    			}
		    			else {
		    				String s = "A acusação não estava correta, o jogador "+controller.getNome()+" está eliminado!";
	    		    		setTexto(s);
			    		    fechar.addActionListener(new ActionListener(){
			    		    	public void actionPerformed(ActionEvent event){
			    		    		
			    		    		setVisible(false);
			    		    		controller.doProximo();
			    		    	}
			    		    });
		    			}
		    			
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
				  acusacao.add(index);
			  }
			  index++;
		  }
		  if(contador==3) {
			  return true;
		  }
		  else if(contador == 1){
			  acusacao.remove(0);
			  
		  }
		  else if(contador == 2){
			  acusacao.remove(1);
			  acusacao.remove(0);
			  
		  }
		  
		  return false;
	  }
	  
	  
	  
	 
}

