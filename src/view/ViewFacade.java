package view;

import javax.swing.*;
import controller.*;

public class ViewFacade {
	private FrameJogo frame;
	
	public ViewFacade(Controller x) {
		frame = new FrameJogo(x);
	}
	
	public static void comecaView(Controller x) {
		PrimFrame prim = new PrimFrame(x);
	}
	
	public void acabaView() {
		frame.setVisible(false);
	}
	
	//Acessos a métodos do FrameJogo
	public void doProximo() {
	    frame.doProximo();
  }
  
  public void setClicavel(boolean b) {
	    frame.setClicavel(b);
  }
  
  public void enableProximo(boolean b) {
	  frame.enableProximo(b);
  }
  
  public void enablePalpite(boolean b) {
	  frame.enablePalpite(b);
  }
  
  public void enablePassagem(boolean b) {
	  frame.enablePassagem(b);
  }
  
  public void setJogadorVez(String s) {
	   frame.setJogadorVez(s);
  }
  
  public void setDado(int n) {
	  frame.setDado(n);
  }
  
  public void setDado(String s) {
	  frame.setDado(s);
  }
}
