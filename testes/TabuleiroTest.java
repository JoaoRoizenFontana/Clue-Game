package model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TabuleiroTest {
	Tabuleiro testTabuleiro;
	
	@Before
	public void setUp() {
		Jogador j = new Jogador("Srta. Scarlet",'N', 5, 1);
    Jogador j2 = new Jogador("Coronel Mustard",'R', 5, 16);
    Jogador j3 = new Jogador("Professor Plum",'R', 15, 15);
    Jogador j4 = new Jogador("Reverendo Green",'R', 9, 8);
    
    
		testTabuleiro = new Tabuleiro();
	}


@Test
  public void testaMovimento(){
    // testa movimento entre uma casa e outra casa
    // movimento horizontal
    assertTrue(testTabuleiro.validaMovimento(1, testTabuleiro.getCelula(5,2), j));
    assertFalse(testTabuleiro.validaMovimento(1, testTabuleiro.getCelula(5,3), j));

    // moveimento vertical
    assertTrue(testTabuleiro.validaMovimento(1, testTabuleiro.getCelula(6,1), j));
    assertFalse(testTabuleiro.validaMovimento(1, testTabuleiro.getCelula(7,1), j));

    // teste com cantos
    assertTrue(testTabuleiro.validaMovimento(4, testTabuleiro.getCelula(7,18), j2));
    assertTrue(testTabuleiro.validaMovimento(4, testTabuleiro.getCelula(16,18), j3));
    assertFalse(testTabuleiro.validaMovimento(3, testTabuleiro.getCelula(16,18), j3));

    // testa movimentacao entre casa e comodo
    assertTrue(testTabuleiro.validaMovimento(1, testTabuleiro.getCelula(9,3), j4));
    assertTrue(testTabuleiro.validaMovimento(4, testTabuleiro.getCelula(9,3), j4));
    
  }
}