package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.awt.event.*;
import controller.Controller;

class EscolhaJogador  extends JFrame{
	private final int WIDTH = 1200;
	private final int HEIGHT = 700;
	private Controller controller; 
	protected EscolhaJogador(Controller x){    
		controller = x;
	    //Definições iniciais
		setLocationByPlatform(true);
	    setSize(WIDTH,HEIGHT);
	    setTitle("Clue - Escolha os jogadores");
	    setResizable(false);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    Container c = getContentPane();
	    JLayeredPane lpane = new JLayeredPane();
	    c.add(lpane,BorderLayout.CENTER);
	    lpane.setBounds(0, 0, 1200, 700);
	    c.setLayout(null);
	    
	    //Desenha imagem de capa no fundo
	    ImageComponent img = new ImageComponent("Imagens/Tabuleiros/Escolha.jpg",true);
	    img.setBounds(0,0,1200,700);
	    lpane.add(img,new Integer(0),0);
	    
	    //Monta checkbox
	    ArrayList<JCheckBox> vetorCb = new ArrayList<JCheckBox>(); 
	    JPanel checkboxes = new JPanel();
	    checkboxes.setLayout(new BoxLayout(checkboxes,BoxLayout.Y_AXIS)); 
	    
	    JCheckBox j1 = new JCheckBox("Miss Scarlett");
	    checkboxes.add(j1);
	    vetorCb.add(j1);
	    
	    JCheckBox j2 = new JCheckBox("Col. Mustard");
	    checkboxes.add(j2);
	    vetorCb.add(j2);
	    
	    JCheckBox j3 = new JCheckBox("Mrs. White");
	    checkboxes.add(j3);
	    vetorCb.add(j3);
	    
	    JCheckBox j4 = new JCheckBox("Rev. Green");
	    checkboxes.add(j4);
	    vetorCb.add(j4);
	    
	    JCheckBox j5 = new JCheckBox("Mrs. Peacock");
	    checkboxes.add(j5);
	    vetorCb.add(j5);
	    
	    JCheckBox j6 = new JCheckBox("Prof. Plum");
	    checkboxes.add(j6);
	    vetorCb.add(j6);
	    
	    checkboxes.setBounds(1000,400,120,140);

	    lpane.add(checkboxes,new Integer(1),0);
	    
	    
	    //Adiciona botão pra jogar
	    JButton jogar = new JButton("Jogar");
	    jogar.setBounds(1020,550,70,30);
	    lpane.add(jogar,new Integer(1),0);
	    jogar.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent event){
	    		ArrayList<String> nomeJogadores = new ArrayList<String>();  
	    		for(int i = 0;i<6;i++){
	    			if(vetorCb.get(i).isSelected()) {
	    				nomeJogadores.add(vetorCb.get(i).getText());
	    			}
	    		}
	    		if(nomeJogadores.size()>=3) {
	    			controller.montaJogadores(nomeJogadores); 
	    			setVisible(false);
	    		}
	    		else {
	    			JLabel erro = new JLabel("Selecione no mínimo 3 jogadores!");
	    			erro.setBounds(970,600,220,30);
	    			erro.setForeground(Color.RED);
	    			lpane.add(erro,new Integer(1),0);
	    		}
	    		
	    	}
	    });
	    
	    setVisible(true);
	} 
	
}
