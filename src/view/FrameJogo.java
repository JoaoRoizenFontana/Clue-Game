package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;

import controller.*;
import model.*;


class FrameJogo extends JFrame{
  public final int WIDTH = 1200;
  public final int HEIGHT = 725;
  private int map = 1;

  private int clique[] = new int[2];
  private Controller controller;
  private ArrayList<CirculoPlayer> circulos = new ArrayList<CirculoPlayer>();
  
  
  private boolean clicavel = false;
  
  private ViewDados dados;
  private PropertyChangeSupport support;
  
  private JLabel jogadorVez = new JLabel(""); 
  private JLabel dado = new JLabel("");
  
  private JButton jogarDados;
  private JButton escolherDados;
  private JButton proximo;
  private JButton palpite;
  private JButton secreta;
  private JButton salvar;
  
  protected FrameJogo(Controller x){    
	controller = x;
    //Definições iniciais
	setLocationByPlatform(true);
    setSize(WIDTH,HEIGHT);
    setTitle("Clue - Jogo");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Container c = getContentPane();
    JLayeredPane lpane = new JLayeredPane();
    c.add(lpane,BorderLayout.CENTER);
    lpane.setBounds(0, 0, WIDTH,HEIGHT);
    c.setLayout(null);
    
    //Desenha tabuleiro
    ImageComponent img = new ImageComponent("Imagens/Tabuleiros/Tabuleiro-Clue-A.jpg",false);
    img.setBounds(0,0,700,725);
    img.addMouseListener(new MouseHandler());
    lpane.add(img,new Integer(0),0);
    
    ImageComponent img2 = new ImageComponent("Imagens/Tabuleiros/Tabuleiro-Clue-C.jpg",false);
    img2.setBounds(0,0,700,725);
    img2.addMouseListener(new MouseHandler());
    lpane.add(img2,new Integer(0),0);
    img2.setVisible(false);
    
    JButton trocaMapa = new JButton("Trocar mapa");
    trocaMapa.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		if(map == 1) {
    			img2.setVisible(true);
    			img.setVisible(false);
    			map = 2;
    		}
    		else {
    			img.setVisible(true);
    			img2.setVisible(false);
    			map = 1;
    		}
    	}
    });
    trocaMapa.setBounds(10,10,130,25);
    lpane.add(trocaMapa,new Integer(1),0);
    
    
    
    //Adiciona jogadores e adiciona observers (jogadores e dados)
    for(int i = 0;i<controller.getNumJogadores();i++) {
    	circulos.add(new CirculoPlayer(i));
    	controller.addPCLJogador(circulos.get(i),i);
    	circulos.get(i).setBounds(0,0,WIDTH,HEIGHT);
    	lpane.add(circulos.get(i),new Integer(1),0);
    }
    
    
    //Monta a seção lateral
    dados = new ViewDados(); 
    controller.addPropertyChangeListener(dados);
    
    secreta = new JButton("Passagem Secreta");
    secreta.setBounds(725,0,475,50);
    c.add(secreta);
    secreta.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		
    		if(x.getId() == 'R') {
    			String nomeCom = x.getComodoName();
    			
    			if(controller.passagemSecreta(nomeCom)) {
    				enableProximo(true);
    				secreta.setEnabled(false);
    				enableDados(false);
    				setDado("Sucesso");
    			}
    			else {
    				setDado("Não é possível, use dados");
    			}
    		}
    	}
    });
    
    proximo = new JButton("Próximo");
    proximo.setBounds(725,55,475,50);
    proximo.setEnabled(false);
    c.add(proximo);
    proximo.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		controller.fluxoInicial();
    		proximo.setEnabled(false);
    		enableDados(true);
    		secreta.setEnabled(true);
    		//dados.repaint(); 
    		controller.possivelPalpite();
    	}
    });
    
    JButton mostrar = new JButton("Mostrar Cartas");
    mostrar.setBounds(725,110,475,50);
    c.add(mostrar);
    mostrar.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		FrameMostrar cartas = new FrameMostrar(controller);
    	}
    });
    
    JButton bloco = new JButton("Bloco de Notas");
    bloco.setBounds(725,165,475,50);
    c.add(bloco);
    bloco.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		FrameBdn bdn = new FrameBdn(controller);
    	}
    });
    
    
    palpite = new JButton("Palpite");
    enablePalpite(false);
    palpite.setBounds(725,220,475,50);
    c.add(palpite);
    palpite.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		FramePalpite pp = new FramePalpite(controller);
    		enablePalpite(false);
    	}
    });
    
    JButton acusar = new JButton("Acusar");
    acusar.setBounds(725,275,475,50);
    c.add(acusar);
    acusar.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		FrameAcusacao acu = new FrameAcusacao(controller);
    	}
    });
    
    
    salvar = new JButton("Salvar");
    salvar.setBounds(725,350,475,50);
    c.add(salvar);
    salvar.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		JFileChooser chooser = new JFileChooser();
    	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	    chooser.setFileFilter(new FilterSave());
    		chooser.setCurrentDirectory(new File("."));
    		int result = chooser.showSaveDialog(FrameJogo.this);
    		if(result == JFileChooser.APPROVE_OPTION) {
    			controller.salvaJogo(chooser.getSelectedFile().getPath());
    		}
    	}
    });
    
    jogarDados = new JButton("Jogar Dados");
    jogarDados.setBounds(725,575,475,50);
    c.add(jogarDados);
    
	dados.setBounds(800,440,325,150);
	c.add(dados);
    jogarDados.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		controller.definirDados();
    		//dados.repaint();
    		enableDados(false);
    	}
    });
    
    
    
    JSpinner escolhedor = new JSpinner(new SpinnerNumberModel(1,1,12,1));
    escolhedor.setBounds(725,630,75,50);
    c.add(escolhedor);
    
    escolherDados = new JButton("Escolher Dados");
    escolherDados.setBounds(800,630,400,50);
    c.add(escolherDados);
    escolherDados.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		controller.jogouDado((Integer) escolhedor.getValue()); 
    		enableDados(false);
    	}
    });
    
   
    
    jogadorVez.setBounds(800,390,160,50);
    c.add(jogadorVez);
    
    dado.setBounds(1005,390,160,50);
    c.add(dado);

    setVisible(true);
    
    
    
    
  }
  
  protected void doProximo() {
	    controller.fluxoInicial();
		proximo.setEnabled(false);
		enableDados(true);
		secreta.setEnabled(true);
		//dados.repaint(); 
		controller.possivelPalpite();
  }
  
  protected void setClicavel(boolean b) {
	  clicavel = b;
  }
  
  protected void enableDados(boolean b){
	  jogarDados.setEnabled(b);
	  escolherDados.setEnabled(b);
	  salvar.setEnabled(b);

  }
  
  
  protected void enableProximo(boolean b) {
	  proximo.setEnabled(b);
  }
  
  protected void enablePalpite(boolean b) {
	  palpite.setEnabled(b);
  }
  
  protected void enablePassagem(boolean b) {
	  secreta.setEnabled(b);
  }
  
  protected void setJogadorVez(String s) {
	  jogadorVez.setText(s);
	  jogadorVez.revalidate();
  }
  
  protected void setDado(int n) {
	  dado.setText(n + " passos");
	  dado.revalidate();
  }
  
  protected void setDado(String s) {
	  dado.setText(s);
	  dado.revalidate();
  }

  private class FilterSave extends FileFilter{

	  public boolean accept(File file) {
		  return true;
	  }

	@Override
	public String getDescription() {
		return "Digite acima o nome do seu arquivo";
	}
}
  
  
  private class MouseHandler extends MouseAdapter{
	  	
		public void mouseClicked(MouseEvent event) {
			if(clicavel) {
				clique[0] = event.getX();
				clique[1] = event.getY();
				
				if(clique[0]>=50 && clique[0]<=650 && clique[1]>=75 &&  clique[0]<=675) {
					controller.move(clique);
				}
			}
		}
  }
  
  private class ViewDados extends JPanel implements PropertyChangeListener{
		public void paintComponent(Graphics g) {
		        Image dado1;
		        Image dado2;
				try {
					
					 Graphics2D g2 = (Graphics2D) g;
					 
					dado1 = ImageIO.read(new File("Imagens/Tabuleiros/dado" + controller.getDado(0) + ".jpg"));
					dado2 = ImageIO.read(new File("Imagens/Tabuleiros/dado" + controller.getDado(1)  + ".jpg"));
					 
					 g2.setColor(controller.getColor(controller.getVez()));
					 g2.fillRect(0, 0, 325, 150);
					 g2.drawImage(dado1,20, 20, 80, 75, null);
					 g2.drawImage(dado2,225, 20, 80, 75, null);
				}
				
				catch(IOException e){
					System.out.println(e.getMessage());
					System.exit(1);
				}
		 }
		
		public void propertyChange(PropertyChangeEvent evt) {
			dados.revalidate();
			dados.repaint();
			
		}
		 
	}
  
  private class CirculoPlayer extends JComponent implements PropertyChangeListener{
		private int x;
		private int y;
		private Color cor;
		private int pos[];
		
		public CirculoPlayer(int vez) {
			pos = controller.getXY(vez);
			x = pos[1]*25 + 50;
			y = pos[0]*25 + 75;
			cor = controller.getColor(vez);
		}
		
		public void paintComponent(Graphics g) {
			
			Graphics2D g2 = (Graphics2D) g;
			Ellipse2D player = new Ellipse2D.Double(); 
			player.setFrame(x,y,25,25);
			
			
			
			g2.setColor(cor);
			g2.fill(player);
		}
		
		public void propertyChange(PropertyChangeEvent evt) {
			int nova[] = (int[])evt.getNewValue();
			
			x = nova[1]*25 + 50;
			y = nova[0]*25 + 75;
			this.revalidate();
			this.repaint();
			
		}
		
		
	}
  
  
}








  

  