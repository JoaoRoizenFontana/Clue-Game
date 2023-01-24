package view;
import javax.imageio.ImageIO;
import controller.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;

class PrimFrame extends JFrame{
  private final int WIDTH = 1200;
  private final int HEIGHT = 700;

  protected PrimFrame(Controller controller){    
    //Definições iniciais
	setLocationByPlatform(true);
    setSize(WIDTH,HEIGHT);
    setTitle("Clue - Início");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Container c = getContentPane();
    JLayeredPane lpane = new JLayeredPane();
    c.add(lpane,BorderLayout.CENTER);
    lpane.setBounds(0, 0, 1200, 700);
    c.setLayout(null);
    
    //Desenha imagem de capa no fundo
    ImageComponent img = new ImageComponent("Imagens/Tabuleiros/Clue1.jpeg",true);
    img.setBounds(0,0,1200,700);
    lpane.add(img,new Integer(0),0);
    //Adiciona botões
    
    JButton nJogo = new JButton("Novo Jogo");
    nJogo.setBounds(175,250,175,50);
    lpane.add(nJogo, new Integer(1),0);
    nJogo.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		setVisible(false);
    		EscolhaJogador frame = new EscolhaJogador(controller);
    	}
    });
    
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setFileFilter(new FilterTxt());
    
    JButton cont = new JButton("Continuar");
    cont.setBounds(175,350,175,50);
    lpane.add(cont, new Integer(1),0);    
    cont.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent event){
    		chooser.setCurrentDirectory(new File("."));
    		int result = chooser.showOpenDialog(PrimFrame.this);
    		if(result == JFileChooser.APPROVE_OPTION) {
    			setVisible(false);
    			controller.restartSetup(chooser.getSelectedFile().getPath());
    		}
    	}
    });
    

    setVisible(true);
    

    
    
  }
  
  private class FilterTxt extends FileFilter{

	  public boolean accept(File file) {
		  String name = file.getName();
		  return name.endsWith(".txt");
	  }

	@Override
	public String getDescription() {
		return ".txt";
	}
}
    
}

class ImageComponent extends JComponent{
 private static final int DEFAULT_WIDTH = 1200;
 private static final int DEFAULT_HEIGHT = 700;

 private Image image;
 private boolean fill;

 public ImageComponent(String path,boolean b){
   fill = b;
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
   if(fill  == true) {
	   g.drawImage(image, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
   } 
   else {
	   g.drawImage(image, 0, 0, DEFAULT_HEIGHT, 725, null);	   
   }
 }

  public Dimension getPreferredSize(){ 
    return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
  }
  
  
  
}



  